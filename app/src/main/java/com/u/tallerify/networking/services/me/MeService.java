package com.u.tallerify.networking.services.me;

import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import java.util.List;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by saguilera on 3/26/17.
 */
public interface MeService {

    @GET("artists/me/favorites")
    Observable<List<Artist>> artists();

    @GET("tracks/me/favorites")
    Observable<List<Song>> songs();

    @GET("playlists/me")
    Observable<List<Playlist>> playlists();

}
