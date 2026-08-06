plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

val appVersionName = "1.0"
val localBuildNumber = 1
val requestedBuildNumber = providers.gradleProperty("buildNumber").orNull
val appBuildNumber =
    requestedBuildNumber?.toIntOrNull()?.takeIf { it > 0 }
        ?: if (requestedBuildNumber == null) {
            localBuildNumber
        } else {
            error("Gradle property 'buildNumber' must be a positive integer, but was '$requestedBuildNumber'.")
        }

android {
    namespace = "la.devpicon.android.mydrawingsapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "la.devpicon.android.mydrawingsapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = appBuildNumber
        versionName = appVersionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks.register("printVersionName") {
    group = "versioning"
    description = "Prints the intentional Android version name for artifact metadata."
    doLast {
        println(appVersionName)
    }
}

detekt {
    toolVersion = libs.versions.detekt.get()
    buildUponDefaultConfig = true
    config.setFrom(rootProject.file("config/detekt/detekt.yml"))
    parallel = true
    baseline = rootProject.file("config/detekt/baseline.xml")
}

ktlint {
    android.set(true)
    outputToConsole.set(true)
    ignoreFailures.set(false)
    baseline.set(rootProject.file("config/ktlint/baseline.xml"))
    filter {
        exclude("**/generated/**")
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
    implementation(libs.androidx.navigation.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
