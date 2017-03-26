package com.u.tallerify.utils;

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

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = null;
            String responseString;

            final String url = chain.request().url().uri().toString();

            if (url.contains("oauth/token")) {
                responseString = Login.RESPONSE_LOGIN;
            } else if (url.contains("tracks/trending") || url.contains("me/tracks/favorites")) {
                responseString = Song.RESPONSE_TRENDING_SONGS;
            } else if (url.contains("track")) {
                responseString = Song.RESPONSE_SONG;
            } else if (url.contains("artists/trending") || url.contains("me/artists/favorites") || url.contains("user/artist/favorite")) {
                responseString = Artist.RESPONSE_ARTISTS_TRENDING;
            } else if (url.contains("artist")) {
                responseString = Artist.RESPONSE_ARTIST;
            } else if (url.contains("me/playlists")) {
                responseString = Playlist.RESPONSE_USER_PLAYLISTS;
            } else if (url.contains("playlist")){
                responseString = Playlist.RESPONSE_USER_PLAYLIST;
            } else if (url.contains("users/")) {
                responseString = User.RESPONSE_USER;
            } else {
                responseString = "";
            }

            response = new Response.Builder()
                .code(200)
                .message(responseString)
                .request(chain.request())
                .protocol(Protocol.HTTP_1_0)
                .body(ResponseBody.create(MediaType.parse("application/json"), responseString.getBytes()))
                .addHeader("content-type", "application/json")
                .build();

        return response;
    }

}
