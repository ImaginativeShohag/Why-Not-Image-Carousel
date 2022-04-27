package org.imaginativeworld.whynotimagecarousel.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

sealed class CarouselItemImage<T>(val image: T)

class DrawableImageType(image: Drawable): CarouselItemImage<Drawable>(image)
class DrawableResImageType(@DrawableRes image: Int): CarouselItemImage<Int>(image)
class BitmapImageType(image: Bitmap): CarouselItemImage<Bitmap>(image)
class UrlImageType(image: String): CarouselItemImage<String>(image)