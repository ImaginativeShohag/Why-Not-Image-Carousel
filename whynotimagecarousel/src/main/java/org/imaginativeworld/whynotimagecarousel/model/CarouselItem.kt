package org.imaginativeworld.whynotimagecarousel.model

import androidx.annotation.DrawableRes

data class CarouselItem constructor(
    val imageUrl: String? = null,
    @DrawableRes val imageDrawable: Int? = null,
    val caption: String? = null,
    val headers: Map<String, String>?
) {
    constructor() : this(null, null, null, null)

    constructor(imageUrl: String? = null) : this(
        imageUrl,
        null,
        null,
        null
    )

    constructor(imageUrl: String? = null, headers: Map<String, String>? = null) : this(
        imageUrl,
        null,
        null,
        headers
    )

    constructor(@DrawableRes imageDrawable: Int? = null) : this(
        null,
        imageDrawable,
        null,
        null
    )

    constructor(imageUrl: String? = null, caption: String? = null) : this(
        imageUrl,
        null,
        caption,
        null
    )

    constructor(
        imageUrl: String? = null,
        caption: String? = null,
        headers: Map<String, String>? = null
    ) : this(
        imageUrl,
        null,
        caption,
        headers
    )

    constructor(@DrawableRes imageDrawable: Int? = null, caption: String? = null) : this(
        null,
        imageDrawable,
        caption,
        null
    )
}
