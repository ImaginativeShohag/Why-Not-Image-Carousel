package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarouselAdapter(
    private val context: Context,
    private val listener: OnObjectListInteractionListener<CarouselItem>? = null,
    private val imageScaleType: ImageView.ScaleType
) : RecyclerView.Adapter<CarouselAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var img: ImageView = itemView.findViewById(R.id.img)
    }

    private val dataList: MutableList<CarouselItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_carousel, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = dataList[position]

        holder.img.scaleType = imageScaleType

        Glide.with(context)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_picture)
            .into(holder.img)

        listener?.apply {

            holder.itemView.setOnClickListener {
                listener.onClick(position, item)
            }

            holder.itemView.setOnLongClickListener {
                listener.onLongClick(position, item)

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