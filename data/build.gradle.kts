plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("name.remal.check-dependency-updates") version "1.5.0"
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk

        testInstrumentationRunner = AppConfig.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":domain"))

    testImplementation(Dependencies.testLibs)

    api(Dependencies.retrofit)
    implementation(Dependencies.loggingInterceptor)

    implementation(Dependencies.moshiConverter)
    implementation(Dependencies.moshi)
    kapt(Dependencies.moshiCodgen)

    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.coroutinesAndroid)

    implementation(Dependencies.dataStorePreference)
    implementation(Dependencies.dataStoreProto)

    hilt()

    room()
}