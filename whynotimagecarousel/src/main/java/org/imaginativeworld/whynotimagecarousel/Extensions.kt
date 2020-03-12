@file:JvmName("Utils")

package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper


/**
 * This method converts device specific pixels to density independent pixels.
 */
fun Int.pxToDp(context: Context): Int {
    return (this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 */
fun Int.dpToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

/**
 * This method converts sp unit to equivalent pixels, depending on device density.
 */
fun Int.spToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

/**
 * Get current snap item position of a recyclerView.
 *
 * @param layoutManager Target recyclerView
 * @return Position of the item or RecyclerView.NO_POSITION (-1)
 */
fun SnapHelper.getSnapPosition(layoutManager: RecyclerView.LayoutManager?): Int {
    if (layoutManager == null) {
        return RecyclerView.NO_POSITION
    }
    val snapView: View = this.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}