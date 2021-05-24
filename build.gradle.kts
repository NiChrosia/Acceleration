version = "1.0"
plugins { kotlin("jvm") version "1.5.0" }
apply(plugin = "kotlin")
kotlin.sourceSets.getByName("main").kotlin.setSrcDirs(listOf("src", "assets"))

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
        val dirName = rootDir.name.split("\\\\").last()

        archiveFileName.set("$dirName-${project.version}.jar")

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

    doLast {
        val files = (
            configurations.compileClasspath.get().files +
            configurations.runtimeClasspath.get().files +
            setOf(File("${project.extra["sdkRoot"]}/platforms/android-${project.extra["sdkVersion"]}/android.jar"))
        ).toTypedArray()

        val dependencies = files.fold(arrayOf<String>()) { collection, file -> collection.plus("--classpath ${file.path}") }

        exec { commandLine("d8 $dependencies --min-api 14 --output ${archiveBaseName}Android.jar ${archiveBaseName}Desktop.jar") }
    }
}

tasks.register("alphableed") {
    exec {
        workingDir = File(rootDir.path + "/assets/sprites")

        if (project.extra["windows"] as Boolean) {
            commandLine("./alpha-bleeding-windows.exe", "--replace", ".")
        } else {
            commandLine("./alpha-bleed", "--replace", ".")
        }
    }
}

tasks.register<Jar>("deploy") {
    dependsOn("alphableed")
    dependsOn("jar")

    doLast {
        delete { delete("$buildDir/libs/${archiveBaseName}Desktop.jar") }

        if (project.extra["moveJar"] as Boolean && project.extra["windows"] as Boolean) {
            exec {
                commandLine("powershell.exe", "mv -Force build/libs/Acceleration-kotlin-${project.version}.jar ../../Acceleration-kotlin-${project.version}.jar")
            }
        }
    }
}

tasks.register("deployDexed") { dependsOn("jarAndroid"); dependsOn("deploy") }