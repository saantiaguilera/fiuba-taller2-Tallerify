package com.u.tallerify.networking.services.album;

import com.u.tallerify.model.entity.Album;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by saguilera on 3/26/17.
 */
public interface AlbumService {

    @GET("albums/{id}")
    Observable<Album> album(@Path("id") long albumId);

    @GET("albums/search")
    Observable<List<Album>> queryAlbums(@Query("query") String query);

}
