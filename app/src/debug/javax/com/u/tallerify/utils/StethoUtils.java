package com.u.tallerify.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import okhttp3.Interceptor;

/**
 * Created by saguilera on 3/19/17.
 */
public final class StethoUtils {

    public static void initialize(@NonNull Context context) {
        Stetho.initialize(
            Stetho.newInitializerBuilder(context.getApplicationContext())
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context.getApplicationContext()))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context.getApplicationContext()))
                .build());
    }

    public static @Nullable Interceptor httpInterceptor() {
        return new StethoInterceptor();
    }

}
