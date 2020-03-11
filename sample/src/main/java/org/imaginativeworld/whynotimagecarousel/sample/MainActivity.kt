package org.imaginativeworld.whynotimagecarousel.sample

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.imaginativeworld.whynotimagecarousel.*

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        context = this

        btn_java_example.setOnClickListener {

            startActivity(Intent(this, JavaActivity::class.java))

        }

        // --------------------------------
        // Example One
        // --------------------------------
        carousel1.showTopShadow = true
        carousel1.topShadowAlpha = 0.6f // 0 to 1, 1 means 100%
        carousel1.showBottomShadow = true
        carousel1.bottomShadowAlpha = 0.6f // 0 to 1, 1 means 100%
        carousel1.showCaption = true
        carousel1.showIndicator = true
        carousel1.showNavigationButtons = true
        carousel1.imageScaleType = ImageView.ScaleType.CENTER_CROP
        carousel1.carouselBackground = ColorDrawable(Color.parseColor("#333333"))
        carousel1.imagePlaceholder = ContextCompat.getDrawable(
            this,
            org.imaginativeworld.whynotimagecarousel.R.drawable.ic_picture
        )
        carousel1.itemLayout = org.imaginativeworld.whynotimagecarousel.R.layout.item_carousel
        carousel1.imageViewId = org.imaginativeworld.whynotimagecarousel.R.id.img
        carousel1.previousButtonLayout =
            org.imaginativeworld.whynotimagecarousel.R.layout.previous_button_layout
        carousel1.previousButtonId = org.imaginativeworld.whynotimagecarousel.R.id.btn_previous
        carousel1.previousButtonMargin = 4.toPx(context) // dp value
        carousel1.nextButtonLayout =
            org.imaginativeworld.whynotimagecarousel.R.layout.next_button_layout
        carousel1.nextButtonId = org.imaginativeworld.whynotimagecarousel.R.id.btn_next
        carousel1.nextButtonMargin = 4.toPx(context) // dp value
        carousel1.carouselType = CarouselType.BLOCK
        carousel1.scaleOnScroll = false
        carousel1.scalingFactor = .15f // 0 to 1; 1 means 100
        carousel1.autoWidthFixing = true
        carousel1.autoPlay = false
        carousel1.autoPlayDelay = 3000 // Milliseconds
        carousel1.onScrollListener = object : CarouselOnScrollListener {
            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int,
                position: Int,
                carouselItem: CarouselItem?
            ) {
                // ...
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // ...
            }
        }
        carousel1.onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {
                // ...
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                // ...
            }

        }

        val listOne = mutableListOf<CarouselItem>()

        val max = 10

        for (i in 1..max) {
            if (i % 2 == 0) {
                listOne.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1581357825340-32259110788a?w=1080",
                        caption = "Image $i of $max"
                    )
                )
            } else {
                listOne.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1581441117193-63e8f6547081?w=1080",
                        caption = "Image $i of $max"
                    )
                )
            }
        }

        carousel1.addData(listOne)


        // --------------------------------
        // Example Two
        // --------------------------------
        val listTwo = mutableListOf<CarouselItem>()

        for (i in 1..max) {
            if (i % 2 == 0) {
                listTwo.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1569398034126-476b0d96e2d1?w=1080"
                    )
                )
            } else {
                listTwo.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1507667522877-ad03f0c7b0e0?w=1080"
                    )
                )
            }
        }

        carousel2.addData(listTwo)

        // Custom click listener
        carousel2.onItemClickListener = object : OnItemClickListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {

                Toast.makeText(this@MainActivity, "Clicked!", Toast.LENGTH_SHORT).show()

            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {

                Toast.makeText(this@MainActivity, "Long Clicked!", Toast.LENGTH_SHORT).show()

            }

        }


        // --------------------------------
        // Example Three
        // --------------------------------
        val listThree = mutableListOf<CarouselItem>()

        for (i in 1..max) {
            if (i % 2 == 0) {
                listThree.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1549577434-d7615fd4ceac?w=1080",
                        caption = "Photo by Jeremy Bishop on Unsplash"
                    )
                )
            } else {
                listThree.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1485038101637-2d4833df1b35?w=1080",
                        caption = "Photo by Karsten Würth on Unsplash"
                    )
                )
            }
        }

        carousel3.addData(listThree)

        custom_caption.isSelected = true

        carousel3.onScrollListener = object : CarouselOnScrollListener {

            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int,
                position: Int,
                carouselItem: CarouselItem?
            ) {

                Log.e("aaa", "newState: $newState")
                Log.e("aaa", "position: $position")
                Log.e("aaa", "carouselItem: $carouselItem")

                carouselItem?.apply {
                    custom_caption.text = caption
                }

            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // ...
            }

        }

        // Custom navigation
        btn_goto_previous.setOnClickListener {
            carousel3.previous()
        }

        btn_goto_next.setOnClickListener {
            carousel3.next()
        }


        // --------------------------------
        // Example Four
        // --------------------------------
        val listFour = mutableListOf<CarouselItem>()

        for (i in 1..max) {
            if (i % 2 == 0) {
                listFour.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1560082020-029ec0709721?w=1080"
                    )
                )
            } else {
                listFour.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1542144611-13e92d1e1d01?w=1080"
                    )
                )
            }
        }

        carousel4.addData(listFour)

        // Custom indicator
        carousel4.setIndicator(custom_indicator)

    }
}
