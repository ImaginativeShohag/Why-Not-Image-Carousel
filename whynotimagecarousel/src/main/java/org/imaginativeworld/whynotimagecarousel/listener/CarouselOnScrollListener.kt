package org.imaginativeworld.whynotimagecarousel.listener

import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

/**
 * @see androidx.recyclerview.widget.RecyclerView.OnScrollListener
 */
interface CarouselOnScrollListener {
    /**
     * Callback method to be invoked when RecyclerView's scroll state changes.
     *
     * @param recyclerView The RecyclerView whose scroll state has changed.
     * @param newState     The updated scroll state. One of {@link #SCROLL_STATE_IDLE},
     *                     {@link #SCROLL_STATE_DRAGGING} or {@link #SCROLL_STATE_SETTLING}.
     * @param position     Current item position. Check for RecyclerView.NO_POSITION.
     * @param carouselItem Current item. It can be null.
     */
    fun onScrollStateChanged(
        recyclerView: RecyclerView,
        newState: Int,
        position: Int,
        carouselItem: CarouselItem?
    ) {
    }

    /**
     * Callback method to be invoked when the RecyclerView has been scrolled. This will be
     * called after the scroll has completed.
     * <p>
     * This callback will also be called if visible item range changes after a layout
     * calculation. In that case, dx and dy will be 0.
     *
     * @param recyclerView The RecyclerView which scrolled.
     * @param dx           The amount of horizontal scroll.
     * @param dy           The amount of vertical scroll.
     * @param position     Current item position. Check for RecyclerView.NO_POSITION.
     * @param carouselItem Current item. It can be null.
     */
    fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int,
        position: Int,
        carouselItem: CarouselItem?
    ) {
    }
}
