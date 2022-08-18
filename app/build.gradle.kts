plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("name.remal.check-dependency-updates") version "1.5.0"
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "com.app.exoplayerdemo"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary = true
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
    buildFeatures {
        viewBinding = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(project(":data"))
    implementation(project(":domain"))

    androidTestImplementation(Dependencies.androidTestLibs)
    testImplementation(Dependencies.testLibs)
    debugImplementation(Dependencies.debugAppLibs)

    implementation(Dependencies.appLibs)

    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    hilt()

}