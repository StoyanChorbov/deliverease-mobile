import java.util.Properties

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
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
    implementation(libs.mapbox)
    implementation(libs.mapbox.compose)
    implementation(libs.mapbox.search.autofill)
    debugImplementation(libs.compose.ui.tooling)
}