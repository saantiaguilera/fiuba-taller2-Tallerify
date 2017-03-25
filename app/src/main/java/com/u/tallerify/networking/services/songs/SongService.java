package com.u.tallerify.networking.services.songs;

import com.u.tallerify.model.entity.Song;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by saguilera on 3/19/17.
 */
public interface SongService {

    @GET("song/{id}")
    Observable<Song> song(@Path("id") long id);

    @GET("songs/trending")
    Observable<List<Song>> trendingSongs();

}
