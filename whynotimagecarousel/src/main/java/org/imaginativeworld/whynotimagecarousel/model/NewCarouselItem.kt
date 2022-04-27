package org.imaginativeworld.whynotimagecarousel.model

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes

class NewCarouselItem private constructor(
    internal val imageType: CarouselItemImage<*>,
    val caption: String? = null,
    val headers: Map<String, String>? = null
) {
    val image = imageType.image

    constructor(
        drawable: Drawable,
        caption: String? = null,
        headers: Map<String, String>? = null
    ) : this(
        imageType = DrawableImageType(image = drawable),
        caption = caption,
        headers = headers
    )

    constructor(
        @DrawableRes drawableRes: Int,
        caption: String? = null,
        headers: Map<String, String>? = null
    ) : this(
        imageType = DrawableResImageType(image = drawableRes),
        caption = caption,
        headers = headers
    )

    constructor(
        bitmap: Bitmap,
        caption: String? = null,
        headers: Map<String, String>? = null
    ) : this(
        imageType = BitmapImageType(image = bitmap),
        caption = caption,
        headers = headers
    )

    constructor(
        imageUrl: String,
        caption: String? = null,
        headers: Map<String, String>? = null
    ) : this(
        imageType = UrlImageType(image = imageUrl),
        caption = caption,
        headers = headers
    )
}