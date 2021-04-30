package co.anitrend.multisearch.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import co.anitrend.multisearch.contract.SimpleTextWatcher
import co.anitrend.multisearch.databinding.SearchItemBinding
import co.anitrend.multisearch.databinding.SearchViewContainerBinding
import co.anitrend.multisearch.extensions.onSearchAction
import co.anitrend.multisearch.model.MultiSearchChangeListener
import co.anitrend.multisearch.model.Search
import co.anitrend.multisearch.presenter.MultiSearchPresenter
import co.anitrend.multisearch.usecase.MultiSearchUseCase
import co.anitrend.multisearch.util.KeyboardUtility.hideKeyboard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MultiSearchContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var searchViewWidth: Float = 0f
    private var viewWidth: Float = 0f

    internal lateinit var presenter: MultiSearchPresenter

    @Deprecated("Use mutableSearchFlow instead")
    internal var multiSearchChangeListener: MultiSearchChangeListener? = null

    internal val mutableSearchFlow =
        MutableStateFlow<Search?>(null)

    private val searchContainer
            by lazy(LazyThreadSafetyMode.NONE) {
                SearchViewContainerBinding.inflate(
                    LayoutInflater.from(context),
                    this,
                    true
                )
            }

    private val multiSearchUseCase
            by lazy(LazyThreadSafetyMode.NONE) {
                MultiSearchUseCase(
                    presenter,
                    multiSearchChangeListener,
                    mutableSearchFlow,
                    searchContainer
                )
            }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        searchContainer.layoutItemContainer.layoutTransition = presenter.getContainerTransition()
        presenter.configureSelectionIndicator(searchContainer.viewIndicator)
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * you were just added to the view hierarchy, you're called with the old
     * values of 0.
     *
     * @param w Current width of this view.
     * @param h Current height of this view.
     * @param oldw Old width of this view.
     * @param oldh Old height of this view.
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = measuredWidth.toFloat()
        searchViewWidth = viewWidth * WIDTH_RATIO
    }

    internal fun search() {
        if (presenter.isInSearchMode)
            return

        multiSearchUseCase.selectedSearchItemTab?.let {
            multiSearchUseCase.deselectTab(it)
        }

        presenter.isInSearchMode = true

        multiSearchUseCase.selectedSearchItemTab = createNewSearchItem()
        multiSearchUseCase.addTab(viewWidth, searchViewWidth)
    }

    internal fun completeSearch() {
        if (!presenter.isInSearchMode)
            return

        presenter.isInSearchMode = false
        hideKeyboard(context)

        multiSearchUseCase.onSearchCompleted()
    }

    private fun createNewSearchItem(): SearchItemBinding {
        val searchItem =
            presenter.createNewSearchItem(this, searchViewWidth)

        searchItem.searchTermEditText.setOnClickListener {
            if (!presenter.isInSearchMode) {
                multiSearchUseCase.onItemClicked(searchItem)
                multiSearchUseCase.changeSelectedTab(searchItem)
            }
        }

        searchItem.searchTermEditText.doOnTextChanged { text, _, _, count ->
            val changedText = text?.toString().orEmpty()
            multiSearchChangeListener?.onTextChanged(
                searchContainer.layoutItemContainer.childCount - 1,
                changedText
            )
            mutableSearchFlow.value = Search.TextChanged(changedText)
        }

        searchItem.searchTermRemoveIcon.setOnClickListener {
            if (it.isVisible)
                multiSearchUseCase.selectedSearchItemTab?.let { selected ->
                    multiSearchUseCase.removeTab(selected)
                }
        }

        searchItem.searchTermEditText.onSearchAction(
            filter = presenter.isInSearchMode
        ) { completeSearch() }

        return searchItem
    }

    companion object {
        private const val WIDTH_RATIO = 0.85f

        private const val BUNDLE_KEY_SELECTED_INDEX = "_BUNDLE_KEY_SELECTED_INDEX"
        private const val BUNDLE_KEY_SEARCH_ITEMS = "_BUNDLE_KEY_SEARCH_ITEMS"
        private const val BUNDLE_SPARSE_STATE_KEY = "_BUNDLE_SPARSE_STATE_KEY"
        private const val BUNDLE_SUPER_STATE_KEY = "_BUNDLE_SUPER_STATE_KEY"
    }
}