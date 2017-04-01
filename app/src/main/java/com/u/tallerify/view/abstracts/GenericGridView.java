package com.u.tallerify.view.abstracts;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import com.u.tallerify.R;
import com.u.tallerify.contract.abstracts.GenericGridContract;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.view.abstracts.decorators.BaseHomeItemDecorator;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
public class GenericGridView extends RecyclerView implements GenericGridContract.View {

    @NonNull GenericAdapter adapter;

    public GenericGridView(final Context context) {
        this(context, null);
    }

    public GenericGridView(final Context context, final AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GenericGridView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.colorAccent, null));

        addItemDecoration(new BaseHomeItemDecorator(getResources().getDimensionPixelSize(R.dimen.home_item_paddings)));

        adapter = new GenericAdapter();

        GridLayoutManager manager = new GridLayoutManager(context, GenericAdapter.MAX_SPAN_SIZE);
        manager.setInitialPrefetchItemCount(4);
        manager.setSpanSizeLookup(adapter.getSpanSizeLookup());
        setLayoutManager(manager);

        setAdapter(adapter);
    }

    @Override
    public void setData(final @NonNull List<GenericAdapter.ItemSupplier> cards) {
        adapter.data(cards);
    }

}
