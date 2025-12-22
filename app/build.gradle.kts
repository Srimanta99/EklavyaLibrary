plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

}

android {
    namespace = "com.sm.mylibrary"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sm.mylibrary"
        minSdk = 24
        //noinspection ExpiredTargetSdkVersion
        targetSdk = 35
        versionCode = 2
        versionName = "1.2"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            abiFilters ; "armeabi-v7a"; "arm64-v8a"; "x86"; "x86_64";
        }
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

    viewBinding {
        enable = true
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    kotlin {
        sourceSets.all {
            languageSettings {
                optIn("kotlinx.parcelize.Parcelize")
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.material)
    implementation( libs.sdp.android)
    implementation(libs.google.gson)


    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.lifecycle.livedata.ktx)
    implementation (libs.androidx.lifecycle.viewmodel.ktx)


    implementation (libs.androidx.drawerlayout)
    implementation (libs.material.v1110)
    implementation (libs.androidx.navigation.fragment.ktx.v275)
    implementation (libs.androidx.navigation.ui.ktx.v275)

    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.2")
   // implementation ("com.github.bumptech.glide:glide:4.16.0")
  //  implementation ("com.squareup.picasso:picasso:2.8")
    //implementation ("com.github.smarteist:autoimageslider:1.4.0")
    //implementation ("com.github.smarteist:autoimageslider:1.3.9")
   // implementation ("com.github.bumptech.glide:glide:4.11.0")


    implementation ("com.github.bumptech.glide:glide:4.16.0") // latest stable
    annotationProcessor ("com.github.bumptech.glide:compiler:4.16.0")
    implementation ("com.vanniktech:android-image-cropper:4.5.0")



    implementation ("com.google.android.play:app-update:2.1.0")
    implementation ("com.google.android.play:app-update-ktx:2.1.0")






}