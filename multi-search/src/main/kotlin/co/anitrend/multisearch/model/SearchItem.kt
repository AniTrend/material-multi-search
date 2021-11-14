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

package co.anitrend.multisearch.model

/**
 * Search model state
 */
sealed class Search {

    /**
     * Called as the text is changing
     *
     * @param text The text of the changes as they happen
     */
    class TextChanged(
        val text: CharSequence
    ) : Search()

    /**
     * Called when a search item has been removed
     *
     * @param index The index of the item that has been removed
     */
    class Removed(
        val index: Int
    ) : Search()

    /**
     * Called when a search item has been selected or IME action of done is triggered
     *
     * @param index The position of the new selection
     * @param text The text of the selected item
     */
    class Selected(
        val text: CharSequence,
        val index: Int
    ) : Search()
}
