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

        // ----------------------------------------------------------------
        // Example One: All properties & attributes
        // ----------------------------------------------------------------

        binding.carousel1.apply {
            showTopShadow = true
            topShadowAlpha = 0.6f // 0 to 1, 1 means 100%
            topShadowHeight = 32.dpToPx(context) // px value of dp
            showBottomShadow = true
            bottomShadowAlpha = 0.6f // 0 to 1, 1 means 100%
            bottomShadowHeight = 64.dpToPx(context) // px value of dp
            showCaption = true
            captionMargin = 0.dpToPx(context) // px value of dp
            captionTextSize = 14.spToPx(context) // px value of sp
            showIndicator = true
            indicatorMargin = 0.dpToPx(context) // px value of dp
            showNavigationButtons = true
            imageScaleType = ImageView.ScaleType.CENTER_CROP
            carouselBackground = ColorDrawable(Color.parseColor("#333333"))
            imagePlaceholder = ContextCompat.getDrawable(
                this@KotlinActivity,
                org.imaginativeworld.whynotimagecarousel.R.drawable.ic_picture
            )
            previousButtonLayout =
                org.imaginativeworld.whynotimagecarousel.R.layout.previous_button_layout
            previousButtonId =
                org.imaginativeworld.whynotimagecarousel.R.id.btn_previous
            previousButtonMargin = 4.dpToPx(context) // px value of dp
            nextButtonLayout =
                org.imaginativeworld.whynotimagecarousel.R.layout.next_button_layout
            nextButtonId = org.imaginativeworld.whynotimagecarousel.R.id.btn_next
            nextButtonMargin = 4.dpToPx(context) // px value of dp
            carouselType = CarouselType.BLOCK
            scaleOnScroll = false
            scalingFactor = .15f // 0 to 1; 1 means 100
            autoWidthFixing = true
            autoPlay = true
            autoPlayDelay = 3000 // Milliseconds
            infiniteCarousel = true
            touchToPause = true
            onScrollListener = object : CarouselOnScrollListener {
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

            for ((index, item) in DataSet.one.withIndex()) {
                listOne.add(
                    CarouselItem(
                        imageUrl = item,
                        caption = "Image $index of ${DataSet.one.size}"
                    )
                )
            }

            binding.carousel1.setData(listOne)

            // ----------------------------------------------------------------
            // Example Two: Custom view
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

            for (item in DataSet.two) {
                listTwo.add(
                    CarouselItem(
                        imageUrl = item
                    )
                )
            }

            binding.carousel2.setData(listTwo)

            // ----------------------------------------------------------------
            // Example Three: Custome navigation
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

            for (item in DataSet.three) {
                listThree.add(
                    CarouselItem(
                        imageUrl = item.first,
                        caption = item.second
                    )
                )
            }

            binding.carousel3.setData(listThree)

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
            // Example Four: Custom indicator
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

            for (item in DataSet.four) {
                listFour.add(
                    CarouselItem(
                        imageUrl = item
                    )
                )
            }

            binding.carousel4.setData(listFour)

            // Custom indicator
            binding.carousel4.setIndicator(binding.customIndicator)

            // ----------------------------------------------------------------
            // Example Five: Usage of Drawable
            // ----------------------------------------------------------------

            // Custom view
            binding.carousel5.carouselListener = object : CarouselListener {
                override fun onCreateViewHolder(
                    layoutInflater: LayoutInflater,
                    parent: ViewGroup
                ): ViewBinding? {
                    return CustomFixedSizeItemTwoLayoutBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
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

            for (item in DataSet.five) {
                listFive.add(
                    CarouselItem(
                        imageDrawable = item.first,
                        caption = item.second
                    )
                )
            }

            binding.carousel5.setData(listFive)
        }
    }
}
