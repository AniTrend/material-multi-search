package co.anitrend.multisearch.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import co.anitrend.multisearch.R
import co.anitrend.multisearch.databinding.SearchViewBinding
import co.anitrend.multisearch.model.MultiSearchChangeListener
import co.anitrend.multisearch.model.Search
import co.anitrend.multisearch.presenter.MultiSearchPresenter
import kotlinx.coroutines.flow.StateFlow

class MultiSearch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr) {

    private val binding
            by lazy(LazyThreadSafetyMode.NONE) {
                SearchViewBinding.inflate(
                    LayoutInflater.from(context),
                    this,
                    true
                )
            }

    private val presenter
            by lazy(LazyThreadSafetyMode.NONE) {
                MultiSearchPresenter(resources)
            }

    init {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs, R.styleable.MultiSearch, defStyleAttr, defStyleAttr
        )
        presenter.createConfiguration(typedArray)
        onInitializeComponent()
    }

    private fun onInitializeComponent() {
        presenter.configureSearchAction(
            binding.multiSearchActionIcon
        )

        binding.multiSearchContainer.presenter = presenter
        binding.multiSearchActionIcon.setOnClickListener {
            if (!presenter.isInSearchMode)
                binding.multiSearchContainer.search()
            else
                binding.multiSearchContainer.completeSearch()
        }
        binding.multiSearchContainer
    }

    /**
     * Provides access to observe changes on this widget
     */
    fun searchChangeFlow(): StateFlow<Search?> {
        return binding.multiSearchContainer.mutableSearchFlow
    }

    @Deprecated(
        "Migrate to flows for better lifecycle management",
        ReplaceWith(
            "searchChangeFlow().filterNotNull().onEach { }.collect()",
            "kotlinx.coroutines.flow.StateFlow",
            "kotlinx.coroutines.flow.collect",
            "kotlinx.coroutines.flow.onEach",
            "kotlinx.coroutines.flow.filterNotNull",
            "co.anitrend.multisearch.model.Search"
        )
    )
    fun setSearchViewListener(multiSearchChangeListener: MultiSearchChangeListener) {
        binding.multiSearchContainer.multiSearchChangeListener = multiSearchChangeListener
    }

    override fun onDetachedFromWindow() {
        binding.multiSearchContainer.multiSearchChangeListener = null
        binding.multiSearchActionIcon.setOnClickListener(null)
        super.onDetachedFromWindow()
    }
}