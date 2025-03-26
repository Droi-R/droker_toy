package com.bvc.ordering.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


//rv_lesson_detail_sns.addItemDecoration(new CustomRecyclerDecoration_Ve(30));
public class CustomRecyclerDecoration_Ve extends RecyclerView.ItemDecoration {
    private final int divHeight;

    public CustomRecyclerDecoration_Ve(int divHeight) {
        this.divHeight = divHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = divHeight;
        outRect.bottom = divHeight;
    }
}
