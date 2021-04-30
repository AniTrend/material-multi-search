package co.anitrend.multisearch.usecase

import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.doOnEnd
import co.anitrend.multisearch.databinding.SearchItemBinding
import co.anitrend.multisearch.databinding.SearchViewContainerBinding
import co.anitrend.multisearch.extensions.afterMeasured
import co.anitrend.multisearch.model.MultiSearchChangeListener
import co.anitrend.multisearch.model.Search
import co.anitrend.multisearch.presenter.MultiSearchPresenter
import co.anitrend.multisearch.usecase.contract.SearchUseCase
import co.anitrend.multisearch.util.KeyboardUtility
import kotlinx.coroutines.flow.MutableStateFlow

internal class MultiSearchUseCase(
    private val presenter: MultiSearchPresenter,
    private val multiSearchChangeListener: MultiSearchChangeListener?,
    private val mutableSearchFlow: MutableStateFlow<Search?>,
    private val multiSearchContainer: SearchViewContainerBinding
) : SearchUseCase {

    internal var selectedSearchItemTab: SearchItemBinding? = null

    private val searchEnterScrollAnimation by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator.ofInt().apply {
            presenter.configureValueAnimator(
                this
            )
            addUpdateListener {
                multiSearchContainer.horizontalScrollView
                    .smoothScrollTo(it.animatedValue as Int, 0)
            }

            doOnEnd { _ ->
                selectedSearchItemTab?.let {
                    it.searchTermEditText.requestFocus()
                    KeyboardUtility.showKeyboard(it.root.context)
                }
            }
        }
    }

    private val searchCompleteCollapseAnimator by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator.ofInt().apply {
            presenter.configureValueAnimator(
                this
            )
            addUpdateListener { valueAnimator ->
                selectedSearchItemTab?.let {
                    val newViewLayoutParams = it.root.layoutParams
                    newViewLayoutParams.width = valueAnimator.animatedValue as Int
                    it.root.layoutParams = newViewLayoutParams
                }
            }
        }
    }

    private val firstSearchTranslateAnimator by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator.ofFloat().apply {
            presenter.configureValueAnimator(
                this
            )
            addUpdateListener { valueAnimator ->
                multiSearchContainer.horizontalScrollView
                    .translationX = valueAnimator.animatedValue as Float
            }
            doOnEnd { _ ->
                selectedSearchItemTab?.let {
                    it.searchTermEditText.requestFocus()
                    KeyboardUtility.showKeyboard(it.root.context)
                }
            }
        }
    }

    private val indicatorAnimator by lazy(LazyThreadSafetyMode.NONE) {
        ValueAnimator.ofFloat().apply {
            presenter.configureValueAnimator(this)
            addUpdateListener { valueAnimator ->
                multiSearchContainer.viewIndicator.x =
                    valueAnimator.animatedValue as Float
            }
        }
    }

    private fun onSearch(viewWidth: Float, searchViewWidth: Float) {
        selectedSearchItemTab?.root?.afterMeasured {
            val widthWithoutCurrentSearch = widthWithoutCurrentSearch()

            when {
                widthWithoutCurrentSearch == 0 -> {
                    firstSearchTranslateAnimator.setFloatValues(viewWidth, 0f)
                    firstSearchTranslateAnimator.start()
                }
                widthWithoutCurrentSearch < viewWidth -> {
                    val scrollEnterStartValue = 0
                    val scrollEnterEndValue = (
                            multiSearchContainer.layoutItemContainer.measuredWidth - viewWidth
                            ).toInt()
                    searchEnterScrollAnimation.setIntValues(
                        scrollEnterStartValue,
                        scrollEnterEndValue
                    )
                    searchEnterScrollAnimation.start()
                }
                else -> {
                    val scrollEnterStartValue = (widthWithoutCurrentSearch - viewWidth).toInt()
                    val scrollEnterEndValue = (
                            widthWithoutCurrentSearch - viewWidth + searchViewWidth.toInt()
                            ).toInt()
                    searchEnterScrollAnimation.setIntValues(
                        scrollEnterStartValue,
                        scrollEnterEndValue
                    )
                    searchEnterScrollAnimation.start()
                }
            }
        }
    }

    internal fun onSearchCompleted() {
        selectedSearchItemTab?.let {
            if ((it.searchTermEditText.text?.length ?: 0) < 3) {
                removeTab(it)
                return
            }
        }

        selectedSearchItemTab?.let {
            it.searchTermEditText.isFocusable = false
            it.searchTermEditText.isFocusableInTouchMode = false
            it.searchTermEditText.clearFocus()
        }

        selectedSearchItemTab?.let {
            val startWidthValue = it.root.measuredWidth
            val endWidthValue = it.searchTermEditText.measuredWidth +
                    presenter.sizeRemoveIcon +
                    presenter.defaultPadding

            searchCompleteCollapseAnimator.setIntValues(
                startWidthValue,
                endWidthValue
            )
            searchCompleteCollapseAnimator.start()
            val index = multiSearchContainer.layoutItemContainer.childCount - 1
            val text = it.searchTermEditText.text.toString()
            multiSearchChangeListener?.onSearchComplete(index, text)
            mutableSearchFlow.value = Search.Selected(text, index)
        }

        selectedSearchItemTab?.let {
            selectTab(it)
        }
    }

    internal fun onItemClicked(searchItem: SearchItemBinding) {
        if (searchItem != selectedSearchItemTab) {
            val item = Search.Selected(
                index = multiSearchContainer.layoutItemContainer.indexOfChild(searchItem.root),
                text = searchItem.searchTermEditText.text.toString()
            )

            val index = multiSearchContainer.layoutItemContainer.indexOfChild(searchItem.root)
            val text = searchItem.searchTermEditText.text.toString()
            multiSearchChangeListener?.onItemSelected(index,text)
            mutableSearchFlow.value = Search.Selected(text, index)
        }
    }

    private fun widthWithoutCurrentSearch(): Int {
        return when {
            multiSearchContainer.layoutItemContainer.childCount > 1 -> {
                var totalWith = 0
                for (i in 0 until multiSearchContainer.layoutItemContainer.childCount - 1) {
                    totalWith += multiSearchContainer.layoutItemContainer
                        .getChildAt(i).measuredWidth
                }
                totalWith
            }
            else -> 0
        }
    }

    private fun onTabRemoving(newSelectedTabView: SearchItemBinding, selectedIndex: Int) {
        val text = newSelectedTabView.searchTermEditText.text.toString()
        multiSearchChangeListener?.onItemSelected(selectedIndex, text)
        mutableSearchFlow.value = Search.Selected(text, selectedIndex)
        changeSelectedTab(newSelectedTabView)
        selectedSearchItemTab = newSelectedTabView
    }

    override fun addTab(viewWidth: Float, searchViewWidth: Float) {
        multiSearchContainer.layoutItemContainer.addView(
            selectedSearchItemTab?.root
        )
        onSearch(viewWidth, searchViewWidth)
    }

    override fun removeTab(item: SearchItemBinding) {
        val removeIndex = multiSearchContainer.layoutItemContainer.indexOfChild(item.root)
        val currentChildCount = multiSearchContainer.layoutItemContainer.childCount

        when {
            currentChildCount == 1 -> {
                multiSearchContainer.viewIndicator.visibility = View.INVISIBLE
                multiSearchContainer.layoutItemContainer.removeView(item.root)
                presenter.isInSearchMode = false
                selectedSearchItemTab = null
            }
            removeIndex == currentChildCount - 1 -> {
                val index = removeIndex - 1
                val newSelectedTabView = multiSearchContainer
                    .layoutItemContainer.getChildAt(index)
                multiSearchContainer.layoutItemContainer.removeView(item.root)
                onTabRemoving(SearchItemBinding.bind(newSelectedTabView), index)
            }
            else -> {
                val index = removeIndex + 1
                val newSelectedTabView = multiSearchContainer
                    .layoutItemContainer.getChildAt(index)
                multiSearchContainer.layoutItemContainer.removeView(item.root)
                onTabRemoving(SearchItemBinding.bind(newSelectedTabView), index)
            }
        }

        multiSearchChangeListener?.onSearchItemRemoved(removeIndex)
        mutableSearchFlow.value = Search.Removed(removeIndex)
    }

    override fun selectTab(item: SearchItemBinding) {
        val indicatorCurrentXPosition = multiSearchContainer.viewIndicator.x
        val indicatorTargetXPosition = item.root.x
        indicatorAnimator.setFloatValues(
            indicatorCurrentXPosition,
            indicatorTargetXPosition
        )
        indicatorAnimator.start()

        multiSearchContainer.viewIndicator.visibility = View.VISIBLE
        item.searchTermRemoveIcon.visibility = View.VISIBLE
        item.searchTermEditText.alpha = 1f
    }

    override fun deselectTab(item: SearchItemBinding) {
        multiSearchContainer.viewIndicator.visibility = View.INVISIBLE
        item.searchTermRemoveIcon.visibility = View.GONE
        item.searchTermEditText.alpha = 0.5f
    }

    override fun changeSelectedTab(item: SearchItemBinding) {
        selectedSearchItemTab?.let { deselectTab(it) }
        selectedSearchItemTab = item
        selectedSearchItemTab?.let { selectTab(it) }
    }
}