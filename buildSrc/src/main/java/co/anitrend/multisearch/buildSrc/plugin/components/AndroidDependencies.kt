package co.anitrend.multisearch.buildSrc.plugin.components

import co.anitrend.multisearch.buildSrc.plugin.strategy.DependencyStrategy
import co.anitrend.multisearch.buildSrc.plugin.extensions.containsAndroidPlugin
import org.gradle.api.Project

internal fun Project.configureDependencies() {
    val dependencyStrategy = DependencyStrategy(project.name)
    dependencies.add("implementation",
        fileTree("libs") {
            include("*.jar")
        }
    )
    dependencyStrategy.applyDependenciesOn(dependencies)
}