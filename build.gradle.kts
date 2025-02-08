import org.jetbrains.intellij.platform.gradle.TestFrameworkType

plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.9.21"
    id("org.jetbrains.intellij.platform") version "2.0.1"
}

group = "com.kiber"
version = "1.2.0"

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


dependencies {
    intellijPlatform {

//        intellijIdeaUltimate("243.15521.24")
//        intellijIdeaCommunity("2024.3.2.1", useInstaller = true)
        intellijIdeaCommunity("2023.3.2", useInstaller = false)

        implementation(kotlin("stdlib"))


        //TODO resolve issues with it !!!
//        bundledPlugin("com.intellij.modules.json")
//        bundledPlugin("com.intellij.jsonpath")

//        pluginVerifier()
        zipSigner()
        instrumentationTools()

        testFramework(TestFrameworkType.Platform)
    }



    //JSON
    implementation("com.flipkart.zjsonpatch:zjsonpatch:$jsonPatchVersion")
    implementation("org.json:json:$jsonLibVersion")

    //XML
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:2.18.2")


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
        untilBuild.set("243.*")
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
