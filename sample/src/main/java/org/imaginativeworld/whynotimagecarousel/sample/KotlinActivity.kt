package org.imaginativeworld.whynotimagecarousel.sample

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.imaginativeworld.whynotimagecarousel.*
import org.imaginativeworld.whynotimagecarousel.sample.databinding.ActivityKotlinBinding

class KotlinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKotlinBinding

    private lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        binding.btnJavaExample.setOnClickListener {
            startActivity(Intent(this, JavaActivity::class.java))
        }

        // --------------------------------
        // Example One
        // --------------------------------
        binding.carousel1.showTopShadow = true
        binding.carousel1.topShadowAlpha = 0.6f // 0 to 1, 1 means 100%
        binding.carousel1.topShadowHeight = 32.dpToPx(context) // px value of dp
        binding.carousel1.showBottomShadow = true
        binding.carousel1.bottomShadowAlpha = 0.6f // 0 to 1, 1 means 100%
        binding.carousel1.bottomShadowHeight = 64.dpToPx(context) // px value of dp
        binding.carousel1.showCaption = true
        binding.carousel1.captionMargin = 0.dpToPx(context) // px value of dp
        binding.carousel1.captionTextSize = 14.spToPx(context) // px value of sp
        binding.carousel1.showIndicator = true
        binding.carousel1.indicatorMargin = 0.dpToPx(context) // px value of dp
        binding.carousel1.showNavigationButtons = true
        binding.carousel1.imageScaleType = ImageView.ScaleType.CENTER_CROP
        binding.carousel1.carouselBackground = ColorDrawable(Color.parseColor("#333333"))
        binding.carousel1.imagePlaceholder = ContextCompat.getDrawable(
            this,
            org.imaginativeworld.whynotimagecarousel.R.drawable.ic_picture
        )
        binding.carousel1.itemLayout =
            org.imaginativeworld.whynotimagecarousel.R.layout.item_carousel
        binding.carousel1.imageViewId = org.imaginativeworld.whynotimagecarousel.R.id.img
        binding.carousel1.previousButtonLayout =
            org.imaginativeworld.whynotimagecarousel.R.layout.previous_button_layout
        binding.carousel1.previousButtonId =
            org.imaginativeworld.whynotimagecarousel.R.id.btn_previous
        binding.carousel1.previousButtonMargin = 4.dpToPx(context) // px value of dp
        binding.carousel1.nextButtonLayout =
            org.imaginativeworld.whynotimagecarousel.R.layout.next_button_layout
        binding.carousel1.nextButtonId = org.imaginativeworld.whynotimagecarousel.R.id.btn_next
        binding.carousel1.nextButtonMargin = 4.dpToPx(context) // px value of dp
        binding.carousel1.carouselType = CarouselType.BLOCK
        binding.carousel1.scaleOnScroll = false
        binding.carousel1.scalingFactor = .15f // 0 to 1; 1 means 100
        binding.carousel1.autoWidthFixing = true
        binding.carousel1.autoPlay = false
        binding.carousel1.autoPlayDelay = 3000 // Milliseconds
        binding.carousel1.onScrollListener = object : CarouselOnScrollListener {
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
        binding.carousel1.carouselListener = object : CarouselListener {
            override fun onBindView(view: View, item: CarouselItem) {
                // ...
            }

            override fun onClick(position: Int, carouselItem: CarouselItem) {
                // ...
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {
                // ...
            }
        }

        val listOne = mutableListOf<CarouselItem>()

        val max = 7

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

        binding.carousel1.addData(listOne)

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

        binding.carousel2.addData(listTwo)

        // Custom click listener
        binding.carousel2.carouselListener = object : CarouselListener {
            override fun onClick(position: Int, carouselItem: CarouselItem) {

                Toast.makeText(this@KotlinActivity, "Clicked!", Toast.LENGTH_SHORT).show()
            }

            override fun onLongClick(position: Int, dataObject: CarouselItem) {

                Toast.makeText(this@KotlinActivity, "Long Clicked!", Toast.LENGTH_SHORT).show()
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

        binding.carousel3.addData(listThree)

        binding.customCaption.isSelected = true

        binding.carousel3.onScrollListener = object : CarouselOnScrollListener {

            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int,
                position: Int,
                carouselItem: CarouselItem?
            ) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    carouselItem?.apply {
                        binding.customCaption.text = caption
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                // ...
            }
        }

        // Custom navigation
        binding.btnGotoPrevious.setOnClickListener {
            binding.carousel3.previous()
        }

        binding.btnGotoNext.setOnClickListener {
            binding.carousel3.next()
        }

        // --------------------------------
        // Example Four
        // --------------------------------
        val listFour = mutableListOf<CarouselItem>()

        for (i in 1..max) {
            if (i % 2 == 0) {
                listFour.add(
                    CarouselItem(
                        imageDrawable = R.drawable.image_1
                    )
                )
            } else {
                listFour.add(
                    CarouselItem(
                        imageDrawable = R.drawable.image_2
                    )
                )
            }
        }

        binding.carousel4.addData(listFour)

        // Custom indicator
        binding.carousel4.setIndicator(binding.customIndicator)
    }
}
