# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/saguilera/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-renamesourcefileattribute SourceFile
-keepattributes SourceFile,LineNumberTable

# Platform calls Class.forName on types which do not exist on Android to determine platform.
-dontnote retrofit2.Platform
# Platform used when running on RoboVM on iOS. Will not be used at runtime.
-dontnote retrofit2.Platform$IOS$MainThreadExecutor
# Platform used when running on Java 8 VMs. Will not be used at runtime.
-dontwarn retrofit2.Platform$Java8
# Retain generic type information for use by reflection by converters and adapters.
-keepattributes Signature
# Retain declared checked exceptions for use by a Proxy instance.
-keepattributes Exceptions

# Local issues to remove warnings/notes that external libraries say its no problem / should take them into account.
-dontwarn retrofit2.adapter.rxjava.CompletableHelper$**
-dontwarn okio.**
-dontwarn com.facebook.**
-dontnote pl.droidsonroids.**
-dontnote libcore.icu.ICU
-dontnote sun.misc.Unsafe
-dontnote org.apache.harmony.xnet.provider.jsse.SSLParametersImpl
-dontnote sun.security.ssl.SSLContextImpl
-dontnote okhttp3.internal.AndroidPlatform
-dontnote android.app.ActivityThread
-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**
-dontnote rx.internal.util.PlatformDependent
-dontnote com.google.gson.internal.UnsafeAllocator
-dontnote com.facebook.**

# Be careful with these, it produces way too much output so im silencing it but it should be payed atention to
-keepattributes EnclosingMethod

-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }

# Firebase rules
-keepnames class com.google.android.gms.dynamic.IObjectWrapper
-keepnames class com.google.android.gms.internal.**
-keepnames class com.google.firebase.FirebaseApp
-keepnames class com.firebase.ui.database.FirebaseRecyclerAdapter
-keepnames class com.google.firebase.database.connection.idl.zz*
-keep class com.google.android.gms.dynamite.DynamiteModule$DynamiteLoaderClassLoader
-keep class com.google.android.gms.dynamite.descriptors.com.google.android.gms.flags.ModuleDescriptor
-keep class com.google.android.gms.dynamite.descriptors.com.google.android.gms.firebase_database.ModuleDescriptor
-dontnote com.google.appengine.api.ThreadManager
-dontnote com.google.android.gms.gcm.GcmListenerService
-dontnote com.google.android.gms.**

# Fresco rules
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

# Application classes that will be serialized/deserialized over Gson
-keep class com.u.tallerify.networking.services.** { *; }
-keep class com.u.tallerify.model.** { *; }

-keep class retrofit2.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-keep class okio.** { *; }
-keep class rx.** { *; }
-keep class com.google.gson.Gson
-keep class com.google.gson.TypeAdapter

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-dontwarn sun.misc.**

-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}

-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-keep public class pl.droidsonroids.gif.GifIOException{<init>(int);}

-keepnames @com.u.tallerify.annotations.KeepName class *
-keepclassmembernames class * {
    @com.u.tallerify.annotations.KeepName *;
}