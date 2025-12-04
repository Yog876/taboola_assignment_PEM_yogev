package com.example.taboolaassignment2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import com.taboola.android.TBLClassicPage
import com.taboola.android.Taboola

/**
 * Main screen of the assignment.
 *
 * Responsibilities:
 *  1. Initialize a Taboola ClassicPage instance for this screen.
 *  2. Fetch JSON content from the remote URL.
 *  3. Build a list of 10 cells where:
 *     - cell #3 (index 2) is a Taboola Classic widget
 *     - cell #10 (index 9) is a Taboola Feed widget
 *  4. Attach the ItemsAdapter to the RecyclerView.
 */
class MainActivity : AppCompatActivity() {

    // Represents the Taboola "page" for this screen.
    private lateinit var classicPage: TBLClassicPage

    // Simple HTTP client for loading the JSON data.
    private val client = OkHttpClient()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create a ClassicPage instance for this Activity,
        // using the pageUrl and pageType from the assignment.
        classicPage = Taboola.getClassicPage(
            "https://blog.taboola.com/", // pageUrl
            "article"                    // pageType
        )

        recyclerView = findViewById(R.id.recyclerView)

        // Load the JSON on a background thread to avoid blocking the UI.
        Thread {
            val contentItems = loadJsonItems()
            val listItems = buildListWithTaboola(contentItems)

            // Switch back to the UI thread to update the RecyclerView.
            runOnUiThread {
                recyclerView.adapter = ItemsAdapter(listItems, classicPage)

            }
        }.start()
    }

    /**
     * Loads the JSON file from the remote URL and maps it
     * to a list of ContentItem objects.
     */
    private fun loadJsonItems(): List<ListItem.ContentItem> {
        val url =
            "https://s3-us-west-2.amazonaws.com/taboola-mobile-sdk/public/home_assignment/data.json"
        val request = Request.Builder().url(url).build()

        client.newCall(request).execute().use { response ->
            val body = response.body?.string() ?: "[]"
            val jsonArray = JSONArray(body)
            val result = mutableListOf<ListItem.ContentItem>()

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)

                // Fallback to "name" or a generic title if "title" is missing.
                val title =
                    obj.optString("title", obj.optString("name", "Item $i"))

                // Fallback from "thumbnail" to "image" if needed.
                val imageUrl =
                    obj.optString("thumbnail", obj.optString("image", ""))

                result.add(ListItem.ContentItem(title, imageUrl))
            }

            return result
        }
    }

    // Builds a fixed list of 10 cells for the RecyclerView.
    private fun buildListWithTaboola(
        contentItems: List<ListItem.ContentItem>
    ): List<ListItem> {
        val items = mutableListOf<ListItem>()

        var contentIndex = 0

        for (position in 0 until 10) {
            when (position) {
                2 -> items.add(ListItem.TaboolaClassicItem) // cell #3: Taboola Classic
                9 -> items.add(ListItem.TaboolaFeedItem)    // cell #10: Taboola Feed
                else -> {
                    if (contentIndex < contentItems.size) {
                        items.add(contentItems[contentIndex])
                        contentIndex++
                    } else {
                        items.add(
                            ListItem.ContentItem(
                                title = "Placeholder $position",
                                imageUrl = ""
                            )
                        )
                    }
                }
            }
        }
        return items
    }
}