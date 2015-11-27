# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /usr/local/android-sdk/tools/proguard/proguard-android.txt
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

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-ignorewarnings

#-libraryjars ./libs/android-support-v4.jar
#-libraryjars ./libs/butterknife-7.0.1.jar
#-libraryjars ./libs/fastjson-android-1.1.34.jar
#-libraryjars ./libs/locSDK_5.2.jar
#-libraryjars ./libs/okhttp-2.2.0.jar
#-libraryjars ./libs/picasso-2.5.0.jar
#-libraryjars ./libs/ormlite-android-4.49.jar
#-libraryjars ./libs/ormlite-core-4.49.jar


##################below is for common android
-keep public class **.R$* { public static final int *; }
-keep public class * extends android.app.Activity
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.view.View
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep class * implements android.os.Parcelable {public static final android.os.Parcelable$Creator *; }
##################upper is for common android

-keep public interface com.yuzhi.fine.common.NotObfuscateInterface{public *;}
-keep class * implements com.yuzhi.fine.common.NotObfuscateInterface{
	<methods>;
	<fields>;
}

# OrmLite uses reflection
-keep class com.j256.**
-keepclassmembers class com.j256.** { *; }
-keep enum com.j256.**
-keepclassmembers enum com.j256.** { *; }
-keep interface com.j256.**
-keepclassmembers interface com.j256.** { *; }


# butterknife uses reflection
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}