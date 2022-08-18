import org.gradle.api.artifacts.dsl.DependencyHandler

object AppConfig {
    const val compileSdk = 32
    const val minSdk = 21
    const val targetSdk = 32
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object Versions {
    const val junitVersion = "4.13.2"
    const val extJunitVersion = "1.1.3"
    const val espressoVersion = "3.4.0"
    const val xCoreKtxVersion = "1.7.0"
    const val googleMaterialVersion = "1.6.1"
    const val navigationVersion = "2.3.5"

    const val exoplayerVersion = "2.18.1"
    const val glideVersion = "4.13.2"

    const val lifecycleRuntimeKtxVersion = "2.4.1"
    const val retrofitVersion = "2.9.0"
    const val moshiVersion = "1.13.0"
    const val loggingVersion = "4.10.0"
    const val coroutineVersion = "1.6.3"

    const val hiltAndroidVersion = "2.42"

    const val timberVersion = "5.0.1"
    const val roomVersion = "2.4.2"
    const val dataStoreVersion = "1.0.0"
}

object ClassPath {
    const val hiltGradleClasspath =
        "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroidVersion}"
}

object Dependencies {
    //Core
    const val xCoreKtx = "androidx.core:core-ktx:${Versions.xCoreKtxVersion}"
    private const val lifeCycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycleRuntimeKtxVersion}"

    //Material
    private const val googleMaterial =
        "com.google.android.material:material:${Versions.googleMaterialVersion}"

    //Navigation
    private const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigationVersion}"
    private const val navigationUI =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigationVersion}"

    //Test
    private const val junit = "junit:junit:${Versions.junitVersion}"
    private const val extJunit = "androidx.test.ext:junit:${Versions.extJunitVersion}"
    private const val espressoCore =
        "androidx.test.espresso:espresso-core:${Versions.espressoVersion}"

    //Exoplayer
    private const val exoplayerCore= "com.google.android.exoplayer:exoplayer-core:${Versions.exoplayerVersion}"
    private const val exoplayerDash= "com.google.android.exoplayer:exoplayer-dash:${Versions.exoplayerVersion}"
    private const val exoplayerUi  = "com.google.android.exoplayer:exoplayer-ui:${Versions.exoplayerVersion}"
    private const val exoplayerHls = "com.google.android.exoplayer:exoplayer-hls:${Versions.exoplayerVersion}"

    //Coil Image Loader
    private const val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"

    //Retrofit
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    const val loggingInterceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.loggingVersion}"

    //Coroutines
    const val coroutinesCore =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutineVersion}"
    const val coroutinesAndroid =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutineVersion}"

    //Moshi
    const val moshiCodgen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshiVersion}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"
    const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"

    //Hilt
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltAndroidVersion}"
    const val hiltAndroidCompiler =
        "com.google.dagger:hilt-android-compiler:${Versions.hiltAndroidVersion}"

    //Room
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

    // logging
    const val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"

    //Caching
    const val dataStorePreference =
        "androidx.datastore:datastore-preferences:${Versions.dataStoreVersion}"
    const val dataStoreProto = "androidx.datastore:datastore:${Versions.dataStoreVersion}"

    //Only meant for implementation("...")
    val appLibs = arrayListOf<String>().apply {
        add(xCoreKtx)
        add(googleMaterial)
        add(lifeCycleRuntime)
        add(timber)
        add(navigationFragment)
        add(navigationUI)
        add(exoplayerCore)
        add(exoplayerDash)
        add(exoplayerUi)
        add(exoplayerHls)
        add(glide)
    }

    //Only meant for debugImplementation("...")
    val debugAppLibs = arrayListOf<String>().apply {

    }

    //Only meant for androidTestImplementation("...")
    val androidTestLibs = arrayListOf<String>().apply {
        add(extJunit)
        add(espressoCore)

    }

    //Only meant for testImplementation("...")
    val testLibs = arrayListOf<String>().apply {
        add(junit)
    }


}

//util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.hilt() {
    implementation(listOf(Dependencies.hiltAndroid))
    kapt(listOf(Dependencies.hiltAndroidCompiler))
}

fun DependencyHandler.room() {
    implementation(listOf(Dependencies.roomRuntime, Dependencies.roomKtx))
    kapt(listOf(Dependencies.roomCompiler))
//    add("annotationProcessor", Dependencies.roomCompiler)
}

fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.api(list: List<String>) {
    list.forEach { dependency ->
        add("api", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}

fun DependencyHandler.debugImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("debugImplementation", dependency)
    }
}