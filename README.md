# Taboola Android Assignment – Mobile Product Enablement Manager  
**Author:** Yogev Vahaba  
**Tech Stack:** Kotlin, Android SDK, RecyclerView, OkHttp, Coil, Taboola Android SDK (v4.0.16)

<p align="center">
  <img src="https://github.com/user-attachments/assets/6a09d919-95d9-46e5-88d6-dc618f17afef" width="250" alt="App demo"/>
</p>

## 📱 Project Overview

This project implements a simple Android application that loads content from a remote JSON file and displays it inside a `RecyclerView`.  
In addition, two Taboola widgets are integrated into the list:

- **Cell #3:** Taboola Classic Unit  
- **Cell #10:** Taboola Feed Unit  

The implementation follows Taboola's official *Android SDK Classic* guidelines exactly as described in:  
https://developers.taboola.com/taboolasdk/docs/android-sdk-classic-first-steps

---

## 🧩 Features

### ✔ Load JSON Content  
Remote content is fetched using **OkHttp**, parsed with `JSONArray`, and mapped into `ContentItem` objects.

### ✔ RecyclerView With Mixed Item Types  
The app uses a sealed class (`ListItem`) to define 3 types of rows:

- `ContentItem`
- `TaboolaClassicItem`
- `TaboolaFeedItem`

Two special positions in the list are reserved for Taboola widgets:
- Position **2** → Classic Widget  
- Position **9** → Feed Widget  

### ✔ Taboola Widgets Integration  
- `TBLClassicPage` is created in `MainActivity`  
- Each Taboola-related ViewHolder creates a single `TBLClassicUnit`
- Units are added dynamically to a `FrameLayout`
- `fetchContent()` loads the Taboola content

---

## 🏗 Architecture Breakdown

### `TaboolaApp`
Initializes the Taboola SDK once per app, using:
```kotlin
val publisherInfo = TBLPublisherInfo("sdk-tester-rnd").setApiKey("<API_KEY>")
Taboola.init(publisherInfo)
```

### `MainActivity`
- Creates the `TBLClassicPage`
- Loads JSON on a background thread
- Builds the 10-cell list  
- Passes everything into `ItemsAdapter`

### `ItemsAdapter`
Handles 3 different ViewHolders:
- `ContentViewHolder`
- `TaboolaClassicViewHolder`
- `TaboolaFeedViewHolder`

Each Taboola ViewHolder:
- Creates its widget once
- Adds it into a dedicated `FrameLayout`
- Calls `fetchContent()` to load content

---

## 📂 Project Structure

```
app/
 └── java/com.example.taboolaassignment2/
      ├── MainActivity.kt
      ├── ItemsAdapter.kt
      ├── ListItem.kt
      ├── TaboolaApp.kt
      └── ...
 └── res/layout/
      ├── item_content.xml
      ├── item_taboola_classic.xml
      ├── item_taboola_feed.xml
      └── activity_main.xml
```

---

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone https://github.com/<your-username>/taboola_assignment_PEM_yogev.git
   ```
2. Open in **Android Studio (Giraffe/Koala or newer)**  
3. Build & run on any device with **Android 7.0 (API 24)** or above.

---

## 💡 Notes

- The Taboola SDK is integrated using the latest available version (`4.0.16`).
- Placement configuration (placementName, mode, placementType) matches the assignment specification.
- ViewHolders are optimized so Taboola units are not recreated unnecessarily during scrolling.
