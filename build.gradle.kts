plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.kiber"
version = "1.1.14"

val jsonPatchVersion = "0.4.16"
val jsonLibVersion = "20231013"
val kotestVersion = "5.8.0"
val kotlinVersion = "1.9.21"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2023.1.5")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("org.jetbrains.kotlin"))
}

dependencies {
    implementation("com.flipkart.zjsonpatch:zjsonpatch:$jsonPatchVersion")
    implementation("org.json:json:$jsonLibVersion")
    compileOnly("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    compileOnly("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")


    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }

    withType<Test>().configureEach {
        useJUnitPlatform()
    }

    patchPluginXml {
        sinceBuild.set("231")
        untilBuild.set("241.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
