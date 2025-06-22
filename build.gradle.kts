/**
 * Copyright © 2025 Md. Mahmudul Hasan Shohag. All rights reserved.
 */


import org.jetbrains.dokka.gradle.DokkaMultiModuleTask // Keep this import

// Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.dokka)
}

// Configure Spotless for the root project (e.g., build.gradle.kts, settings.gradle.kts)
configure<com.diffplug.gradle.spotless.SpotlessExtension> {
    // Optional: If you want to use the same ktlint version across kotlin and kotlinGradle
    // val ktlintVersion = libs.versions.ktlint.get() // Assuming you add ktlint version to toml

    kotlinGradle {
        target("*.gradle.kts", "settings.gradle.kts") // Target root .gradle.kts files and settings.gradle.kts
        targetExclude("**/build/**/*.kts", "build-logic/**/*.gradle.kts") // Exclude build dir & build-logic
        ktlint() // .editorconfig() can be added if you have one at the root
        licenseHeaderFile(rootProject.file("spotless/copyright.kts"), """(^(?![\\/ ]\\*).*$)""") // Regex for KTS license
    }

    // You might still want a general kotlin format for any .kt files directly in the root, if any.
    // kotlin {
    //     target("*.kt")
    //     ktlint()
    //     licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
    // }
}

subprojects {
    // Apply Spotless plugin to each subproject.
    // The plugin's version is managed via the root project's plugins block.
    apply(plugin = "com.diffplug.spotless")

    // Configure Spotless for each subproject
    configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        kotlin {
            target("**/*.kt")
            targetExclude("${layout.buildDirectory.get()}/**/*.kt", "bin/**/*.kt")
            ktlint()
            licenseHeaderFile(rootProject.file("spotless/copyright.kt"))
        }
        kotlinGradle {
            target("build.gradle.kts") // Target build.gradle.kts in each subproject
            ktlint()
            licenseHeaderFile(rootProject.file("spotless/copyright.kts"), """(^(?![\\/ ]\\*).*$)""")
        }
        // Removed problematic format("kts") block. build.gradle.kts for subprojects will be handled by kotlinGradle.
        format("xml") {
            target("**/*.xml")
            targetExclude("**/build/**/*.xml")
            licenseHeaderFile(rootProject.file("spotless/copyright.xml"), "(<[^!?])")
        }
    }
}

tasks.withType<DokkaMultiModuleTask>().configureEach {
    moduleName.set("Why Not! Image Carousel!")
    outputDirectory.set(rootProject.file("docs/api"))
    failOnWarning.set(true)

    // dokkaPlugins is the modern way, but pluginsMapConfiguration might still work.
    // If issues arise, this is an area to check.
    // For now, keeping it similar to the original:
    pluginsMapConfiguration.set(
        mapOf(
            "org.jetbrains.dokka.base.DokkaBase" to """{
              "footerMessage": "Copyright © 2021 Md. Mahmudul Hasan Shohag"
            }""",
        ),
    )
}
