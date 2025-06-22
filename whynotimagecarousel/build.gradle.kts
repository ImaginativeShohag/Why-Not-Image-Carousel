/**
 * Copyright © 2025 Md. Mahmudul Hasan Shohag. All rights reserved.
 */


import com.vanniktech.maven.publish.SonatypeHost // Keep this import
import org.jetbrains.dokka.gradle.DokkaTaskPartial // Moved to top
// Remove import org.jetbrains.dokka.gradle.DokkaPartialTask as it's not needed when using the extension

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.mavenPublish)
    alias(libs.plugins.dokka)
}

android {
    namespace = "org.imaginativeworld.whynotimagecarousel"
    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()
        // targetSdk is not typically set in library defaultConfig; it's usually inherited or set at the variant level.
        // The original had targetSdkVersion 36, so if issues arise, this might be a place to check.
        // For libraries, targetSdk is often implicitly the same as compileSdk.
        // Setting it explicitly in defaultConfig for a library can sometimes have unintended consequences
        // if the consuming app has a different targetSdk.
        // Let's keep it commented out for now as per best practices for libraries.
        // targetSdkVersion(libs.versions.targetSdk.get().toInt())

        // versionCode and versionName are not used in libraries directly for publishing;
        // the version comes from mavenPublishing.coordinates.
    }

    buildTypes {
        release {
            isMinifyEnabled = false // In KTS, it's isMinifyEnabled
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
        targetCompatibility = JavaVersion.toVersion(libs.versions.javaVersion.get())
    }

    kotlinOptions {
        jvmTarget = libs.versions.javaVersion.get()
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)

    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)

    // Glide
    implementation(libs.glide)

    // Circle Indicator
    implementation(libs.circleindicator)
}

// The com.vanniktech.maven.publish plugin usually provides a typed extension.
// If not, one might need:
// import com.vanniktech.maven.publish.MavenPublishBaseExtension
// configure<MavenPublishBaseExtension> { ... }
// Or more specifically:
// import com.vanniktech.maven.publish.MavenPublishPluginExtension
// configure<MavenPublishPluginExtension> { ... }
// For now, assuming the direct `mavenPublishing { ... }` block works as intended by the plugin.

mavenPublishing {
    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, true)
    signAllPublications()

    // The version here (2.1.1) was hardcoded in the original Groovy script.
    // Ideally, this would also come from a single source of truth, like gradle.properties or version catalog if static.
    // For now, keeping it as it was.
    coordinates("org.imaginativeworld.whynotimagecarousel", "whynotimagecarousel", "2.1.1")

    pom {
        name.set("Why Not! Image Carousel!")
        description.set("An easy, super simple and customizable image carousel view for Android.")
        inceptionYear.set("2020")
        url.set("https://github.com/ImaginativeShohag/Why-Not-Image-Carousel")

        licenses {
            license {
                name.set("The Apache Software License, Version 2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                distribution.set("repo")
            }
        }

        developers {
            developer {
                id.set("ImaginativeShohag")
                name.set("Md. Mahmudul Hasan Shohag")
                url.set("https://github.com/ImaginativeShohag")
            }
        }

        scm {
            url.set("https://github.com/ImaginativeShohag/Why-Not-Image-Carousel")
            connection.set("scm:git:git://github.com/ImaginativeShohag/Why-Not-Image-Carousel.git")
            developerConnection.set("scm:git:ssh://git@github.com/ImaginativeShohag/Why-Not-Image-Carousel.git")
        }
    }
}

// Configure Dokka for this specific module (partial documentation)
tasks.named<org.jetbrains.dokka.gradle.DokkaTaskPartial>("dokkaHtmlPartial") {
    pluginsMapConfiguration.set(
        mapOf(
            "org.jetbrains.dokka.base.DokkaBase" to """{
              "footerMessage": "Copyright © 2020 Md. Mahmudul Hasan Shohag"
            }""",
        ),
    )
}
