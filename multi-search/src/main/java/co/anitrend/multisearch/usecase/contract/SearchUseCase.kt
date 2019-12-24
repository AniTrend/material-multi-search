package co.anitrend.multisearch.usecase.contract

import android.view.View

internal interface SearchUseCase {

    fun removeTab(parent: View)
    fun selectTab(parent: View)
    fun deselectTab(parent: View)
    fun changeSelectedTab(parent: View)
    fun addTab(viewWidth: Float, searchViewWidth: Float)
}