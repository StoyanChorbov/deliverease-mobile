plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

val mapboxPublicToken: String = providers.gradleProperty("MAPBOX_PUBLIC_TOKEN").get()
val mapboxNavigationSecret: String = providers.gradleProperty("MAPBOX_NAVIGATION_SECRET").get()

android {
    namespace = "xyz.deliverease.deliverease.android"
    compileSdk = 35
    defaultConfig {
        applicationId = "xyz.deliverease.deliverease.android"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "MAPBOX_ACCESS_TOKEN", "\"$mapboxPublicToken\"")
        buildConfigField("String", "MAPBOX_PUBLIC_TOKEN", "\"$mapboxPublicToken\"")
        buildConfigField("String", "MAPBOX_NAVIGATION_SECRET", "\"$mapboxNavigationSecret\"")
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17)
        }
    }
}

dependencies {
    implementation(projects.shared)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)
    implementation(libs.play.services.location)
    implementation(libs.mapbox) {
        exclude("com.mapbox.common", "okhttp")
    }
    implementation(libs.mapbox.compose) {
        exclude("com.mapbox.common", "okhttp")
    }
    implementation(libs.mapbox.search.base) {
        exclude("com.mapbox.common", "okhttp")
    }
    implementation(libs.mapbox.place.autocomplete) {
        exclude("com.mapbox.common", "okhttp")
    }
    implementation(libs.mapbox.search.android.native) {
        exclude("com.mapbox.common", "okhttp")
    }
    implementation(libs.mapbox.search.autofill) {
        exclude("com.mapbox.common", "okhttp")
    }
    debugImplementation(libs.compose.ui.tooling)
}
