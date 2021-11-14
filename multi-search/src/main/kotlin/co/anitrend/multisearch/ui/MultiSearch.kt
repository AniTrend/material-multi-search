/**
 * Copyright 2021 AniTrend
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.anitrend.multisearch.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import co.anitrend.multisearch.R
import co.anitrend.multisearch.databinding.SearchViewBinding
import co.anitrend.multisearch.model.Search
import co.anitrend.multisearch.presenter.MultiSearchPresenter
import kotlinx.coroutines.flow.StateFlow

class MultiSearch @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

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

    override fun onDetachedFromWindow() {
        binding.multiSearchActionIcon.setOnClickListener(null)
        super.onDetachedFromWindow()
    }
}
