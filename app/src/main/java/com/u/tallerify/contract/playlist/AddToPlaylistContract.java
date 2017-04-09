package com.u.tallerify.contract.playlist;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.model.entity.Playlist;
import java.util.List;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */

public interface AddToPlaylistContract {

    interface View extends ContractView {

        void setPlaylists(@NonNull List<String> names);
        @NonNull Observable<Integer> observePlaylistPositionClicks();
        @NonNull Observable<String> observePlaylistCreations();
        void setEditable(boolean editable);

    }

    interface Presenter extends ContractPresenter {

        @NonNull Observable<Playlist> observePlaylistSelection();
        void setInputEnabled(boolean enabled);

    }

}
