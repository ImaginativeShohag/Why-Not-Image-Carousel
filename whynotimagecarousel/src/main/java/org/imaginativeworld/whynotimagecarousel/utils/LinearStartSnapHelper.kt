package org.imaginativeworld.whynotimagecarousel.utils

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Linear horizontal start snap helper.
 */
class LinearStartSnapHelper : LinearSnapHelper() {

    private var mHorizontalHelper: OrientationHelper? = null

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return findFirstView(layoutManager, getHorizontalHelper(layoutManager))
    }

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)
        out[0] = distanceToStart(targetView, getHorizontalHelper(layoutManager))
        return out
    }

    private fun distanceToStart(targetView: View, helper: OrientationHelper?): Int {
        val childStart = helper!!.getDecoratedStart(targetView)
        val containerStart = helper.startAfterPadding

        return childStart - containerStart
    }

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView != null && recyclerView.layoutManager != null &&
            !recyclerView.layoutManager!!.canScrollHorizontally()
        ) {
            throw Exception("This only works with linear layout manager with horizontal scroll!")
        }

        super.attachToRecyclerView(recyclerView)
    }

    private fun findFirstView(
        layoutManager: RecyclerView.LayoutManager?,
        helper: OrientationHelper?
    ): View? {
        if (layoutManager == null || helper == null) return null

        val childCount = layoutManager.childCount
        if (childCount == 0) return null

        if (layoutManager is LinearLayoutManager) {
            val firstVisibleChild = layoutManager.findFirstVisibleItemPosition()
            val lastVisibleChild = layoutManager.findLastCompletelyVisibleItemPosition()
            val isLastItem = (lastVisibleChild == layoutManager.getItemCount() - 1)

            if (firstVisibleChild == RecyclerView.NO_POSITION || isLastItem) {
                return null
            }

            val child = layoutManager.findViewByPosition(firstVisibleChild)

            return if (helper.getDecoratedEnd(child) >= helper.getDecoratedMeasurement(child) / 2 &&
                helper.getDecoratedEnd(child) > 0
            ) {
                child
            } else {
                if (lastVisibleChild == layoutManager.getItemCount() - 1) {
                    null
                } else {
                    layoutManager.findViewByPosition(firstVisibleChild + 1)
                }
            }
        }

        return super.findSnapView(layoutManager)
    }

    private fun getHorizontalHelper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return mHorizontalHelper
    }
}
