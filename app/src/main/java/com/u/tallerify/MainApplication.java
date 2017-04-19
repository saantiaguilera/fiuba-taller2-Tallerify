package com.u.tallerify;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.memory.PoolConfig;
import com.facebook.imagepipeline.memory.PoolFactory;
import com.miguelbcr.ui.rx_paparazzo.RxPaparazzo;
import com.squareup.leakcanary.LeakCanary;
import com.u.tallerify.utils.StethoUtils;
import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

public class MainApplication extends Application implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this))
            return;

        LeakCanary.install(this);

        registerActivityLifecycleCallbacks(this);
        initializeFresco();
        StethoUtils.initialize(this);
        RxPaparazzo.register(this);
    }

    private void initializeFresco() {
        PoolFactory poolFactory = new PoolFactory(PoolConfig.newBuilder().build());

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(poolFactory.getFlexByteArrayPoolMaxNumThreads());

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .dispatcher(dispatcher)
            .build();

        final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();
        final int MAX_DISK_CACHE_SIZE = 40 * ByteConstants.MB;
        final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;
        final String CACHE_DIR = "fresco_pipeline_cache";

        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
            MAX_MEMORY_CACHE_SIZE, // Max total size of elements in the cache
            Integer.MAX_VALUE,                     // Max entries in the cache
            MAX_MEMORY_CACHE_SIZE, // Max total size of elements in eviction queue
            Integer.MAX_VALUE,                     // Max length of eviction queue
            Integer.MAX_VALUE);                    // Max cache entry size

        ImagePipelineConfig pipelineConfig =
            OkHttpImagePipelineConfigFactory.newBuilder(this, okHttpClient)
                .setPoolFactory(poolFactory)
                .setDownsampleEnabled(true)
                .setBitmapMemoryCacheParamsSupplier(
                new Supplier<MemoryCacheParams>() {
                    public MemoryCacheParams get() {
                        return bitmapCacheParams;
                    }
                })
                .setMainDiskCacheConfig(
                    DiskCacheConfig.newBuilder(this)
                        .setBaseDirectoryPath(getCacheDir())
                        .setBaseDirectoryName(CACHE_DIR)
                        .setMaxCacheSize(MAX_DISK_CACHE_SIZE)
                        .build())
                .build();

        Fresco.initialize(this, pipelineConfig);
    }

    @Override
    public void onActivityStopped(final Activity activity) {
        // We dont have to do weird counts because its monolithic activity this app.
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    @Override
    public void onActivityCreated(final Activity activity, final Bundle savedInstanceState) {}
    @Override
    public void onActivityStarted(final Activity activity) {}
    @Override
    public void onActivityResumed(final Activity activity) {}
    @Override
    public void onActivityPaused(final Activity activity) {}
    @Override
    public void onActivitySaveInstanceState(final Activity activity, final Bundle outState) {}
    @Override
    public void onActivityDestroyed(final Activity activity) {}

}