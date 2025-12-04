plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.taboolaassignment2"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.taboolaassignment2"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {
    // Core Android + UI dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // RecyclerView + CardView for list UI
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.cardview)

    // Image loading from the network (Coil)
    implementation(libs.coil)

    // HTTP client for fetching the JSON file (OkHttp)
    implementation(libs.okhttp)

    // Coroutines support (currently used only for future extension)
    implementation(libs.kotlinx.coroutines.android)

    // Taboola Android SDK - required dependency
    implementation("com.taboola:android-sdk:4.0.16")

    // Used to open clicked items in a new browser tab
    implementation("androidx.browser:browser:1.2.0")

    // Test dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

