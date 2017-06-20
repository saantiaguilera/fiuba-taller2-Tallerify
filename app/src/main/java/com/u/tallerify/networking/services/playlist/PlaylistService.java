package com.u.tallerify.networking.services.playlist;

import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import java.util.List;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by saguilera on 3/26/17.
 */

public interface PlaylistService {

    @GET("playlists/{id}")
    Observable<Playlist> playlist(@Path("id") long id);

    @PUT("playlists/{playlistId}/tracks/{trackId}")
    Observable<Playlist> addSong(@Path("playlistId") long playlistId, @Path("trackId") long songId);

    @DELETE("playlists/{playlistId}/tracks/{trackId}")
    Observable<Void> removeSong(@Path("playlistId") long playlistId, @Path("trackId") long songId);

    @POST("playlists")
    Observable<Playlist> create(@Body Playlist playlist);

    @DELETE("playlists/{playlistId}")
    Observable<Void> delete(@Path("playlistId") long id);

    @GET("playlists/{playlistId}/tracks")
    Observable<List<Song>> songs(@Path("playlistId") long playlistId);

}
