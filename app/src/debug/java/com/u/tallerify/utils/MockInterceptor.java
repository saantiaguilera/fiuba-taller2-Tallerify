package com.u.tallerify.utils;

import android.util.Log;
import com.u.tallerify.mocks.Album;
import com.u.tallerify.mocks.Artist;
import com.u.tallerify.mocks.Login;
import com.u.tallerify.mocks.Playlist;
import com.u.tallerify.mocks.Song;
import com.u.tallerify.mocks.User;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by saguilera on 3/19/17.
 */
public class MockInterceptor implements Interceptor {

    private static final boolean ENABLED = false;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response;
        String responseString = null;

        if (ENABLED) {
            final String url = chain.request().url().uri().toString();

            if (url.contains("popularity")) {
                responseString = Song.RESPONSE_RATING;
            } else if (url.contains("resolve/")) {
                responseString = Song.RESPONSE_RESOLVED_URI;
            } else if (url.contains("tracks/recommended") || url.contains("me/tracks/favorites")
                || url.contains("tracks/search") || url.contains("/activity") &&
                (!url.contains("playlists/") && !url.contains("/tracks/"))) {
                responseString = Song.RESPONSE_TRENDING_SONGS;
            } else if (url.contains("track")) {
                responseString = Song.RESPONSE_SONG;
            } else if (url.contains("artists/recommended") || url.contains("me/artists/favorites")
                || url.contains("artists/search")
                || url.contains("user/artist/favorite")) {
                responseString = Artist.RESPONSE_ARTISTS_TRENDING;
            } else if (url.contains("artist")) {
                responseString = Artist.RESPONSE_ARTIST;
            } else if (url.contains("me/playlists")) {
                responseString = Playlist.RESPONSE_USER_PLAYLISTS;
            } else if (url.contains("playlist")) {
                responseString = Playlist.RESPONSE_USER_PLAYLIST;
            } else if (url.contains("users/search")) {
                responseString = User.RESPONSE_USERS;
            } else if (url.contains("albums/search")) {
                responseString = Album.RESPONSE_ALBUMS;
            } else if (url.contains("albums")) {
                responseString = Album.RESPONSE_ALBUM;
            }
        }

        if (responseString != null) {
            response = new Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();
        } else {
            Log.w("Request", "Starting request...");
            response = chain.proceed(chain.request());
            Log.w("Request", "Finished request. Response status code: " + response.code());
        }

        return response;
    }

}
