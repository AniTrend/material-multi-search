package co.anitrend.multisearch.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import co.anitrend.multisearch.model.Search
import co.anitrend.multisearch.sample.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private fun onSearchStateChanged(search: Search) {
        when (search) {
            is Search.TextChanged ->
                Timber.i("changed: ${search.text}")
            is Search.Removed ->
                Timber.i("removed: ${search.index}")
            is Search.Selected ->
                Timber.i("selected: ${search.index} -> ${search.text}")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        lifecycleScope.launchWhenResumed {
            binding.multiSearch.searchChangeFlow()
                .filterNotNull()
                .onEach {
                    onSearchStateChanged(it)
                }
                .catch { e ->
                    // for any uncaught exceptions
                    Timber.w(e)
                }
                .collect()
        }
    }
}