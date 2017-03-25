package com.u.tallerify.networking.services.artist;

import com.u.tallerify.model.entity.Artist;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by saguilera on 3/19/17.
 */
public interface ArtistService {

    @GET("artist/{id}")
    Observable<Artist> artist(@Path("id") long artistId);

    @GET("artists/trending")
    Observable<List<Artist>> trendingArtists();

}
