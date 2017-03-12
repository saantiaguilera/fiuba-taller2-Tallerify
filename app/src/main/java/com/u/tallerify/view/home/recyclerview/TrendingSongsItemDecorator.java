package com.u.tallerify.view.home.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * This shit doesnt support reverse layout, who tf would use that?
 * Created by saguilera on 3/12/17.
 */
public class TrendingSongsItemDecorator extends RecyclerView.ItemDecoration {

    private int space;

    public TrendingSongsItemDecorator(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        final int itemPosition = parent.getChildAdapterPosition(view);
        if (itemPosition == RecyclerView.NO_POSITION) {
            return;
        }

        int quarterSpace = space / 4;
        if (itemPosition == 0) {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.bottom = space;
                outRect.top = 0;
                outRect.left = quarterSpace;
                outRect.right = quarterSpace;
            } else {
                outRect.right = space;
                outRect.left = 0;
                outRect.top = quarterSpace;
                outRect.bottom = quarterSpace;
            }
        } else if (itemPosition == state.getItemCount() - 1) {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.top = space;
                outRect.bottom = 0;
                outRect.left = quarterSpace;
                outRect.right = quarterSpace;
            } else {
                outRect.left = space;
                outRect.right = 0;
                outRect.top = quarterSpace;
                outRect.bottom = quarterSpace;
            }
        } else {
            if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
                outRect.top = space;
                outRect.bottom = space;
                outRect.left = quarterSpace;
                outRect.right = quarterSpace;
            } else {
                outRect.left = space;
                outRect.right = space;
                outRect.top = quarterSpace;
                outRect.bottom = quarterSpace;
            }
        }
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) parent.getLayoutManager()).getOrientation();
        } else {
            throw new IllegalStateException(
                "SpaceItemDecoration can only be used with a LinearLayoutManager.");
        }
    }

}