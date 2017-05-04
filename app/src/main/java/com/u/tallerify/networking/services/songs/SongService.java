package com.u.tallerify.networking.services.songs;

import com.u.tallerify.model.Rating;
import com.u.tallerify.model.ResolvedUri;
import com.u.tallerify.model.entity.Song;
import java.util.List;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by saguilera on 3/19/17.
 */
public interface SongService {

    @GET("tracks/{id}")
    Observable<Song> song(@Path("id") long id);

    @GET("tracks/recommended")
    Observable<List<Song>> recommendedSongs();

    @GET("tracks/search")
    Observable<List<Song>> querySongs(@Query("query") String query);

    @POST("tracks/{trackId}/like")
    Observable<Song> likeSong(@Path("trackId") long songId);

    @DELETE("tracks/{trackId}/like")
    Observable<Void> dislikeSong(@Path("trackId") long songId);

    @FormUrlEncoded
    @POST("tracks/{trackId}/popularity")
    Observable<Rating> rateSong(@Path("trackId") long songId, @Field("rate") int rate);

    @GET("tracks/{trackId}/popularity")
    Observable<Rating> rateSong(@Path("trackId") long songId);

    @GET("resolve/{trackId}")
    Observable<ResolvedUri> resolve(@Path("trackId") long songId);

}
