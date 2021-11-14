package co.anitrend.multisearch.buildSrc.plugin.strategy

import co.anitrend.multisearch.buildSrc.Libraries
import co.anitrend.multisearch.buildSrc.plugin.extensions.isSampleModule
import co.anitrend.multisearch.buildSrc.plugin.extensions.implementation
import co.anitrend.multisearch.buildSrc.plugin.extensions.androidTest
import co.anitrend.multisearch.buildSrc.plugin.extensions.test
import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.DependencyHandler

internal class DependencyStrategy(
    private val project: Project
) {

    private fun DependencyHandler.applyDefaultDependencies() {
        implementation(Libraries.JetBrains.Kotlin.stdlib)
        // Testing libraries
        test(Libraries.junit)
        test(Libraries.mockk)
    }

    private fun DependencyHandler.applyTestDependencies() {
        androidTest(Libraries.AndroidX.Test.core)
        androidTest(Libraries.AndroidX.Test.rules)
        androidTest(Libraries.AndroidX.Test.runner)
    }

    private fun DependencyHandler.applyLifeCycleDependencies() {
        implementation(Libraries.AndroidX.Lifecycle.liveDataCoreKtx)
        implementation(Libraries.AndroidX.Lifecycle.runTimeKtx)
        implementation(Libraries.AndroidX.Lifecycle.liveDataKtx)
        implementation(Libraries.AndroidX.Lifecycle.extensions)
    }

    private fun DependencyHandler.applyCoroutinesDependencies() {
        if (project.isSampleModule())
            implementation(Libraries.JetBrains.Coroutines.android)
        implementation(Libraries.JetBrains.Coroutines.core)
        test(Libraries.JetBrains.Coroutines.test)
    }

    fun applyDependenciesOn(handler: DependencyHandler) {
        handler.applyDefaultDependencies()
        handler.applyCoroutinesDependencies()
        if (project.isSampleModule()) {
            handler.applyTestDependencies()
            handler.applyLifeCycleDependencies()
        }
    }
}