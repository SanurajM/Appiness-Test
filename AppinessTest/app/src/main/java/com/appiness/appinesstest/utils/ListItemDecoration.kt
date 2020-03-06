package com.appiness.appinesstest.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ListItemDecoration(private var horizontalSpace: Int = 0, private var verticalSpace: Int = 0) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.left = horizontalSpace
        outRect.right = horizontalSpace
        outRect.top=verticalSpace
        if (parent.getChildAdapterPosition(view) == (parent.adapter?.itemCount ?: 1) - 1) {
            outRect.bottom = verticalSpace;
        }
    }
}