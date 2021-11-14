package co.anitrend.multisearch.buildSrc.plugin.components

import co.anitrend.multisearch.buildSrc.plugin.strategy.DependencyStrategy
import co.anitrend.multisearch.buildSrc.plugin.extensions.implementation
import co.anitrend.multisearch.buildSrc.plugin.extensions.isLibraryModule
import co.anitrend.multisearch.buildSrc.Libraries
import org.gradle.api.Project

internal fun Project.configureDependencies() {
    val dependencyStrategy = DependencyStrategy(project)
    dependencies.implementation(
        fileTree("libs") {
            include("*.jar")
        }
    )
    dependencyStrategy.applyDependenciesOn(dependencies)

    if (isLibraryModule())
        dependencies.implementation(Libraries.AniTrend.Arch.ext)
}