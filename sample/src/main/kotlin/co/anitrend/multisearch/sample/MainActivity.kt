package co.anitrend.multisearch.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import co.anitrend.multisearch.model.MultiSearchChangeListener
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val searchChangeListener =
        object : MultiSearchChangeListener {
            override fun onTextChanged(index: Int, charSequence: CharSequence) {
                Timber.tag(TAG).i("onTextChanged(index: $index, charSequence: $charSequence)")
            }

            override fun onSearchComplete(index: Int, charSequence: CharSequence) {
                Timber.tag(TAG).i("onSearchComplete(index: $index, charSequence: $charSequence)")
            }

            override fun onSearchItemRemoved(index: Int) {
                Timber.tag(TAG).i("onSearchItemRemoved(index: $index)")
            }

            override fun onItemSelected(index: Int, charSequence: CharSequence) {
                Timber.tag(TAG).i("onItemSelected(index: $index, charSequence: $charSequence)")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        multiSearch.setSearchViewListener(searchChangeListener)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}