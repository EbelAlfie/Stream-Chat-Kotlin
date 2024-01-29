import java.io.FileInputStream
import java.util.Properties

plugins {
  kotlin("kapt")
  id("com.android.application")
  id("org.jetbrains.kotlin.android")
  id("com.google.dagger.hilt.android")
  id("kotlin-parcelize")
  id("kotlin-kapt")
}

val localProperty = Properties().apply {
  load(FileInputStream(File(rootProject.rootDir, "local.properties")))
}

android {
  namespace = "com.example.streamchattest"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.example.streamchattest"
    minSdk = 21
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    buildConfigField("String", "STREAM_KEY", localProperty.getProperty("stream.key"))
    buildConfigField("String", "STREAM_SCRT", localProperty.getProperty("stream.secret"))
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
    viewBinding = true
    buildConfig = true
  }
}

dependencies {

  implementation ("io.getstream:stream-chat-android-ui-components:6.0.13")

  implementation("com.google.dagger:hilt-android:2.48")
  kapt("com.google.dagger:hilt-android-compiler:2.48")

  implementation ("androidx.fragment:fragment-ktx:1.5.6")
  implementation("androidx.core:core-ktx:1.12.0")
  implementation("androidx.appcompat:appcompat:1.6.1")
  implementation("com.google.android.material:material:1.11.0")
  testImplementation("junit:junit:4.13.2")
  androidTestImplementation("androidx.test.ext:junit:1.1.5")
  androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}