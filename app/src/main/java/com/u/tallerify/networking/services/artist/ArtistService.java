package com.u.tallerify.networking.services.artist;

import com.u.tallerify.model.entity.Artist;
import java.util.List;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by saguilera on 3/19/17.
 */
public interface ArtistService {

    @GET("artists/{id}")
    Observable<Artist> artist(@Path("id") long artistId);

    @GET("artists/trending")
    Observable<List<Artist>> trendingArtists();

    @POST("me/artists/{artistId}/follow")
    Observable<Artist> followArtist(@Path("artistId") long artistId);

    @DELETE("me/artists/{artistId}/follow")
    Observable<Void> unfollowArtist(@Path("artistId") long artistId);

}