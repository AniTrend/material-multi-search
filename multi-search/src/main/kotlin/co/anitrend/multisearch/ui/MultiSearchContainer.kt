package co.anitrend.multisearch.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import co.anitrend.multisearch.R
import co.anitrend.multisearch.contract.SimpleTextWatcher
import co.anitrend.multisearch.extensions.onSearchAction
import co.anitrend.multisearch.model.MultiSearchChangeListener
import co.anitrend.multisearch.presenter.MultiSearchPresenter
import co.anitrend.multisearch.usecase.MultiSearchUseCase
import co.anitrend.multisearch.util.KeyboardUtility.hideKeyboard
import kotlinx.android.synthetic.main.search_item.view.*
import kotlinx.android.synthetic.main.search_view_container.view.*

class MultiSearchContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var searchViewWidth: Float = 0f
    private var viewWidth: Float = 0f

    private val multiSearchUseCase by lazy {
        MultiSearchUseCase(
            presenter,
            multiSearchChangeListener,
            this@MultiSearchContainer
        )
    }

    init {
        LayoutInflater.from(context).inflate(
            R.layout.search_view_container,
            this,
            true
        )
    }

    internal lateinit var presenter: MultiSearchPresenter

    var multiSearchChangeListener: MultiSearchChangeListener? = null
        internal set

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        layoutItemContainer.layoutTransition = presenter.getContainerTransition()
        presenter.configureSelectionIndicator(viewIndicator)
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
        if (isInSearchMode)
            return

        multiSearchUseCase.selectedSearchItemTab?.let {
            multiSearchUseCase.deselectTab(it)
        }

        isInSearchMode = true

        multiSearchUseCase.selectedSearchItemTab = createNewSearchItem()
        multiSearchUseCase.addTab(viewWidth, searchViewWidth)
    }

    internal fun completeSearch() {
        if (!isInSearchMode)
            return

        isInSearchMode = false
        hideKeyboard(context)

        multiSearchUseCase.onSearchCompleted()
    }

    private fun createNewSearchItem(): View {
        val searchItem = presenter.createNewSearchItem(this, searchViewWidth)

        searchItem.searchTermEditText.setOnClickListener {
            multiSearchUseCase.onItemClicked(searchItem)
            multiSearchUseCase.changeSelectedTab(searchItem)
        }

        searchItem.searchTermEditText.addTextChangedListener(
            object : SimpleTextWatcher() {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    super.onTextChanged(s, start, before, count)
                    s?.let {
                        multiSearchChangeListener?.onTextChanged(
                            layoutItemContainer.childCount - 1,
                            it
                        )
                    }
                }
            }
        )

        searchItem.searchTermRemoveIcon.setOnClickListener {
            if (it.isVisible)
                multiSearchUseCase.selectedSearchItemTab?.let { selected ->
                    multiSearchUseCase.removeTab(selected)
                }
        }

        searchItem.searchTermEditText.onSearchAction(
            filter = isInSearchMode
        ) { completeSearch() }

        return searchItem
    }

    companion object {
        private const val WIDTH_RATIO = 0.85f
    }
}