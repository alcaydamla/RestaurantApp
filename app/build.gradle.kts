plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize") // Parcelize desteği
    id("kotlin-kapt")
}

android {
    namespace = "com.defneersungur.a487project"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.defneersungur.a487project"
        minSdk = 26
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        viewBinding = true // View Binding aktif
    }
}

dependencies {
    // AndroidX Kütüphaneleri
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)

    // Room (annotationProcessor ile kullanma)
    // Room
    val room_version = "2.5.2"
    implementation("androidx.room:room-runtime:$room_version")
    //kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    implementation(libs.androidx.espresso.core)
    annotationProcessor("androidx.room:room-compiler:2.5.2") // Room için annotationProcessor

    // Retrofit (JSON İşleme)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Glide (Görsel Yükleme, annotationProcessor ile kullanma)
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0") // Glide için annotationProcessor kullanımı

    // Test Kütüphaneleri
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    val worker_version="2.8.1"
    implementation ("androidx.work:work-runtime:$worker_version")
    implementation ("androidx.work:work-runtime-ktx:2.8.1")

}
