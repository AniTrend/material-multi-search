// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(co.anitrend.multisearch.buildSrc.Libraries.Android.Tools.buildGradle)
        classpath(co.anitrend.multisearch.buildSrc.Libraries.JetBrains.Kotlin.Gradle.plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            setUrl("https://www.jitpack.io")
        }
    }
}

tasks.create("clean", Delete::class) {
    delete(rootProject.buildDir)
}

/*
tasks.named(
    "dependencyUpdates",
    DependencyUpdatesTask::class.java
).configure {
    checkForGradleUpdate = false
    outputFormatter = "json"
    outputDir = "build/dependencyUpdates"
    reportfileName = "report"
    resolutionStrategy {
        componentSelection {
            all {
                val blackList = listOf("preview", "m", "aplpha", "beta")
                val reject = blackList.map { qualifier ->
                    val pattern = "(?i).*[.-]$qualifier[.\\d-]*"
                    Regex(pattern, RegexOption.IGNORE_CASE)
                }.any { it.matches(candidate.version) }
                if (reject)
                    reject("${blackList.joinToString()} releases not wanted")
            }
        }
    }
}*/
