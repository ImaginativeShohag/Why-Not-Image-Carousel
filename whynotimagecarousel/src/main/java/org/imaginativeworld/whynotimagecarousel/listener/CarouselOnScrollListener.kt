package org.imaginativeworld.whynotimagecarousel.listener

import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

interface CarouselOnScrollListener {

    fun onScrollStateChanged(
        recyclerView: RecyclerView,
        newState: Int,
        position: Int,
        carouselItem: CarouselItem?
    ) {}

    fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {}
}
