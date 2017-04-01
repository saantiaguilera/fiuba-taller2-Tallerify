package com.u.tallerify.view.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.u.tallerify.R;
import com.u.tallerify.contract.profile.ProfileUserContactsContract;
import com.u.tallerify.view.abstracts.FixedListView;
import com.u.tallerify.view.base.cards.HeaderCardView;
import java.util.List;
import rx.Observable;

/**
 * Created by saguilera on 4/1/17.
 */

public class ProfileUserContactsView extends LinearLayout
        implements ProfileUserContactsContract.View {

    private @NonNull HeaderCardView headerView;
    private @NonNull FixedListView recyclerView;

    public ProfileUserContactsView(final Context context) {
        this(context, null);
    }

    public ProfileUserContactsView(final Context context, @Nullable final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProfileUserContactsView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // For perfmance, since its just 2 custom views, add them programatically
        headerView = new HeaderCardView(context);
        recyclerView = new FixedListView(context);

        setOrientation(VERTICAL);

        headerView.setTitle(getResources().getString(R.string.view_profile_last_activity_header_title));

        addView(headerView);
        addView(recyclerView);
    }

    @Override
    public void setContacts(final @NonNull List<String> names, final @NonNull List<String> urls) {
        recyclerView.setData(names, urls);
    }

    @NonNull
    @Override
    public Observable<Integer> observeContactClicks() {
        return recyclerView.observePositionClicks();
    }
}
