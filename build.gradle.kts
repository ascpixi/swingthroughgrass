import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("kr.entree.spigradle") version "1.2.4"
    idea
}

group = "com.github.ascpixi"
version = "2.0"

spigot {
    name = "SwingThroughGrass"
    description = "Allows players to swing through grass with swords and axes."
    authors = listOf("ascpixi")
    apiVersion = "1.16"
    main = "com.github.ascpixi.stg.SwingThroughGrass"
    prefix = "SwingThroughGrass"
    website = "https://github.com/ascpixi/swingthroughgrass"

    commands {
        create("swingthroughgrass") {
            aliases = listOf("stg")
            description = "Manages the SwingThroughGrass plugin."
            usage = "/swingthroughgrass [reload | version]"
            permission = "swingthroughgrass.command"
        }
    }

    permissions {
        create("swingthroughgrass.command") {
            description = "Allows the use of the main /stg plugin management command"
            defaults = "op"
        }
    }
}

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/public")
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    shadow("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    implementation(kotlin("stdlib"))
    implementation("org.bstats:bstats-bukkit:3.0.2")
}

tasks.withType<ShadowJar> {
    relocate("kotlin", "com.github.ascpixi.stg.shadow.kotlin")
    relocate("org.bstats", "com.github.ascpixi.stg.shadow.bstats")

    exclude("org/intellij/lang/annotations/**")
    exclude("org/jetbrains/annotations/**")

    minimize()
}
