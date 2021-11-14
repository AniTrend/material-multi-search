package co.anitrend.multisearch.buildSrc

import co.anitrend.multisearch.buildSrc.common.Versions

object Libraries {
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    const val junit = "junit:junit:${Versions.junit}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"

    object Android {

        object Tools {
            private const val version = "7.0.3"
            const val buildGradle = "com.android.tools.build:gradle:$version"
        }
    }

    object AndroidX {

        object AppCompat {
            private const val version = "1.3.1"
            const val appcompat = "androidx.appcompat:appcompat:$version"
            const val appcompatResources = "androidx.appcompat:appcompat-resources:$version"
        }

        object Core {
            private const val version = "1.6.0"
            const val core = "androidx.core:core:$version"
            const val coreKtx = "androidx.core:core-ktx:$version"
        }

        object Lifecycle {
            private const val version = "2.3.1"
            const val extensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
            const val runTimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveDataCoreKtx = "androidx.lifecycle:lifecycle-livedata-core-ktx:$version"
        }

        object Test {
            private const val version = "1.3.0"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"

            object Extension {
                private const val version = "1.1.2"
                const val junit = "androidx.test.ext:junit:$version"
                const val junitKtx = "androidx.test.ext:junit-ktx:$version"
            }
        }
    }

    object AniTrend {

        object Arch {
            private const val version = "1.3.0-rc07"
            const val ext = "com.github.anitrend.support-arch:extension:${version}"
        }
    }

    object JetBrains {
        object Coroutines {
            private const val version = "1.4.2"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
            const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
            const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
        }

        object Kotlin {
            private const val version = "1.4.32"
            const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"

            object Gradle {
                const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
            }
        }
    }
}