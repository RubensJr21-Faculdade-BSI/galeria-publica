plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "junior.correa.nascimento.rubens.galeriapublica"
    compileSdk = 34

    defaultConfig {
        applicationId = "junior.correa.nascimento.rubens.galeriapublica"
        minSdk = 29
        targetSdk = 34
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
}

dependencies {

implementation(libs.paging.common.android)
    //    implementation fileTree(dir: "libs", include: ["*.jar"])
    val paging_version = "3.1.1"
    // optional - Guava ListenableFuture support
    implementation("androidx.paging:paging-guava:$paging_version")
    implementation("androidx.paging:paging-runtime:$paging_version")

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}