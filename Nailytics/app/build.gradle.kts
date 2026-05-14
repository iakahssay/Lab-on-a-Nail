import java.util.Properties
import java.io.FileInputStream

val nixProperties = Properties()
val nixPropertiesFile = rootProject.file("nix.properties")

if (nixPropertiesFile.exists()) {
    nixProperties.load(FileInputStream(nixPropertiesFile))
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.nailytics"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.nailytics"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildFeatures {
            buildConfig = true
        }

        //For Nix setup properties (its option and signature)
        buildConfigField(
            "String",
            "NIX_LICENSE_OPTIONS",
            "\"${nixProperties["NIX_LICENSE_OPTIONS"]}\""
        )

        buildConfigField(
            "String",
            "NIX_LICENSE_SIGNATURE",
            "\"${nixProperties["NIX_LICENSE_SIGNATURE"]}\""
        )
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
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("com.google.android.material:material:1.12.0")

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Nix Universal SDK
    implementation("com.nixsensor:universalsdk:4.2.3")

    // Optional: Enables USB support in Nix Universal SDK
    implementation("com.github.mik3y:usb-serial-for-android:v3.8.1")

}

