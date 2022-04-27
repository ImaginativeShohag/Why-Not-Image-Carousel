package org.imaginativeworld.whynotimagecarousel.adapter

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselGravity
import org.imaginativeworld.whynotimagecarousel.model.CarouselType
import org.imaginativeworld.whynotimagecarousel.model.NewCarouselItem

class InfiniteCarouselAdapter(
    recyclerView: RecyclerView,
    carouselType: CarouselType,
    carouselGravity: CarouselGravity,
    autoWidthFixing: Boolean,
    imageScaleType: ImageView.ScaleType,
    imagePlaceholder: Drawable?
) : FiniteCarouselAdapter(
    recyclerView,
    carouselType,
    carouselGravity,
    autoWidthFixing,
    imageScaleType,
    imagePlaceholder
) {
    override fun getItemCount(): Int {
        return if (dataList.isEmpty()) 0 else Integer.MAX_VALUE
    }

    override fun getItem(position: Int): NewCarouselItem? {
        return if (position < itemCount) {
            dataList[position % dataList.size]
        } else {
            null
        }
    }

    override fun getRealDataPosition(position: Int): Int {
        if (dataList.size == 0) return ImageCarousel.NO_POSITION
        return position % dataList.size
    }
}
