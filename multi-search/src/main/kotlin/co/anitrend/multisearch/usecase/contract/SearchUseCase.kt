package co.anitrend.multisearch.usecase.contract

import co.anitrend.multisearch.databinding.SearchItemBinding

internal interface SearchUseCase {
    fun removeTab(item: SearchItemBinding)
    fun selectTab(item: SearchItemBinding)
    fun deselectTab(item: SearchItemBinding)
    fun changeSelectedTab(item: SearchItemBinding)
    fun addTab(viewWidth: Float, searchViewWidth: Float)
}