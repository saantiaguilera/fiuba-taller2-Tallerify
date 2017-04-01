package com.u.tallerify.view.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.u.tallerify.R;
import com.u.tallerify.contract.profile.ProfileUserActivityContract;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.view.base.cards.HeaderCardView;
import com.u.tallerify.view.base.cards.HorizontalCardView;
import java.util.List;

/**
 * Created by saguilera on 3/30/17.
 */
public class ProfileUserActivityView extends LinearLayout
        implements ProfileUserActivityContract.View {

    private @NonNull HeaderCardView headerView;
    private @NonNull HorizontalCardView recyclerView;

    public ProfileUserActivityView(final Context context) {
        super(context);
    }

    public ProfileUserActivityView(final Context context, @Nullable final AttributeSet attrs) {
        super(context, attrs);
    }

    public ProfileUserActivityView(final Context context, @Nullable final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // For perfmance, since its just 2 custom views, add them programatically
        headerView = new HeaderCardView(context);
        recyclerView = new HorizontalCardView(context);

        setOrientation(VERTICAL);

        headerView.setTitle(getResources().getString(R.string.view_profile_last_activity_header_title));

        addView(headerView);
        addView(recyclerView);
    }

    @Override
    public void setActivities(@NonNull final List<GenericAdapter.ItemSupplier> cards) {
        recyclerView.setData(cards);
    }

}
