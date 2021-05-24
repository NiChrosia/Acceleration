version = "1.0"
plugins { kotlin("jvm") version "1.5.0" }
apply(plugin = "kotlin")
kotlin.sourceSets.getByName("main").kotlin.setSrcDirs(listOf("src", "assets"))

fun String.runCommand(workingDir: File = file("./")): String {
    val parts = this.split("\\s".toRegex())
    val proc = ProcessBuilder(*parts.toTypedArray())
        .directory(workingDir)
        .redirectOutput(ProcessBuilder.Redirect.PIPE)
        .redirectError(ProcessBuilder.Redirect.PIPE)
        .start()

    //while (!proc.onExit().isDone) {
    //    TimeUnit.SECONDS.sleep(1L)
    //}

    return proc.inputStream.bufferedReader().readText().trim()
}

buildscript {
    project.extra.apply {
        /** Whether to move the jarfile into my mods directory for easy testing. Disable if you are not me. */
        set("moveJar", true)

        set("kotlinVersion", "1.5.0")
        set("mindustryVersion", "v126.2")
        set("sdkVersion", "30")
        set("sdkRoot", System.getenv("ANDROID_HOME"))
        set("windows", System.getProperty("os.name").toLowerCase().contains("windows"))
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
    compileOnly("com.github.Anuken.Arc:arc-core:${project.extra["mindustryVersion"]}")
    compileOnly("com.github.Anuken.Mindustry:core:${project.extra["mindustryVersion"]}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${project.extra["kotlinVersion"]}")
}

tasks {
    "jar"(Jar::class) {
        val dirName = rootDir.name.split("//").last()

        archiveFileName.set("$dirName-Desktop.jar")

        from(configurations.runtimeClasspath.map { configuration ->
            configuration.asFileTree.fold(files().asFileTree) { collection, file ->
                if (file.isDirectory) collection else collection.plus(zipTree(file))
            }
        })

        from(rootDir) { include("mod.hjson") }
        from("assets/") { include("**") }
    }
}

tasks.register<Jar>("jarAndroid") {
    dependsOn("jar")

    val dirName = rootDir.name.split("/").last()
    archiveFileName.set("$dirName-Android.jar")

    doLast {
        val files = (
            configurations.compileClasspath.get().files +
            configurations.runtimeClasspath.get().files +
            arrayOf(File("${project.extra["sdkRoot"]}/platforms/android-${project.extra["sdkVersion"]}/android.jar"))
        )

        val dependencies = files.fold("") { str, file ->  str + " --classpath ${file.path}" }

        //dex and desugar files - this requires d8 in your PATH
        "d8$dependencies --min-api 14 --output $dirName-Android.jar $dirName-Desktop.jar".runCommand(File("${buildDir.path}/libs"))
    }
}

tasks.register("alphableed") {
    exec {
        workingDir = rootDir

        if (project.extra["windows"] as Boolean) {
            commandLine("./alpha-bleeding-windows.exe", "--replace", "./assets/sprites")
        } else {
            commandLine("./alpha-bleed", "--replace", "./assets/sprites")
        }
    }
}

tasks.register<Jar>("deploy") {
    dependsOn("alphableed")
    dependsOn("jar")

    val dirName = rootDir.name.split("/").last()
    archiveFileName.set("$dirName-${project.version}.jar")

    from(zipTree("$buildDir/libs/${dirName}-Desktop.jar"))

    doLast {
        delete { delete("$buildDir/libs/${dirName}-Desktop.jar") }

        if (project.extra["moveJar"] as Boolean && project.extra["windows"] as Boolean) {
            exec {
                commandLine("powershell.exe", "mv -Force build/libs/Acceleration-kotlin-${project.version}.jar ../../Acceleration-kotlin-${project.version}.jar")
            }
        }
    }
}

tasks.register<Jar>("deployDexed") {
    dependsOn("alphableed")
    dependsOn("jar")
    dependsOn("jarAndroid")

    val dirName = rootDir.name.split("/").last()
    archiveFileName.set("$dirName-${project.version}.jar")

    from(zipTree("$buildDir/libs/${dirName}-Desktop.jar"),
         zipTree("$buildDir/libs/${dirName}-Android.jar"))

    doLast {
        delete {
            delete("$buildDir/libs/${dirName}-Desktop.jar")
            delete("$buildDir/libs/${dirName}-Android.jar")
        }

        if (project.extra["moveJar"] as Boolean && project.extra["windows"] as Boolean) {
            exec {
                commandLine("powershell.exe", "mv -Force build/libs/Acceleration-kotlin-${project.version}.jar ../../Acceleration-kotlin-${project.version}.jar")
            }
        }
    }
}