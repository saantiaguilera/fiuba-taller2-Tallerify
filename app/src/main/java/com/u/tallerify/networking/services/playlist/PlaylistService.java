package com.u.tallerify.networking.services.playlist;

import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Playlist;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by saguilera on 3/26/17.
 */

public interface PlaylistService {

    @GET("playlists/{id}")
    Observable<Playlist> playlist(@Path("id") long id);

    @POST("playlists/{playlistId}/tracks/{trackId}")
    Observable<Playlist> addSong(@Path("playlistId") long playlistId, @Path("trackId") long songId);

    @DELETE("playlists/{playlistId}/tracks/{trackId}")
    Observable<Void> removeSong(@Path("playlistId") long playlistId, @Path("trackId") long songId);

    @POST("playlists")
    Observable<Playlist> create(@Field("name") @NonNull String name,
        @Field("description") @NonNull String description,
        @Field("ownerId") long ownerId);

    @DELETE("playlists/{playlistId}")
    Observable<Void> delete(@Path("playlistId") long id);

}
