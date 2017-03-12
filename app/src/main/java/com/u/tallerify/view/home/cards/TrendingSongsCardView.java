package com.u.tallerify.view.home.cards;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.u.tallerify.R;
import com.u.tallerify.contract.home.cards.TrendingSongsCardContract;
import com.u.tallerify.list.adapter.GenericAdapter;
import com.u.tallerify.view.home.recyclerview.TrendingSongsItemDecorator;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */

public class TrendingSongsCardView extends RecyclerView
        implements TrendingSongsCardContract.View {

    private GenericAdapter adapter;

    public TrendingSongsCardView(final Context context) {
        this(context, null);
    }

    public TrendingSongsCardView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TrendingSongsCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT));

        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));

        addItemDecoration(new TrendingSongsItemDecorator(getResources().getDimensionPixelSize(R.dimen.home_item_paddings)));

        setLayoutManager(new LinearLayoutManager(context, HORIZONTAL, false));
        setAdapter(adapter = new GenericAdapter());
    }

    @Override
    public void setData(@NonNull final List<GenericAdapter.ItemSupplier> cards) {
        adapter.data(cards);
    }

}
