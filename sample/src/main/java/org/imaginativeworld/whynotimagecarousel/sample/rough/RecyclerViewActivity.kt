package org.imaginativeworld.whynotimagecarousel.sample.rough

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.sample.DataSet
import org.imaginativeworld.whynotimagecarousel.sample.databinding.ActivityRecyclerViewBinding

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerViewBinding

    private lateinit var adapter: RVItemListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RVItemListAdapter(lifecycle)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        val list = mutableListOf<RVItem>()

        val imageCarouselList = mutableListOf<CarouselItem>()
        for ((index, item) in DataSet.one.withIndex()) {
            imageCarouselList.add(
                CarouselItem(
                    imageUrl = item,
                    caption = "Image ${index + 1} of ${DataSet.one.size}"
                )
            )
        }

        list.add(RVItem(1, RVItemType.NO_CAROUSEL, emptyList()))
        list.add(RVItem(2, RVItemType.WITH_CAROUSEL, imageCarouselList))
        list.add(RVItem(3, RVItemType.NO_CAROUSEL, emptyList()))
        list.add(RVItem(4, RVItemType.WITH_CAROUSEL, imageCarouselList))
        list.add(RVItem(5, RVItemType.NO_CAROUSEL, emptyList()))
        list.add(RVItem(6, RVItemType.WITH_CAROUSEL, imageCarouselList))
        list.add(RVItem(7, RVItemType.NO_CAROUSEL, emptyList()))
        list.add(RVItem(8, RVItemType.WITH_CAROUSEL, imageCarouselList))
        list.add(RVItem(9, RVItemType.NO_CAROUSEL, emptyList()))
        list.add(RVItem(10, RVItemType.WITH_CAROUSEL, imageCarouselList))

        adapter.submitList(list)
    }
}
