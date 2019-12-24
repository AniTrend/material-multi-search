package co.anitrend.multisearch.model

interface MultiSearchChangeListener {

    /**
     * Called when text has been changed
     *
     * @param index character index that has been changed
     * @param charSequence stream of characters including changes
     */
    fun onTextChanged(index: Int, charSequence: CharSequence)

    /**
     * Called when an IME action of done is triggered
     *
     * @param index character index that has been changed
     * @param charSequence stream of characters including changes
     */
    fun onSearchComplete(index: Int, charSequence: CharSequence)

    /**
     * Called when a search item has been removed
     *
     * @param index
     */
    fun onSearchItemRemoved(index: Int)

    /**
     * Called when a search item has been selected
     *
     * @param index character index that has been changed
     * @param charSequence stream of characters including changes
     */
    fun onItemSelected(index: Int, charSequence: CharSequence)
}