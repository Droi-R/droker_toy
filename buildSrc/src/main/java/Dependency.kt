object Versions {
    const val HILT_VERSION = "2.50"
    const val COMPOSE_VERSION = "1.5.3"
    const val COMPOSE_COMPILER_VERSION = "1.5.12"
    const val KOTLIN_VERSION = "1.9.10"
}

object Kotlin {
    const val SDK = "org.jetbrains.java:java-stdlib-jdk8:1.5.21"
    const val STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.KOTLIN_VERSION}"
}

object AndroidX {
    const val MATERIAL = "androidx.compose.material:material:1.0.0-rc02"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.1.0"
    const val APP_COMPAT = "androidx.appcompat:appcompat:1.7.0"
    const val LEGACY = "androidx.legacy:legacy-support-v4:1.0.0"
    const val LIFECYCLE_VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
    const val LIFECYCLE_LIVEDATA = "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
    const val ACTIVITY = "androidx.activity:activity-ktx:1.3.1"
    const val FRAGMENT = "androidx.fragment:fragment-ktx:1.3.6"
    const val DATASTORE = "androidx.datastore:datastore-preferences:1.0.0"
}

object KTX {
    const val CORE = "androidx.core:core-ktx:1.12.0"
}

object Google {
    const val MATERIAL = "com.google.android.material:material:1.12.0"
}

object Test {
    const val JUNIT = "junit:junit:4.+"
    const val ANDROID_JUNIT_RUNNER = "AndroidJUnitRunner"
}

object AndroidTest {
    const val EXT_JUNIT = "androidx.test.ext:junit:1.1.3"
    const val TEST_RUNNER = "androidx.test:runner:1.4.0"
    const val ESPRESSO_CORE = "androidx.test.espresso:espresso-core:3.4.0"
}

object DaggerHilt {
    const val DAGGER_HILT = "com.google.dagger:hilt-android:${Versions.HILT_VERSION}"
    const val DAGGER_HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.HILT_VERSION}"
    const val DAGGER_HILT_COMPOSE = "androidx.hilt:hilt-navigation-compose:1.1.0"
}

object Retrofit {
    const val RETROFIT = "com.squareup.retrofit2:retrofit:2.9.0"
    const val CONVERTER_GSON = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val CONVERTER_JAXB = "com.squareup.retrofit2:converter-jaxb:2.9.0"
}

object OkHttp {
    const val OKHTTP = "com.squareup.okhttp3:okhttp:4.9.1"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:4.9.1"
}

object Coroutines {
    const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"
}

object CameraX {
    const val CAMERA_CORE = "androidx.camera:camera-core:1.0.2"
    const val CAMERA_CAMERA2 = "androidx.camera:camera-camera2:1.0.2"
    const val CAMERA_LIFECYCLE = "androidx.camera:camera-lifecycle:1.0.2"
    const val CAMERA_VIEW = "androidx.camera:camera-view:1.0.0-alpha29"
    const val CAMERA_EXTENSIONS = "androidx.camera:camera-extensions:1.0.0-alpha29"
}

object Compose {
    const val MATERIAL3 = "androidx.compose.material3:material3:1.1.0"
    const val ACTIVITY = "androidx.activity:activity-compose:1.7.2"
    const val UI = "androidx.compose.ui:ui:${Versions.COMPOSE_VERSION}"
    const val TOOLING = "androidx.compose.ui:ui-tooling:${Versions.COMPOSE_VERSION}"
    const val FOUNDATION = "androidx.compose.foundation:foundation:${Versions.COMPOSE_VERSION}"
    const val EMBEDDABLE = "org.jetbrains.kotlin:kotlin-compose-compiler-plugin-embeddable:${Versions.KOTLIN_VERSION}"
}

object Room {
    const val RUNTIME = "androidx.room:room-runtime:2.4.3"
    const val COMPILER = "androidx.room:room-compiler:2.4.3"
    const val KTX = "androidx.room:room-ktx:2.4.3"
    const val GUAVA = "androidx.room:room-guava:2.4.3"
    const val TESTING = "androidx.room:room-testing:2.4.3"
}

object Navigation {
    const val FRAGMENT = "androidx.navigation:navigation-fragment-ktx:2.7.5"
    const val UI = "androidx.navigation:navigation-ui-ktx:2.7.5"
    const val COMPOSE = "androidx.navigation:navigation-compose:2.4.0-alpha10"
}

object ThirdParty {
    const val SPLASHSCREEN = "androidx.core:core-splashscreen:1.0.1"
    const val GLIDE = "com.github.bumptech.glide:glide:4.15.1"
    const val GLIDE_COMPILER = "com.github.bumptech.glide:compiler:4.15.1"
}
