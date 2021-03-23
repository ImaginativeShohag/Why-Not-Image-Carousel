package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.recyclerview.widget.*
import androidx.recyclerview.widget.RecyclerView.SmoothScroller
import androidx.recyclerview.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
import kotlin.math.abs

class LinearStartSnapHelper : LinearSnapHelper() {

    private var context: Context? = null
    private val MAX_SCROLL_ON_FLING_DURATION_MS = 1000
    private val MILLISECONDS_PER_INCH = 100f
    private var helper: OrientationHelper? = null
    private var scroller: Scroller? = null
    private var maxScrollDistance = 0

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        return findFirstView(layoutManager, helper(layoutManager))
    }

    override fun createScroller(layoutManager: RecyclerView.LayoutManager): SmoothScroller? {
        if (layoutManager is ScrollVectorProvider) {
            return super.createScroller(layoutManager)
        }

        if (context == null) {
            return null
        }

        val context = context
        return object : LinearSmoothScroller(context) {
            override fun onTargetFound(
                targetView: View,
                state: RecyclerView.State,
                action: Action
            ) {
                val snapDistance = calculateDistanceToFinalSnap(layoutManager, targetView)
                val dx = snapDistance[0]
                val dy = snapDistance[1]
//                val dt = calculateTimeForDeceleration(abs(dx))
//                val time = Math.max(1, Math.min(MAX_SCROLL_ON_FLING_DURATION_MS, dt))
//                action.update(dx, dy, time, mDecelerateInterpolator)

                // following code copied from snaphelper.java, but not works -_-

                val time = calculateTimeForDeceleration(abs(dx))
                if (time > 0) {
                    action.update(dx, dy, time, mDecelerateInterpolator)
                }
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
            }
        }
    }

    override fun calculateDistanceToFinalSnap(
        layoutManager: RecyclerView.LayoutManager,
        targetView: View
    ): IntArray {
        val out = IntArray(2)
        out[0] = distanceStart(targetView, helper(layoutManager))
        return out
    }

    private fun distanceStart(targetView: View, helper: OrientationHelper?): Int {
        val childStart = helper!!.getDecoratedStart(targetView)
        val containerStart = helper.startAfterPadding

        return childStart - containerStart
    }

    override fun calculateScrollDistance(velocityX: Int, velocityY: Int): IntArray? {
        val out = IntArray(2)
        if (helper == null) {
            return out
        }
        val helper = helper
        if (maxScrollDistance == 0) {
            maxScrollDistance = (helper!!.endAfterPadding - helper.startAfterPadding) / 2
        }
        scroller!!.fling(0, 0, velocityX, velocityY, -maxScrollDistance, maxScrollDistance, 0, 0)
        out[0] = scroller!!.finalX
        out[1] = scroller!!.finalY
        return out
    }

    @Throws(IllegalStateException::class)
    override fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (recyclerView != null) {
            context = recyclerView.context
            scroller = Scroller(context, DecelerateInterpolator())
        } else {
            scroller = null
            context = null
        }
        super.attachToRecyclerView(recyclerView)
    }

    private fun findFirstView(
        layoutManager: RecyclerView.LayoutManager?,
        helper: OrientationHelper?
    ): View? {
        if (layoutManager == null) return null

        val childCount = layoutManager.childCount
        if (childCount == 0) return null

        // ----

        if (layoutManager is LinearLayoutManager) {
            if (layoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                return layoutManager.getChildAt(0)
            } else if (layoutManager.findLastCompletelyVisibleItemPosition()
                == childCount - 1
            ) {
                return layoutManager.getChildAt(childCount - 1)
            }
        }

        // ----

        var absClosest = Int.MAX_VALUE
        var closestView: View? = null
        val start = helper!!.startAfterPadding

        for (i in 0 until childCount) {
            val child = layoutManager.getChildAt(i)
            val childStart = helper.getDecoratedStart(child)
            val absDistanceToStart = Math.abs(childStart - start)
            if (absDistanceToStart < absClosest) {
                absClosest = absDistanceToStart
                closestView = child
            }
        }
        return closestView
    }

    private fun helper(layoutManager: RecyclerView.LayoutManager): OrientationHelper? {
        if (helper == null) {
            helper = OrientationHelper.createHorizontalHelper(layoutManager)
        }
        return helper
    }
}
