package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarouselAdapter(
    private val context: Context,
    @LayoutRes private val itemLayout: Int,
    @IdRes private val imageViewId: Int,
    var listener: OnItemClickListener? = null,
    private val imageScaleType: ImageView.ScaleType,
    private val imagePlaceholder: Drawable?
) : RecyclerView.Adapter<CarouselAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View, imageViewId: Int) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(imageViewId)
    }

    private val dataList: MutableList<CarouselItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)

        return MyViewHolder(view, imageViewId)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]

        holder.img.scaleType = imageScaleType

        Glide.with(context)
            .load(item.imageUrl)
            .placeholder(imagePlaceholder)
            .into(holder.img)

        listener?.apply {

            holder.itemView.setOnClickListener {
                this.onClick(position, item)
            }

            holder.itemView.setOnLongClickListener {
                this.onLongClick(position, item)

                true
            }

        }
    }

    public fun getItem(position: Int): CarouselItem? {
        return if (position < dataList.size) {
            dataList[position]
        } else {
            null
        }
    }

    public fun addAll(dataList: List<CarouselItem>) {
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }

    public fun add(item: CarouselItem) {
        this.dataList.add(item)
        notifyItemInserted(dataList.size - 1)
    }

}