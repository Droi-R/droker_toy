package com.bvc.ordering.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


//rv_lesson_detail_sns.addItemDecoration(new CustomRecyclerDecoration_Ve(30));
public class CustomRecyclerDecoration_Ho extends RecyclerView.ItemDecoration {
    private final int divWidth;

    public CustomRecyclerDecoration_Ho(int divWidth) {
        this.divWidth = divWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.right = divWidth;
        outRect.left = divWidth;
    }
}
