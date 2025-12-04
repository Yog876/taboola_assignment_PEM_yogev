package com.example.taboolaassignment2

/**
 * Represents a single row (cell) in the RecyclerView.
 * The UI consists of three possible item types:
 *  1. ContentItem         – regular JSON content loaded from the server
 *  2. TaboolaClassicItem  – a Classic Taboola widget (assigned to cell #3)
 *  3. TaboolaFeedItem     – a Taboola Feed widget (assigned to cell #10)
 *
 * Using a sealed class enforces compile-time safety:
 * the adapter knows exactly which item types exist.
 */
sealed class ListItem {

    /**
     * Regular content item loaded from the JSON feed.
     */
    data class ContentItem(
        val title: String,
        val imageUrl: String
    ) : ListItem()

    /**
     * Placeholder item for the Taboola Classic widget.
     * This is displayed in RecyclerView position 2 (cell #3).
     */
    object TaboolaClassicItem : ListItem()

    /**
     * Placeholder item for the Taboola Feed widget.
     * This is displayed in RecyclerView position 9 (cell #10).
     */
    object TaboolaFeedItem : ListItem()
}
