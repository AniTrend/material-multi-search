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