plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij.platform") version "2.0.1"
}

group = "com.kiber"
version = "1.1.15"

val jsonPatchVersion = "0.4.16"
val jsonLibVersion = "20231013"
val kotestVersion = "5.8.0"
val kotlinVersion = "1.9.21"

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}


// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
intellijPlatform {
    pluginConfiguration {
        ideaVersion {
            sinceBuild = "231"
            untilBuild = "242.*"
        }
    }
    publishing {
        token = System.getenv("PUBLISH_TOKEN")
    }

    signing {
        privateKey = System.getenv("PRIVATE_KEY")
        password = System.getenv("PRIVATE_KEY_PASSWORD")
        certificateChain = System.getenv("CERTIFICATE_CHAIN")
    }
}

dependencies {
    intellijPlatform {
        intellijIdeaCommunity("2024.2.2")
        bundledPlugin("org.jetbrains.kotlin")

        instrumentationTools()
    }

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

}
