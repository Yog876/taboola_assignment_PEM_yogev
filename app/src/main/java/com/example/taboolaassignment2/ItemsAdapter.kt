package com.example.taboolaassignment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.taboola.android.TBLClassicPage
import android.widget.FrameLayout
import com.taboola.android.TBLClassicUnit
import com.taboola.android.annotations.TBL_PLACEMENT_TYPE
import com.taboola.android.listeners.TBLClassicListener

/**
 * RecyclerView adapter that displays:
 *  - Regular content items loaded from JSON
 *  - A single Taboola Classic widget (cell #3)
 *  - A single Taboola Feed widget (cell #10)
 */

class ItemsAdapter(
    private val items: List<ListItem>,
    private val classicPage: TBLClassicPage

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_CONTENT = 1
        private const val VIEW_TYPE_TABOOLA_CLASSIC = 2
        private const val VIEW_TYPE_TABOOLA_FEED = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ListItem.ContentItem -> VIEW_TYPE_CONTENT
            is ListItem.TaboolaClassicItem -> VIEW_TYPE_TABOOLA_CLASSIC
            is ListItem.TaboolaFeedItem -> VIEW_TYPE_TABOOLA_FEED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_CONTENT -> {
                val view = inflater.inflate(R.layout.item_content, parent, false)
                ContentViewHolder(view)
            }
            VIEW_TYPE_TABOOLA_CLASSIC -> {
                val view = inflater.inflate(R.layout.item_taboola_classic, parent, false)
                TaboolaClassicViewHolder(view, classicPage)
            }
            VIEW_TYPE_TABOOLA_FEED -> {
                val view = inflater.inflate(R.layout.item_taboola_feed, parent, false)
                TaboolaFeedViewHolder(view, classicPage)
            }
            else -> throw IllegalArgumentException("Unknown viewType: $viewType")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is ListItem.ContentItem -> (holder as ContentViewHolder).bind(item)
            is ListItem.TaboolaClassicItem -> (holder as TaboolaClassicViewHolder).bind()
            is ListItem.TaboolaFeedItem -> (holder as TaboolaFeedViewHolder).bind()
        }
    }

    /**
     * ViewHolder for regular JSON content items.
     */

    class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

        fun bind(item: ListItem.ContentItem) {
            titleTextView.text = item.title
            if (item.imageUrl.isNotEmpty()) {
                imageView.load(item.imageUrl)
            } else {
                imageView.setImageDrawable(null)
            }
        }
    }

    /**
     * ViewHolder for the Taboola Classic widget (position 2 / cell #3).
     * The Taboola unit is created once and reused while the ViewHolder is alive.
     */
    class TaboolaClassicViewHolder(
        itemView: View,
        private val classicPage: TBLClassicPage
    ) : RecyclerView.ViewHolder(itemView) {

        private val container: FrameLayout =
            itemView.findViewById(R.id.taboolaClassicContainer)

        private var classicUnit: TBLClassicUnit? = null

        fun bind() {
            // Create the Taboola Classic unit only once per ViewHolder instance
            if (classicUnit == null) {
                classicUnit = classicPage.build(
                    itemView.context,
                    "Mid Article",                          // placement name from the assignment
                    "alternating-widget-without-video-1x1", // mode from the assignment
                    TBL_PLACEMENT_TYPE.PAGE_MIDDLE,         // mid-article widget placement
                    object : TBLClassicListener() {}
                )

                container.removeAllViews()
                container.addView(classicUnit)
                classicUnit?.fetchContent()
            }
        }

    }

    /**
     * ViewHolder for the Taboola Feed widget (position 9 / cell #10).
     * The Taboola feed unit is also created once and then reused.
     */
    class TaboolaFeedViewHolder(
        itemView: View,
        private val classicPage: TBLClassicPage
    ) : RecyclerView.ViewHolder(itemView) {

        private val container: FrameLayout =
            itemView.findViewById(R.id.taboolaFeedContainer)

        private var feedUnit: TBLClassicUnit? = null

        fun bind() {
            // Create the Taboola Feed unit only once per ViewHolder instance

            if (feedUnit == null) {
                feedUnit = classicPage.build(
                    itemView.context,
                    "Feed without video",       // placement name for the feed
                    "thumbnails-feed",          // feed mode
                    TBL_PLACEMENT_TYPE.FEED,    // feed placement type
                    object : TBLClassicListener() {}
                )

                container.removeAllViews()
                container.addView(feedUnit)
                feedUnit?.fetchContent()
            }
        }
    }

}
