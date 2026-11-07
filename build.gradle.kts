import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilerExecutionStrategy

plugins {
    id("fabric-loom") version "1.9.2"
    kotlin("jvm") version "2.1.20"
}

fun prop(name: String): String = project.property(name) as String

version = prop("mod_version")
group = prop("mod_group_id")

base {
    archivesName.set("${prop("mod_id")}-fabric-${prop("minecraft_version")}")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(21))

kotlin {
    jvmToolchain(21)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerExecutionStrategy.set(KotlinCompilerExecutionStrategy.IN_PROCESS)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
        freeCompilerArgs.add("-Xjvm-default=all")
    }
}

repositories {
    maven {
        name = "Modrinth"
        url = uri("https://api.modrinth.com/maven")
        content { includeGroup("maven.modrinth") }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${prop("minecraft_version")}")
    mappings(loom.officialMojangMappings())
    modImplementation("net.fabricmc:fabric-loader:${prop("fabric_loader_version")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${prop("fabric_api_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${prop("fabric_kotlin_version")}")
    modImplementation("maven.modrinth:glitchcore:${prop("glitchcore_maven")}")
    modImplementation("maven.modrinth:serene-seasons:${prop("serene_seasons_maven")}")
    // GlitchCore ships NightConfig inside its jar. The compiler still needs the types.
    compileOnly("com.electronwill.night-config:core:3.6.7")
    compileOnly("com.electronwill.night-config:toml:3.6.7")
    // GlitchCore keeps these inside its own jar. Dev launches do not open that nest.
    localRuntime("com.electronwill.night-config:core:3.6.7")
    localRuntime("com.electronwill.night-config:toml:3.6.7")
    localRuntime("net.jodah:typetools:0.6.3")
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.processResources {
    val version = project.version.toString()
    inputs.property("version", version)
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    filesMatching("fabric.mod.json") {
        filter { line -> line.replace("\${version}", version) }
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

loom {
    runs {
        named("client") {
            vmArg("-Xms256m")
            vmArg("-Xmx768m")
            vmArg("-XX:+UseSerialGC")
        }
        named("server") {
            programArg("--nogui")
            programArg("--port")
            programArg("25567")
            vmArg("-Xms128m")
            vmArg("-Xmx512m")
            vmArg("-XX:+UseSerialGC")
        }
    }
}
