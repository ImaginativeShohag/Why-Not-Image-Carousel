package org.imaginativeworld.whynotimagecarousel.listener

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

interface CarouselListener {
    /**
     * Inflate custom view here using view binding.
     * It's mapped with RecyclerView.Adapter#onCreateViewHolder.
     *
     * @param layoutInflater for inflating layout.
     * @param parent the parent of the generated hierarchy.
     * @return ViewBinding of the custom view.
     */
    fun onCreateViewHolder(layoutInflater: LayoutInflater, parent: ViewGroup): ViewBinding? = null

    /**
     * Bind view with data.
     * It's mapped with RecyclerView.Adapter#onBindViewHolder.
     *
     * @param binding The ViewBinding returned in onCreateViewHolder.
     * @param item current CarouselItem item for bind.
     * @param position current data position.
     */
    fun onBindViewHolder(binding: ViewBinding, item: CarouselItem, position: Int) {}

    /**
     * When an item view is clicked it will be invoked.
     *
     * Note: It will not work for the custom layout.
     *
     * @param position Data item position.
     * @param carouselItem Data item of the clicked view.
     */
    fun onClick(position: Int, carouselItem: CarouselItem) {}

    /**
     * When an item view is long clicked it will be invoked.
     *
     * Note: It will not work for the custom layout.
     *
     * @param position Data item position.
     * @param carouselItem Data item of the long clicked view.
     */
    fun onLongClick(position: Int, carouselItem: CarouselItem) {}
}
