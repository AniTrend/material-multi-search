package co.anitrend.multisearch.buildSrc.plugin.strategy

import co.anitrend.multisearch.buildSrc.Libraries
import co.anitrend.multisearch.buildSrc.common.sample
import org.gradle.api.artifacts.dsl.DependencyHandler

internal class DependencyStrategy(
    private val module: String
) {

    private fun DependencyHandler.applyDefaultDependencies() {
        add("implementation", Libraries.JetBrains.Kotlin.stdlib)
        // Testing libraries
        add("testImplementation", Libraries.junit)
        add("testImplementation", Libraries.mockk)
    }

    private fun DependencyHandler.applyTestDependencies() {
        add("androidTestImplementation", Libraries.AndroidX.Test.core)
        add("androidTestImplementation", Libraries.AndroidX.Test.rules)
        add("androidTestImplementation", Libraries.AndroidX.Test.runner)
    }

    private fun DependencyHandler.applyLifeCycleDependencies() {
        add("implementation", Libraries.AndroidX.Lifecycle.liveDataCoreKtx)
        add("implementation", Libraries.AndroidX.Lifecycle.runTimeKtx)
        add("implementation", Libraries.AndroidX.Lifecycle.liveDataKtx)
        add("implementation", Libraries.AndroidX.Lifecycle.extensions)
    }

    private fun DependencyHandler.applyCoroutinesDependencies() {
        if (module == sample)
            add("implementation", Libraries.JetBrains.Coroutines.android)
        add("implementation", Libraries.JetBrains.Coroutines.core)
        add("testImplementation", Libraries.JetBrains.Coroutines.test)
    }

    fun applyDependenciesOn(handler: DependencyHandler) {
        handler.applyDefaultDependencies()
        handler.applyCoroutinesDependencies()
        if (module == sample) {
            handler.applyTestDependencies()
            handler.applyLifeCycleDependencies()
        }
    }
}