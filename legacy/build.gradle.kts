plugins {
    id("java")
}

repositories {
    mavenCentral()
    maven(url = "https://maven.minecraftforge.net/")
}

dependencies {
    compileOnly("net.minecraftforge:installer:2.0.24")
}
