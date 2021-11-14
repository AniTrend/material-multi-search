package co.anitrend.multisearch.buildSrc.module

internal object Modules {

    interface Module {
        val id: String

        /**
         * @return Formatted id of module as a path string
         */
        fun path(): String = ":$id"
    }

    enum class Sample(override val id: String) : Module {
        App("sample")
    }

    enum class Library(override val id: String) : Module {
        MultiSearch("multi-search")
    }
}