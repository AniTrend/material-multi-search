package co.anitrend.multisearch.presenter

import android.animation.LayoutTransition
import android.animation.ValueAnimator
import android.content.Context
import android.content.res.Resources
import android.content.res.TypedArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator
import co.anitrend.multisearch.R
import co.anitrend.multisearch.databinding.SearchItemBinding
import co.anitrend.multisearch.extensions.setStyle
import co.anitrend.multisearch.model.MultiSearchConfiguration

internal class MultiSearchPresenter(resources: Resources) {

    private lateinit var multiSearchConfiguration: MultiSearchConfiguration

    var isInSearchMode = false

    val sizeRemoveIcon = resources.getDimensionPixelSize(R.dimen.material_padding)
    val defaultPadding = resources.getDimensionPixelSize(R.dimen.material_padding)

    /**
     * Creates configuration from styles
     *
     * @param typedArray style loaded array
     */
    fun createConfiguration(typedArray: TypedArray) {
        val searchSelectionIndicator = typedArray.getResourceId(
            R.styleable.MultiSearch_searchSelectionIndicator,
            R.drawable.indicator_line
        )
        val searchTextAppearance = typedArray.getResourceId(
            R.styleable.MultiSearch_searchTextAppearance,
            0
        )
        val searchIcon = typedArray.getResourceId(
            R.styleable.MultiSearch_searchIcon,
            R.drawable.ic_round_search_24dp
        )
        val searchRemoveIcon = typedArray.getResourceId(
            R.styleable.MultiSearch_searchRemoveIcon,
            R.drawable.ic_round_clear
        )
        val searchAnimationDuration = typedArray.getInt(
            R.styleable.MultiSearch_searchAnimationDuration,
            500
        )
        typedArray.recycle()

        multiSearchConfiguration = MultiSearchConfiguration(
            searchSelectionIndicator = searchSelectionIndicator,
            searchTextAppearance = searchTextAppearance,
            searchIcon = searchIcon,
            searchRemoveIcon = searchRemoveIcon,
            searchAnimationDuration = searchAnimationDuration
        )
    }

    /**
     * Configure search image view icon
     *
     * @param multiSearchActionIcon view to manipulate
     */
    fun configureSearchAction(
        multiSearchActionIcon: AppCompatImageView?
    ) {
        if (multiSearchActionIcon != null) {
            val searchIcon = ContextCompat.getDrawable(
                multiSearchActionIcon.context,
                multiSearchConfiguration.searchIcon
            )

            multiSearchActionIcon.setImageDrawable(searchIcon)
        }
    }

    /**
     * Configure search indicator icon
     *
     * @param viewIndicator view to manipulate
     */
    fun configureSelectionIndicator(viewIndicator: View?) {
        if (viewIndicator != null) {
            val indicatorDrawable = ContextCompat.getDrawable(
                viewIndicator.context,
                multiSearchConfiguration.searchSelectionIndicator
            )
            viewIndicator.background = indicatorDrawable
        }
    }

    /**
     * Configure search item remove icon
     *
     * @param searchItemAction view to manipulate
     */
    private fun configureSearchItemAction(searchItemAction: AppCompatImageView?) {
        if (searchItemAction != null) {
            val actionDrawable = ContextCompat.getDrawable(
                searchItemAction.context,
                multiSearchConfiguration.searchRemoveIcon
            )

            searchItemAction.setImageDrawable(actionDrawable)
        }
    }

    /**
     * Provides transition options
     */
    fun getContainerTransition(): LayoutTransition? {
        return LayoutTransition().apply {
            disableTransitionType(LayoutTransition.APPEARING)
            disableTransitionType(LayoutTransition.CHANGE_APPEARING)
        }
    }

    /**
     * Inflates search item view
     */
    fun createNewSearchItem(viewGroup: ViewGroup, searchItemWidth: Float): SearchItemBinding {
        val context = viewGroup.context
        val searchItem = SearchItemBinding.inflate(
            LayoutInflater.from(context),
            viewGroup,
            false
        )

        configureSearchItemAction(searchItem.searchTermRemoveIcon)
        val editText = searchItem.searchTermEditText
        editText.setStyle(context, multiSearchConfiguration.searchTextAppearance)
        searchItem.root.layoutParams = LinearLayout.LayoutParams(
            searchItemWidth.toInt(),
            WRAP_CONTENT
        )

        return searchItem
    }

    /**
     * Configures animators duration and interpolator
     */
    fun configureValueAnimator(valueAnimator: ValueAnimator) {
        valueAnimator.duration = multiSearchConfiguration.searchAnimationDuration.toLong()
        valueAnimator.interpolator = LinearOutSlowInInterpolator()
    }
}