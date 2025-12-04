package com.example.taboolaassignment2

import android.app.Application
import com.taboola.android.TBLPublisherInfo
import com.taboola.android.Taboola

/**
 * Application class responsible for initializing the Taboola SDK.
 *
 * According to Taboola documentation, the SDK should be initialized
 * once per application lifetime — as early as possible.
 *
 * This class is referenced in AndroidManifest.xml under:
 *      android:name=".TaboolaApp"
 */
object PublisherInfo {
    // Publisher ID provided in the assignment
    const val PUBLISHER = "sdk-tester-rnd"

    // API key for authentication- from github sdk example
    const val API_KEY = "c4c6a04f0b48c7992ff477c89a28ef8d41932d12"
}

class TaboolaApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Define a publisher info object
        val publisherInfo = TBLPublisherInfo(PublisherInfo.PUBLISHER).setApiKey(PublisherInfo.API_KEY)

        // Initialize Taboola SDK as early as possible
        Taboola.init(publisherInfo)
    }
}