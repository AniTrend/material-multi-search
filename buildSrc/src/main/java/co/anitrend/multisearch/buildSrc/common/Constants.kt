package co.anitrend.multisearch.buildSrc.common

import org.gradle.api.Project

internal const val sample = "sample"
internal const val multiSearch = "multi-search"

fun Project.isSampleModule() = name == sample
fun Project.isLibraryModule() = name == multiSearch