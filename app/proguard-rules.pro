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

-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault,Exceptions
-keep class sun.misc.Unsafe { *; }
-keep class com.gem.mediaplayers.data.model.** { <fields>; }
-keep class com.gem.mediaplayers.data.network.request.** { <fields>; }
-keep class com.gem.mediaplayers.data.network.response.** { <fields>; }

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference

#### -- Glide --
-keep class com.bumptech.** {*;}
-keepclassmembers class com.bumptech.** {*;}

#### -- Event Bus --
-keepattributes *Annotation*
-keepclassmembers class * {
   @org.greenrobot.eventbus.Subscribe <methods>;
}
-keep enum org.greenrobot.eventbus.ThreadMode { *; }

-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
  }
-keep,allowobfuscation @interface com.google.gson.annotations.SerializedName

# POJOs used with GSON
# The variable names are JSON key values and should not be obfuscated
-keepclassmembers class com.gem.mediaplayers.data.model.SecurityPoint { <fields>; }

#### -- Google Service --
-keep class com.google.api.** { *; }
-keep class io.grpc.** { *; }
-keep class com.google.rpc.** { *; }
-keep class com.google.auth.oauth2.** { *; }
-keep class com.google.protobuf.** { *; }
-keep class com.google.cloud.speech.** { *; }
-keep class com.google.cloud.texttospeech.** { *; }
-keep class com.google.cloud.translate.** { *; }
-keep public class com.google.cloud.**
-keepclassmembers class * {
  @com.google.cloud.texttospeech <fields>;
}

-keep class javax.crypto.** { *; }
-keep class java.security.** { *; }

#### -- Sql Cipher --
-keep  class net.sqlcipher.** { *; }
-keep  class net.sqlcipher.database.** { *; }

#Keep native
-keepclasseswithmembernames class * {
    native <methods>;
}

-keep public class * extends android.view.View {
 public <init>(android.content.Context);
 public <init>(android.content.Context, android.util.AttributeSet);
 public <init>(android.content.Context, android.util.AttributeSet, int);
 public void set*(...);
}

-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
 public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
 public void *(android.view.View);
}

# For enumeration classes, see http://proguard.sourceforge.net/manual/examples.html#enumerations
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
 public static <fields>;
}

# The support library contains references to newer platform versions.
# Don't warn about those in case this app is linking against an older
# platform version. We know about them, and they are safe.
-dontwarn android.support.**
-dontwarn com.google.ads.**