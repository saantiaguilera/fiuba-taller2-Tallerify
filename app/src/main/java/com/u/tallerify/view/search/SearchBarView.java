package com.u.tallerify.view.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.u.tallerify.R;
import com.u.tallerify.contract.search.SearchBarContract;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Android search view is a masterpiece of garbage.
 *
 * Created by saguilera on 3/24/17.
 */
public class SearchBarView extends LinearLayout
        implements SearchBarContract.View {

    @NonNull EditText editTextView;
    @NonNull ImageView clearView;

    @Nullable PublishSubject<String> subject;

    public SearchBarView(final Context context) {
        super(context);

        inflate(context, R.layout.view_search, this);

        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT));

        editTextView = (EditText) findViewById(R.id.view_search_edit_text);
        clearView = (ImageView) findViewById(R.id.view_search_clear);

        editTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {}

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                if (s.toString().isEmpty()) {
                    clearView.setVisibility(View.GONE);
                } else {
                    if (clearView.getVisibility() != View.VISIBLE) {
                        clearView.setVisibility(View.VISIBLE);
                    }

                    if (subject != null) {
                        subject.onNext(s.toString());
                    }
                }
            }

            @Override
            public void afterTextChanged(final Editable s) {}
        });

        clearView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                editTextView.setText("");
            }
        });
    }

    @NonNull
    @Override
    public Observable<String> observeInputs() {
        if (subject == null) {
            subject = PublishSubject.create();
        }

        return subject;
    }

}
