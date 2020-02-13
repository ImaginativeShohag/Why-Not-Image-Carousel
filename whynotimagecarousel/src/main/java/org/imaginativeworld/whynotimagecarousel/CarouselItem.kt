package org.imaginativeworld.whynotimagecarousel

import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

data class CarouselItem @JvmOverloads constructor(
    @NotNull val imageUrl: String,
    @Nullable val caption: String? = null
)