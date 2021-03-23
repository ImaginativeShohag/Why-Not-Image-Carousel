package org.imaginativeworld.whynotimagecarousel

import android.view.View

interface CarouselListener {

    fun onBindView(view: View, item: CarouselItem) {}

    fun onClick(position: Int, carouselItem: CarouselItem) {}

    fun onLongClick(position: Int, dataObject: CarouselItem) {}

}