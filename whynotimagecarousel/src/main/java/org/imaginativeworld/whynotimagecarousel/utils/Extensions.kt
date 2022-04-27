@file:JvmName("Utils")

package org.imaginativeworld.whynotimagecarousel.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import org.imaginativeworld.whynotimagecarousel.model.*

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

fun ImageView.setImage(
    item: NewCarouselItem
) {
    val glide = Glide.with(context.applicationContext)
    when (item.imageType) {
        is DrawableImageType -> glide.load(item.image)
        is BitmapImageType -> glide.load(item.image)
        is DrawableResImageType -> glide.load(item.image)
        is UrlImageType -> glide.load(item.image)
    }
        .into(this)
}

fun ImageView.setImage(
    item: NewCarouselItem,
    @DrawableRes placeholderRes: Int
) {
    val glide = Glide.with(context.applicationContext)
    when (item.imageType) {
        is DrawableImageType -> glide.load(item.image)
        is BitmapImageType -> glide.load(item.image)
        is DrawableResImageType -> glide.load(item.image)
        is UrlImageType -> glide.load(item.image)
    }
        .placeholder(placeholderRes)
        .into(this)
}

fun ImageView.setImage(
    item: NewCarouselItem,
    placeholder: Drawable
) {
    val glide = Glide.with(context.applicationContext)
    when (item.imageType) {
        is DrawableImageType -> glide.load(item.image)
        is BitmapImageType -> glide.load(item.image)
        is DrawableResImageType -> glide.load(item.image)
        is UrlImageType -> glide.load(item.image)
    }
        .placeholder(placeholder)
        .into(this)
}
