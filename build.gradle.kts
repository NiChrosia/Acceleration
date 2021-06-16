version = "1.0"
plugins { kotlin("jvm") version "1.5.0" }
apply(plugin = "kotlin")

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, "seconds")
}

fun String.runCommand(workingDir: File = file("./")): String {
    val parts = this.split("\\s".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

    return proc.inputStream.bufferedReader().readText().trim()
}

buildscript {
    project.extra.apply {
        val args = if (project.hasProperty("args")) project.properties["args"] else ""

        /** Whether this run is local or from GitHub Actions. */
        val local = (args as String).split(" ").first() != "githubActions"

        /** Whether the user running this build is NiChrosia. Used only for moveJar. Disable if you aren't me. */
        val isNiChrosia = true

        /** Whether this is a development build. Used to determine whether to use commit hashes or releases. */
        val dev = true

        /** The latest Mindustry release. */
        val latestMindustryRelease = "v126.2"
        val mindustryHash = "fe9ff212b24f7b2f0e1ac1a95fef4c19a4e21ce8"
        val arcHash = "07ced971f4c8b8b5a61aa3a84b29c90aa497cb48"

        set("moveJar", isNiChrosia && local)
        set("kotlinVersion", "1.5.10")
        set("arcVersion", if (dev) arcHash else latestMindustryRelease)
        set("mindustryVersion", if (dev) mindustryHash else latestMindustryRelease)
        set("sdkVersion", "30")
        set("sdkRoot", System.getenv("ANDROID_HOME"))
        set("windows", System.getProperty("os.name").toLowerCase().contains("windows"))
        set("dirName", rootDir.name.split("/").last())
    }

    repositories { mavenCentral() }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${project.extra["kotlinVersion"]}")
    }
}

repositories {
    mavenCentral()
    maven { setUrl("https://www.jitpack.io") }
}

dependencies {
    compileOnly("com.github.Anuken.Arc:arc-core:${project.extra["arcVersion"]}")
    compileOnly("com.github.Anuken.Mindustry:core:${project.extra["mindustryVersion"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.extra["kotlinVersion"]}")
}

tasks {
    "jar"(Jar::class) {
        dependsOn("compileKotlin")
        
        archiveFileName.set("${project.extra["dirName"]}-Desktop.jar")

        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from(configurations.runtimeClasspath.map { configuration ->
            configuration.asFileTree.fold(files().asFileTree) { collection, file ->
                if (file.isDirectory) collection else collection.plus(zipTree(file))
            }
        })

        from(rootDir) {
            include("mod.hjson")
            include("icon.png")
            include("preview.png")
        }
    }
}

tasks.register<Jar>("jarAndroid") {
    dependsOn("jar")

    archiveFileName.set("${project.extra["dirName"]}-Android.jar")

    doLast {
        val files = (
            configurations.compileClasspath.get().files +
            configurations.runtimeClasspath.get().files +
            arrayOf(File("${project.extra["sdkRoot"]}/platforms/android-${project.extra["sdkVersion"]}/android.jar"))
        )

        val dependencies = files.fold("") { str, file ->  str + " --classpath ${file.path}" }

        //dex and desugar files - this requires d8 in your PATH
        "d8$dependencies --min-api 14 --output ${project.extra["dirName"]}-Android.jar ${project.extra["dirName"]}-Desktop.jar".runCommand(File("${buildDir.path}/libs"))
    }
}

tasks.register("alphableed") {
    exec {
        workingDir = rootDir

        if (project.extra["windows"] as Boolean) {
            commandLine("./alpha-bleeding-windows.exe", "--replace", "./src/main/resources/sprites")
        } else {
            commandLine("./alpha-bleed", "./src/main/resources/sprites")
        }
    }
}

tasks.register("moveJar") {
    doLast {
        if (project.extra["moveJar"] as Boolean) {
            if (project.extra["windows"] as Boolean) {
                exec {
                    commandLine(
                            "powershell.exe",
                            "mv -Force build/libs/${project.extra["dirName"]}-${project.version}.jar ../../${project.extra["dirName"]}-${project.version}.jar"
                    )
                }
            } else {
                exec {
                    commandLine(
                        "mv",
                        "-f",
                        "./build/libs/${project.extra["dirName"]}-${project.version}.jar",
                        "../../.local/share/Mindustry/mods/${project.extra["dirName"]}-${project.version}.jar"
                    )
                }
            }
        }
    }
}

tasks.register<Jar>("deploy") {
    dependsOn("alphableed", "jar")

    from(zipTree("$buildDir/libs/${project.extra["dirName"]}-Desktop.jar"))

    doLast {
        delete { delete("$buildDir/libs/${project.extra["dirName"]}-Desktop.jar") }
    }

    finalizedBy("moveJar")
}

tasks.register<Jar>("deployDexed") {
    dependsOn("alphableed", "jar", "jarAndroid")

    tasks.getByName("moveJar").setShouldRunAfter(mutableListOf(this))

    archiveFileName.set("${project.extra["dirName"]}.jar")

    from(zipTree("$buildDir/libs/${project.extra["dirName"]}-Desktop.jar"),
         zipTree("$buildDir/libs/${project.extra["dirName"]}-Android.jar"))

    doLast {
        delete {
            delete("$buildDir/libs/${project.extra["dirName"]}-Desktop.jar")
            delete("$buildDir/libs/${project.extra["dirName"]}-Android.jar")
        }
    }

    finalizedBy("moveJar")
}