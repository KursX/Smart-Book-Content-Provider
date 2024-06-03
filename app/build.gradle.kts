plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.kursx.smartbook.books"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.kursx.smartbook.books"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    val javaVersion = JavaVersion.VERSION_1_8
    compileOptions {
        sourceCompatibility = javaVersion
        targetCompatibility = javaVersion
    }
    kotlinOptions {
        jvmTarget = javaVersion.toString()
    }
    buildFeatures {
        compose = true
    }
}

composeCompiler {
    enableStrongSkippingMode = true
    reportsDestination = layout.buildDirectory.dir("compose_compiler")
}

dependencies {
    implementation(libs.androidx.activity.compose)
    implementation(libs.coil.compose)

    with(compose) {
        implementation(runtime)
        implementation(foundation)
        implementation(material3)
        implementation(preview)
    }
}