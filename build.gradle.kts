
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.10")

        //classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.25")

//        classpath (libs.hilt.android.gradle.plugin)// Add this
    }
}