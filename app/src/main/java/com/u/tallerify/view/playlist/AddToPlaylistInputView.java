package com.u.tallerify.view.playlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.u.tallerify.R;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 4/8/17.
 */
class AddToPlaylistInputView extends LinearLayout {

    @NonNull EditText editText;
    @NonNull ImageView sendView;

    @Nullable PublishSubject<String> createSubject;

    public AddToPlaylistInputView(final Context context) {
        super(context);

        inflate(context, R.layout.view_add_to_playlist_input, this);

        editText = (EditText) findViewById(R.id.view_add_to_playlist_input_edit_text);
        sendView = (ImageView) findViewById(R.id.view_add_to_playlist_input_image);

        sendView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (createSubject != null) {
                    createSubject.onNext(editText.getText().toString());
                }
            }
        });
    }

    public @NonNull Observable<String> observeCreateClicks() {
        if (createSubject == null) {
            createSubject = PublishSubject.create();
        }
        return createSubject;
    }

}
