package com.u.tallerify.utils;

import com.facebook.internal.CallbackManagerImpl;
import javax.annotation.Nullable;

/**
 * Created by saguilera on 3/17/17.
 */
public enum RequestCodes {

    FACEBOOK_LOGIN(CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()),
    REQUEST_LOCATION_PERMISSION(1);

    private int value;

    RequestCodes(int value) {
        this.value = value;
    }

    public int value() {
        return value;
    }

    public static @Nullable
    RequestCodes valueOf(int value) {
        if (value == FACEBOOK_LOGIN.value)
            return FACEBOOK_LOGIN;

        return null;
    }

}
