import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import java.text.SimpleDateFormat
import java.util.Date

plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

val app_version: String by project
val app_group: String by project
val app_detail_version = "$app_version${getVersionSuffix()}"
version = app_detail_version
group = app_group

configurations {
    shadow {
        implementation.extendsFrom(shadow.get())
    }
}

val shadowJar by tasks.getting(ShadowJar::class) {
    configurations = listOf(project.configurations.shadow.get())
}

tasks.assemble {
    dependsOn(shadowJar)
}

repositories {
    mavenCentral()
    maven(url = "https://maven.minecraftforge.net/")
}

dependencies {
    compileOnly("net.minecraftforge:installer:2.1.4")

    shadow(project(":legacy"))
}

java {
    withSourcesJar()
}

val jar by tasks.getting(Jar::class) {
    manifest {
        attributes["Specification-Title"] = project.name
        attributes["Specification-Vendor"] = "Kamesuta"
        attributes["Specification-Version"] = app_version
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = project.version
        attributes["Implementation-Vendor"] = "Kamesuta"
        attributes["Implementation-Timestamp"] = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date())
        attributes["Automatic-Module-Name"] = "${project.group}.${rootProject.name}".toString().toLowerCase()
        attributes["Multi-Release"] = "true"
        attributes["Main-Class"] = "com.kamesuta.forgeinstallercli.Main"
        attributes["GitCommit"] = "${System.getenv(" GITHUB_SHA ") ?: ""}"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = app_group
            artifactId = rootProject.name
            version = app_detail_version

            from(components["java"])
        }
    }
    repositories {
        maven(url = layout.buildDirectory.dir("maven"))
    }
}
tasks.publish {
    dependsOn(tasks.build)
}

fun getVersionSuffix(): String {
    if (System.getenv("IS_PUBLICATION") != null) {
        return ""
    } else if (System.getenv("GITHUB_RUN_NUMBER") != null && System.getenv("GITHUB_SHA") != null) {
        return "-s." + System.getenv("GITHUB_RUN_NUMBER") + "-" + System.getenv("GITHUB_SHA").substring(0, 7)
    }
    return "-LOCAL"
}
