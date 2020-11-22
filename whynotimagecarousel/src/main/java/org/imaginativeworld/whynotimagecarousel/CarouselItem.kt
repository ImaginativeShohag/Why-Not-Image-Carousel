package org.imaginativeworld.whynotimagecarousel

import androidx.annotation.DrawableRes

data class CarouselItem constructor(
    val imageUrl: String? = null,
    @DrawableRes val imageDrawable: Int? = null,
    val caption: String? = null
) {
    constructor(imageUrl: String? = null) : this(
        imageUrl,
        null,
        null
    )
    constructor(@DrawableRes imageDrawable: Int? = null) : this(
        null,
        imageDrawable,
        null
    )
    constructor(imageUrl: String? = null, caption: String? = null) : this(
        imageUrl,
        null,
        caption
    )
    constructor(@DrawableRes imageDrawable: Int? = null, caption: String? = null) : this(
        null,
        imageDrawable,
        caption
    )
}