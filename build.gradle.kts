import org.gradle.internal.declarativedsl.schemaBuilder.isValidNestedModelType

plugins {
    id("java-library")
    id("maven-publish")
    id("com.gradleup.shadow") version "9.4.1"
}

group = "fr.maxlego08.zvoteparty"
version = "1.1.0.5"
description = "zVoteParty-1.1.0.5"
java.sourceCompatibility = JavaVersion.VERSION_25

repositories {
    mavenLocal()
    maven {
        url = uri("https://jitpack.io")
    }

    maven {
        url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    maven {
        url = uri("https://libraries.minecraft.net/")
    }

    maven {
        url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    }

    maven {
        url = uri("https://repo.groupez.dev/releases")
    }

    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

/*dependencies {
    api("redis.clients:jedis::7.2.0")
    //api("com.github.technicallycoded:folialib")
    implementation("com.tcoded:FoliaLib:0.5.1")
    compileOnly("io.papermc.paper:paper-api:26.1.2.build.+")
    //compileOnly("com.github.nuvotifier:nuvotifier:nuvotifier.api")
    //compileOnly("com.github.nuvotifier.nuvotifier.nuvotifier.bukkit")
    compileOnly("com.vexsoftware:nuvotifier-universal:version")
    compileOnly("com.mojang.authlib")
    compileOnly("fr.maxlego08.menu.zmenu.api")
    compileOnly("me.clip.placeholde
    compileOnly("commons.lang.commons.lang")
}

 */
dependencies {
    implementation("org.spigotmc:spigot-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("redis.clients:jedis:5.1.3")
    implementation("com.github.NuVotifier.NuVotifier:nuvotifier-api:2.7.2")
    implementation("com.github.NuVotifier.NuVotifier:nuvotifier-bukkit:2.7.2")
    implementation("com.mojang:authlib:4.0.43")
    implementation("fr.maxlego08.menu:zmenu-api:1.1.1.4")
    implementation("me.clip:placeholderapi:2.11.6")
    implementation("commons-lang:commons-lang:2.6")
    compileOnly("com.github.technicallycoded:FoliaLib:0.4.3")
}

tasks.shadowJar {
    configurations = project.configurations.runtimeClasspath.map { setOf(it) }

    dependencies {
        // Only merge bStats into the final jar, no other dependencies
    }

    // Relocate bStats into the plugin's package to avoid conflicts with other
    // plugins using bStats
    //relocate("org.bstats", project.group.toString())
}



publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
