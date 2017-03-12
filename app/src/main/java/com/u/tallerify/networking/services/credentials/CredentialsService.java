package com.u.tallerify.networking.services.credentials;

import android.support.annotation.NonNull;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.model.AccessToken.Provider;
import java.io.Serializable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Api Service for the login endpoints
 *
 * Created by saguilera on 3/12/17.
 */
@SuppressWarnings("unused")
public interface CredentialsService {

    String PATH_URL = "oauth/token";

    String GRANT_TYPE_CREATE = "assertion";
    String GRANT_TYPE_REFRESH = "refresh_token";
    String GRANT_TYPE_LOGIN = "assertion";

    /**
     * Post to create an AccessToken.
     *
     * #NO AUTH
     *
     * @return Application Access Token.
     */
    @POST(PATH_URL)
    Observable<AccessToken> create(@Body CreateCredentialForm body);

    /**
     * Post to refresh an access token
     *
     * #NO AUTH
     *
     * @return Application refreshed Access Token
     */
    @POST(PATH_URL)
    Observable<AccessToken> refresh(@Body RefreshCredentialForm body);

    /**
     * Class for the with request body
     */
    class CreateCredentialForm implements Serializable {

        private @NonNull String grantType = GRANT_TYPE_CREATE;
        private @NonNull String assertion;
        private @NonNull Provider provider;

        public CreateCredentialForm(@NonNull String accessToken,
            @NonNull Provider provider) {
            this.assertion = accessToken;
            this.provider = provider;
        }

    }

    /**
     * Class for the refresh request body
     */
    class RefreshCredentialForm implements Serializable {

        private @NonNull String grantType = GRANT_TYPE_REFRESH;
        private @NonNull String refreshToken;

        public RefreshCredentialForm(@NonNull String refreshToken) {
            this.refreshToken = refreshToken;
        }

    }

}
