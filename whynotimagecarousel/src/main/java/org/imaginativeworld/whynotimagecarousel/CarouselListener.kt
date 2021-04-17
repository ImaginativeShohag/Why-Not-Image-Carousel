package org.imaginativeworld.whynotimagecarousel

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewbinding.ViewBinding

interface CarouselListener {
    fun onCreateViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): ViewBinding?

    fun onBindViewHolder(
        binding: ViewBinding,
        imageScaleType: ImageView.ScaleType,
        item: CarouselItem
    )
}
