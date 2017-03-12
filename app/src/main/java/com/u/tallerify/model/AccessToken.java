package com.u.tallerify.model;

import android.support.annotation.NonNull;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

/**
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public final class AccessToken implements Serializable {

    private @NonNull String tokenType;

    private @NonNull String accessToken;
    private @NonNull String refreshToken;

    private long createdAt;
    private long expiresIn;

    private AccessToken() {
    }

    public @NonNull String tokenType() {
        return tokenType;
    }

    public @NonNull String accessToken() {
        return accessToken;
    }

    public @NonNull String refreshToken() {
        return refreshToken;
    }

    public boolean hasExpired() {
        return System.currentTimeMillis() / 1000 > createdAt + expiresIn;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccessToken)) {
            return false;
        }

        final AccessToken that = (AccessToken) o;

        if (createdAt != that.createdAt) {
            return false;
        }
        if (expiresIn != that.expiresIn) {
            return false;
        }
        if (!tokenType.equals(that.tokenType)) {
            return false;
        }
        if (!accessToken.equals(that.accessToken)) {
            return false;
        }
        if (!refreshToken.equals(that.refreshToken)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = tokenType.hashCode();
        result = 31 * result + accessToken.hashCode();
        result = 31 * result + refreshToken.hashCode();
        result = 31 * result + (int) (createdAt ^ (createdAt >>> 32));
        result = 31 * result + (int) (expiresIn ^ (expiresIn >>> 32));
        return result;
    }

    public static class ProviderToken implements Serializable {

        private @NonNull String accessToken;
        private @NonNull Provider provider;

        public ProviderToken(@NonNull Provider provider, @NonNull String accessToken) {
            this.provider = provider;
            this.accessToken =  accessToken;
        }

    }

    public enum Provider implements Serializable {
        @SerializedName("google")
        GOOGLE,
        @SerializedName("facebook")
        FACEBOOK
    }

}
