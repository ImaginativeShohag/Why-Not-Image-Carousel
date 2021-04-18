package org.imaginativeworld.whynotimagecarousel.listener

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewbinding.ViewBinding
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

interface CarouselListener {
    fun onCreateViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): ViewBinding?

    fun onBindViewHolder(
        binding: ViewBinding,
        imageScaleType: ImageView.ScaleType,
        item: CarouselItem,
        position: Int
    )
}
