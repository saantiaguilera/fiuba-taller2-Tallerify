package com.u.tallerify.utils;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by saguilera on 3/19/17.
 */
public class MockInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }

}
