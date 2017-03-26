package com.u.tallerify.networking.services.user;

import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.model.entity.User;
import java.util.List;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */
public interface UserService {

    @GET("users/me")
    Observable<User> currentUser();

    @GET("users/{id}")
    Observable<User> user(@Path("id") long id);

}
