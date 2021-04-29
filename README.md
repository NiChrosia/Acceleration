[![Java CI](https://github.com/MEEPofFaith/prog-mats-java/workflows/Java%20CI%20with%20Gradle/badge.svg)](https://github.com/nichrosia/Acceleration/actions)

# Acceleration
Acceleration, but now ported to Kotlin. All future mods/work on this will be done in Kotlin

The old JS version can be found on the `JS` branch, and can be downloaded as a zip from there.

## Download Now!
[![Download](https://img.shields.io/github/v/release/nichrosia/Acceleration?color=gold&include_prereleases&label=DOWNLOAD%20LATEST%20RELEASE&logo=github&logoColor=FCC21B&style=for-the-badge)](https://github.com/nichrosia/Acceleration/releases)

__Or find it in the in-game mod browser, where installation is automatic, and you can ignore everything under this message!__

### Releases
Go to the releases, the latest release will have a `Acceleration.jar` attached to it that you can download. If it does not have it, go to the actions tab, click the latest workflow with a green check, and download the `.JAR` from that.
After you have the `Acceleration.jar`, paste it into your mod folder (locate your mod folder in the "open mod folder" of Mindustry).

## Compiling

First, remove the code that moves the jar file into a seperate directory, that's adapted for my filesystem.  
Then, `gradlew deploy`/`./gradlew deploy`/running the deploy task in IntelliJ,
and move the jar file out of `build/libs` into your mods directory, and open Mindustry.

## Contributors

### Code
- [sk7725](https://github.com/sk7725) *AnimatedItem code*
- [GlennFolker](https://github.com/GlennFolker) *Auto alphableed code*