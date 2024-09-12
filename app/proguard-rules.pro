# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class **.models.** {
    *;
}

-keep class **.mappers.** {
    *;
}

-keep class **.apis.** {
    *;
}

-keep class androidx.lifecycle.ViewModel { *; }


# AndroidX Libraries
-keep class androidx.** { *; }

# Hilt (Dependency Injection)
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-dontwarn dagger.hilt.internal.**
-keep class dagger.hilt.internal.** { *; }
-keepattributes *Annotation*

# Firebase
-keep class com.google.firebase.** { *; }
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.firebase.**
-dontwarn com.google.android.gms.**

# Retrofit and OkHttp
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
-dontwarn retrofit2.**
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes *Annotation*
-keep class **$Gson$Types { *; }
-keep class **$Gson$Preconditions { *; }

# Coil (Image Loading)
-keep class coil.** { *; }
-dontwarn coil.**

# Generative AI
-keep class com.google.generativeai.** { *; }
-dontwarn com.google.generativeai.**

# Lottie (Animation Library)
-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**

# Java Inject (Dependency Injection)
-keep class javax.inject.** { *; }
-keepattributes *Annotation*

# AndroidX Security (Crypto)
-keep class androidx.security.crypto.** { *; }
-dontwarn androidx.security.crypto.**

# Root Beer (Root Detection)
-keep class com.scottyab.rootbeer.** { *; }
-dontwarn com.scottyab.rootbeer.**

# Markwon (Markdown Renderer)
-keep class io.noties.markwon.** { *; }
-dontwarn io.noties.markwon.**

# General Keep Rule for Reflection
-keepclassmembers class ** {
    @com.google.gson.annotations.SerializedName <fields>;
    @javax.inject.Inject <fields>;
}
# Gson
-keep class com.google.gson.** { *; }



# Ensure ProGuard does not remove type parameters from classes in your app that deal with reflection or generics
-keepclassmembers class * {
    <fields>;
    <methods>;
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep any class using reflection (important for Gson and Retrofit)
-keep class * extends java.lang.reflect.Type {
    <fields>;
    <methods>;
}

-keep class ** {
    public <fields>;
    public <methods>;
}

-dontwarn java.lang.management.ManagementFactory
-dontwarn java.lang.management.RuntimeMXBean
-dontwarn com.google.api.client.http.GenericUrl
-dontwarn com.google.api.client.http.HttpHeaders
-dontwarn com.google.api.client.http.HttpRequest
-dontwarn com.google.api.client.http.HttpRequestFactory
-dontwarn com.google.api.client.http.HttpResponse
-dontwarn com.google.api.client.http.HttpTransport
-dontwarn com.google.api.client.http.javanet.NetHttpTransport$Builder
-dontwarn com.google.api.client.http.javanet.NetHttpTransport
-dontwarn org.joda.time.Instant