package com.u.tallerify.view.playlist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            getResources().getDimensionPixelSize(R.dimen.view_playlist_add_item_height)));

        inflate(context, R.layout.view_add_to_playlist_input, this);

        editText = (EditText) findViewById(R.id.view_add_to_playlist_input_edit_text);
        sendView = (ImageView) findViewById(R.id.view_add_to_playlist_input_image);

        sendView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                send();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND ||
                        event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    send();
                    return true;
                }
                return false;
            }

        });
    }

    void send() {
        if (createSubject != null && !editText.getText().toString().isEmpty()) {
            createSubject.onNext(editText.getText().toString());
        }
    }

    public void setEditable(boolean editable) {
        editText.setInputType(editable ?
            InputType.TYPE_CLASS_TEXT :
            InputType.TYPE_NULL);
    }

    public @NonNull Observable<String> observeCreateClicks() {
        if (createSubject == null) {
            createSubject = PublishSubject.create();
        }
        return createSubject;
    }

}
