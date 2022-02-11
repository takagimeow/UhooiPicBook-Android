// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlin_version by extra { "1.6.10" }
    val nav_version by extra { "2.3.5" }
    val hilt_version by extra { "2.40.5" }

    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:7.1.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")

        // Firebase
        classpath("com.google.gms:google-services:4.3.10")
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.8.1")
        classpath("com.google.firebase:perf-plugin:1.3.2")

        classpath("com.google.dagger:hilt-android-gradle-plugin:$hilt_version")

        classpath("com.google.android.gms:oss-licenses-plugin:0.10.4")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}
