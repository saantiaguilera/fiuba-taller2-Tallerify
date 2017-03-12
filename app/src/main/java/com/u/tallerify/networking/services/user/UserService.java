package com.u.tallerify.networking.services.user;

import com.u.tallerify.model.entity.User;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */
public interface UserService {

    @GET("user/")
    Observable<User> currentUser();

}
