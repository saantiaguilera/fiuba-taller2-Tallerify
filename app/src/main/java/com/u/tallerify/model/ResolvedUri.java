package com.u.tallerify.model;

import android.support.annotation.NonNull;

/**
 * Created by saguilera on 4/8/17.
 */
public final class ResolvedUri {

    private long songId;
    private @NonNull String url;

    private ResolvedUri() {
    }

    public long songId() {
        return songId;
    }

    @NonNull
    public String url() {
        return url;
    }

}
