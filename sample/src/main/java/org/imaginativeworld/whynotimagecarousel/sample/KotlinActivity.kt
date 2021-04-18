package org.imaginativeworld.whynotimagecarousel.sample

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.imaginativeworld.whynotimagecarousel.dpToPx
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.listener.CarouselOnScrollListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.model.CarouselType
import org.imaginativeworld.whynotimagecarousel.sample.databinding.ActivityKotlinBinding
import org.imaginativeworld.whynotimagecarousel.sample.databinding.CustomFixedSizeItemLayoutBinding
import org.imaginativeworld.whynotimagecarousel.sample.databinding.CustomFixedSizeItemTwoLayoutBinding
import org.imaginativeworld.whynotimagecarousel.sample.databinding.CustomItemTwoLayoutBinding
import org.imaginativeworld.whynotimagecarousel.setImage
import org.imaginativeworld.whynotimagecarousel.spToPx

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

        val max = 7

        // ----------------------------------------------------------------
        // Example One
        // ----------------------------------------------------------------

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
        binding.carousel1.autoPlay = true
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

        val listOne = mutableListOf<CarouselItem>()

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

        binding.carousel1.setData(listOne)

        // ----------------------------------------------------------------
        // Example Two
        // ----------------------------------------------------------------

        // Custom view
        binding.carousel2.carouselListener = object : CarouselListener {
            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup
            ): ViewBinding? {
                return CustomItemTwoLayoutBinding.inflate(layoutInflater, parent, false)
            }

            override fun onBindViewHolder(
                binding: ViewBinding,
                imageScaleType: ImageView.ScaleType,
                item: CarouselItem,
                position: Int
            ) {
                val currentBinding = binding as CustomItemTwoLayoutBinding

                currentBinding.imageView.apply {
                    scaleType = imageScaleType

                    setImage(item, R.drawable.ic_wb_cloudy_with_padding)
                }
            }
        }
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

        binding.carousel2.setData(listTwo)

        // ----------------------------------------------------------------
        // Example Three: Example of InfiniteImageCarousel
        // ----------------------------------------------------------------

        // Custom view
        binding.carousel3.carouselListener = object : CarouselListener {
            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup
            ): ViewBinding? {
                return CustomFixedSizeItemLayoutBinding.inflate(layoutInflater, parent, false)
            }

            override fun onBindViewHolder(
                binding: ViewBinding,
                imageScaleType: ImageView.ScaleType,
                item: CarouselItem,
                position: Int
            ) {
                val currentBinding = binding as CustomFixedSizeItemLayoutBinding

                currentBinding.imageView.apply {
                    scaleType = imageScaleType

                    setImage(item, R.drawable.ic_wb_cloudy_with_padding)
                }
            }
        }

        val listThree = mutableListOf<CarouselItem>()

        listThree.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1558364015-0d5ba7a4ba86?w=1080",
                caption = "Photo by Mae Mu on Unsplash"
            )
        )
        listThree.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1568702846914-96b305d2aaeb?w=1080",
                caption = "Photo by an_vision on Unsplash"
            )
        )
        listThree.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1572635148818-ef6fd45eb394?w=1080",
                caption = "Photo by Giorgio Trovato on Unsplash"
            )
        )
        listThree.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1559181567-c3190ca9959b?w=1080",
                caption = "Photo by Mae Mu on Unsplash"
            )
        )
        listThree.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1589533610925-1cffc309ebaa?w=1080",
                caption = "Photo by Sahand Babali on Unsplash"
            )
        )
        listThree.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1547514701-42782101795e?w=1080",
                caption = "Photo by Xiaolong Wong on Unsplash"
            )
        )
        listThree.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1550258987-190a2d41a8ba?w=1080",
                caption = "Photo by Miguel Andrade on Unsplash"
            )
        )
        listThree.add(
            CarouselItem(
                imageUrl = "https://images.unsplash.com/photo-1528825871115-3581a5387919?w=1080",
                caption = "Photo by Charles Deluvio on Unsplash"
            )
        )

        binding.carousel3.setData(listThree)

        binding.carousel3.initStartPosition()

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

        // ----------------------------------------------------------------
        // Example Four
        // ----------------------------------------------------------------

        // Custom view
        binding.carousel4.carouselListener = object : CarouselListener {
            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup
            ): ViewBinding? {
                return CustomFixedSizeItemLayoutBinding.inflate(layoutInflater, parent, false)
            }

            override fun onBindViewHolder(
                binding: ViewBinding,
                imageScaleType: ImageView.ScaleType,
                item: CarouselItem,
                position: Int
            ) {
                val currentBinding = binding as CustomFixedSizeItemLayoutBinding

                currentBinding.imageView.apply {
                    scaleType = imageScaleType

                    setImage(item, R.drawable.ic_wb_cloudy_with_padding)
                }
            }
        }

        val listFour = mutableListOf<CarouselItem>()

        for (i in 1..max) {
            if (i % 2 == 0) {
                listFour.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1618207981235-22a93d2e3ef8?w=1080",
                        caption = "Smokin' Burger"
                    )
                )
            } else {
                listFour.add(
                    CarouselItem(
                        imageUrl = "https://images.unsplash.com/photo-1618346136472-090de27fe8b4?w=1080",
                        caption = "Beef Masala Curry"
                    )
                )
            }
        }

        binding.carousel4.setData(listFour)

        // Custom indicator
        binding.carousel4.setIndicator(binding.customIndicator)

        // ----------------------------------------------------------------
        // Example Five
        // ----------------------------------------------------------------

        // Custom view
        binding.carousel5.carouselListener = object : CarouselListener {
            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup
            ): ViewBinding? {
                return CustomFixedSizeItemTwoLayoutBinding.inflate(layoutInflater, parent, false)
            }

            override fun onBindViewHolder(
                binding: ViewBinding,
                imageScaleType: ImageView.ScaleType,
                item: CarouselItem,
                position: Int
            ) {
                val currentBinding = binding as CustomFixedSizeItemTwoLayoutBinding

                currentBinding.textView.text = item.caption ?: ""

                currentBinding.imageView.apply {
                    scaleType = imageScaleType

                    setImage(item, R.drawable.ic_wb_cloudy_with_padding)
                }
            }
        }

        val listFive = mutableListOf<CarouselItem>()

        for (i in 1..max) {
            if (i % 2 == 0) {
                listFive.add(
                    CarouselItem(
                        imageDrawable = R.drawable.image_1,
                        caption = "Smokin' Burger"
                    )
                )
            } else {
                listFive.add(
                    CarouselItem(
                        imageDrawable = R.drawable.image_2,
                        caption = "Beef Masala Curry"
                    )
                )
            }
        }

        binding.carousel5.setData(listFive)
    }
}
