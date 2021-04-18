package org.imaginativeworld.whynotimagecarousel.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CarouselItemDecoration(
    private val width: Int,
    private val spacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect.right = if (width > 0) spacing / 2 else spacing
        outRect.left = if (width > 0) spacing / 2 else 0

        // First Item
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.left =
                if (width > 0) parent.measuredWidth / 2 - width / 2 else 0
        }

        // Last Item
        if (state.itemCount - 1 == parent.getChildLayoutPosition(view)) {
            outRect.right =
                if (width > 0) parent.measuredWidth / 2 - width / 2 else 0
        }
    }
}
