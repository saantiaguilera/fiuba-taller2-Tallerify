package com.u.tallerify.mocks;

import static com.u.tallerify.mocks.Song.RESPONSE_SONG;
import static com.u.tallerify.mocks.User.RESPONSE_USER;

/**
 * Created by saguilera on 3/19/17.
 */
public class Playlist {

    public static final String RESPONSE_USER_PLAYLIST = "[\n" +
        "\"id\": 0,\n" +
        "\"name\": \"Playlist 0\",\n" +
        "\"creator\": \n" +
        RESPONSE_USER + "," +
        "\"songs\": [\n" +
        RESPONSE_SONG + "," +
        RESPONSE_SONG + "," +
        RESPONSE_SONG + "," +
        RESPONSE_SONG + "," +
        RESPONSE_SONG + "," +
        RESPONSE_SONG +
        "]\n" +
        "]";

    public static final String RESPONSE_USER_PLAYLISTS = "[\n" +
        RESPONSE_USER_PLAYLIST + ",\n" +
        RESPONSE_USER_PLAYLIST + ",\n" +
        RESPONSE_USER_PLAYLIST + ",\n" +
        RESPONSE_USER_PLAYLIST + ",\n" +
        RESPONSE_USER_PLAYLIST + ",\n" +
        "]";

}
