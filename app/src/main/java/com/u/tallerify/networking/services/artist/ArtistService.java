package com.u.tallerify.networking.services.artist;

import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Song;
import java.util.List;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by saguilera on 3/19/17.
 */
public interface ArtistService {

    @GET("artists/{id}")
    Observable<Artist> artist(@Path("id") long artistId);

    @GET("artists/recommended")
    Observable<List<Artist>> recommendedArtists();

    @GET("artists")
    Observable<List<Artist>> queryArtists(@Query("name") String query);

    @POST("me/artists/{artistId}/follow")
    Observable<Artist> followArtist(@Path("artistId") long artistId);

    @DELETE("me/artists/{artistId}/follow")
    Observable<Void> unfollowArtist(@Path("artistId") long artistId);

    @GET("artists/{artistId}/tracks")
    Observable<List<Song>> songs(@Path("artistId") long artistId);

}
