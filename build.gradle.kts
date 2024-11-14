
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
buildscript {
    repositories {
        google()  // Ensure this is added
        mavenCentral()
        jcenter()
        maven { url;"https://jitpack.io" }
    }
    dependencies {
        classpath ("com.google.gms:google-services:4.3.14")  // Google services classpath
    }
}
