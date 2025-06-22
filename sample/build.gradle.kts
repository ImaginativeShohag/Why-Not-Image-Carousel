/**
 * Copyright © 2025 Md. Mahmudul Hasan Shohag. All rights reserved.
 */


plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp) // For libraries using KSP
}

android {
    namespace = "org.imaginativeworld.whynotimagecarousel.sample"
    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "org.imaginativeworld.whynotimagecarousel.sample"
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.targetSdk
                .get()
                .toInt()
        versionCode = 1
        // Assuming VERSION_NAME is defined in gradle.properties
        // If not, this will fail. Plan includes checking/adding it to gradle.properties.
        versionName = project.property("VERSION_NAME") as String? ?: "1.0" // Provide a default or handle missing property

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
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
        dataBinding = true // Ensure dataBinding is still needed. If so, keep it.
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Core Kotlin & AndroidX
    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.fragment.ktx)

    // Material Components
    implementation(libs.material)

    // Project specific
    implementation(project(":whynotimagecarousel"))

    // Third-party UI
    implementation(libs.circleindicator)
    implementation(libs.glide)

    // Debug
    debugImplementation(libs.leakcanary.android)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.coreKtx) // Corrected alias from previous step's TOML generation
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.espresso.intents)
}
