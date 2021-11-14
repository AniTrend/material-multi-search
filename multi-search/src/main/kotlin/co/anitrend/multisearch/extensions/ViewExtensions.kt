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

package co.anitrend.multisearch.extensions

import android.view.View
import android.view.ViewTreeObserver

/**
 * Creates a new [ViewTreeObserver.OnGlobalLayoutListener] on this receiver
 *
 * @param action Delegate to execute when the global state is visible
 *
 * @see ViewTreeObserver
 */
internal inline fun View.afterMeasured(crossinline action: View.(View) -> Unit) {
    viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        override fun onGlobalLayout() {
            if (measuredWidth > 0 && measuredHeight > 0) {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                action(this@afterMeasured)
            }
        }
    })
}
