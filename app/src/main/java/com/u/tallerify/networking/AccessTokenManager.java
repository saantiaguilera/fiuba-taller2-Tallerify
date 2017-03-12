package com.u.tallerify.networking;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.u.tallerify.model.AccessToken;

/**
 * Access token manager for credentials.
 *
 * This has the ability to read the current token (if existent) and to write a new one.
 *
 * Created by saguilera on 3/12/17.
 */
public class AccessTokenManager {

    /**
     * Shared prefs values
     */
    private static final String SHARED_PREFERENCES_DIR = AccessTokenManager.class + "_SHARED_PREFERENCES";
    private static final String SHARED_PREFERENCIES_KEY = "ACCESS_TOKEN";

    /**
     * Singleton instance to avoid constant creation of instances,
     * since it will be used constantly for each request.
     */
    private static final @NonNull AccessTokenManager instance = new AccessTokenManager();

    /**
     * Current token to avoid having to read from shared preferences
     * each time
     */
    private @Nullable AccessToken token;

    /**
     * Getter of an instance
     * @return singleton instance of the class
     */
    @NonNull
    public static AccessTokenManager getInstance() {
        return instance;
    }

    /**
     * Reads the current access token saved (if existent) and returns it
     *
     * @param context of the scope that wants to read
     * @return access token, if persisted. Else null
     */
    public @Nullable AccessToken read(@NonNull Context context) {
        if (token != null)
            return token;

        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DIR, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(SHARED_PREFERENCIES_KEY, null);

        if (json == null)
            return null;

        return token = new Gson().fromJson(json, AccessToken.class);
    }

    /**
     * Write a new access token and persist it
     *
     * note: This will override any old token
     * @param context of the scope that wants to write
     * @param token to persist
     */
    public void write(@NonNull Context context, @NonNull AccessToken token) {
        this.token = token;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DIR, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(SHARED_PREFERENCIES_KEY, new Gson().toJson(token)).apply();
    }

    /**
     * Clear the access token.
     *
     * @param context of the scope that wants to write
     */
    public void clear(@NonNull Context context) {
        this.token = null;
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES_DIR, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(SHARED_PREFERENCIES_KEY).apply();
    }

}
