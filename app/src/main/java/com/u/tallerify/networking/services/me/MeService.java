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

    @GET("me/artists/favorites")
    Observable<List<Artist>> artists();

    @GET("me/tracks/favorites")
    Observable<List<Song>> songs();

    @GET("me/playlists")
    Observable<List<Playlist>> playlists();

}
