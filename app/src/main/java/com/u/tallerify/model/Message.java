package com.u.tallerify.model;

import android.support.annotation.NonNull;

/**
 * Created by saguilera on 4/4/17.
 */
public class Message {

    private long id;
    private @NonNull String text;

    private Message() {}

    public @NonNull String message() {
        return text;
    }

    public long senderId() {
        return id;
    }

}
