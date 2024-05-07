/**
 * Copyright © 2021 Md. Mahmudul Hasan Shohag. All rights reserved.
 */

package org.imaginativeworld.whynotimagecarousel.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselGravity
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.sample.databinding.ActivityTestBinding
import org.imaginativeworld.whynotimagecarousel.sample.databinding.ItemCustomFixedSizeLayout3Binding
import org.imaginativeworld.whynotimagecarousel.utils.setImage

class TestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --------------------------------

        val listOne = mutableListOf<CarouselItem>()

        for ((index, item) in DataSet.one.withIndex()) {
            listOne.add(
                CarouselItem(
                    imageUrl = item,
                    caption = "Image ${index + 1} of ${DataSet.one.size}",
                ),
            )
        }

        // --------------------------------

        val listTwo = mutableListOf<CarouselItem>()

        for ((index, item) in DataSet.one.withIndex()) {
            listTwo.add(
                CarouselItem(
                    imageUrl = item,
                    caption = "Image ${index + 1} of ${DataSet.one.size}",
                ),
            )
        }

        // --------------------------------

        binding.carousel3.carouselListener =
            object : CarouselListener {
                override fun onCreateViewHolder(
                    layoutInflater: LayoutInflater,
                    parent: ViewGroup,
                ): ViewBinding {
                    return ItemCustomFixedSizeLayout3Binding.inflate(
                        layoutInflater,
                        parent,
                        false,
                    )
                }

                override fun onBindViewHolder(
                    binding: ViewBinding,
                    item: CarouselItem,
                    position: Int,
                ) {
                    val currentBinding = binding as ItemCustomFixedSizeLayout3Binding

                    currentBinding.imageView.apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP

                        setImage(item, R.drawable.ic_wb_cloudy_with_padding)
                    }
                }
            }

        val listThree = mutableListOf<CarouselItem>()

        for ((index, item) in DataSet.one.withIndex()) {
            listThree.add(
                CarouselItem(
                    imageUrl = item,
                    caption = "Image ${index + 1} of ${DataSet.one.size}",
                ),
            )
        }

        // --------------------------------

        binding.carousel4.carouselListener =
            object : CarouselListener {
                override fun onCreateViewHolder(
                    layoutInflater: LayoutInflater,
                    parent: ViewGroup,
                ): ViewBinding {
                    return ItemCustomFixedSizeLayout3Binding.inflate(
                        layoutInflater,
                        parent,
                        false,
                    )
                }

                override fun onBindViewHolder(
                    binding: ViewBinding,
                    item: CarouselItem,
                    position: Int,
                ) {
                    val currentBinding = binding as ItemCustomFixedSizeLayout3Binding

                    currentBinding.imageView.apply {
                        scaleType = ImageView.ScaleType.CENTER_CROP

                        setImage(item, R.drawable.ic_wb_cloudy_with_padding)
                    }
                }
            }

        val listFour = mutableListOf<CarouselItem>()

        for ((index, item) in DataSet.one.withIndex()) {
            listFour.add(
                CarouselItem(
                    imageUrl = item,
                    caption = "Image ${index + 1} of ${DataSet.one.size}",
                ),
            )
        }

        // --------------------------------

        binding.btnPreviousAll.setOnClickListener {
            binding.carousel1.currentPosition--
            binding.carousel2.currentPosition--
            binding.carousel3.currentPosition--
            binding.carousel4.currentPosition--
        }

        binding.btnNextAll.setOnClickListener {
            binding.carousel1.currentPosition++
            binding.carousel2.currentPosition++
            binding.carousel3.currentPosition++
            binding.carousel4.currentPosition++
        }

        // --------------------------------

        binding.btnSingleAppend.setOnClickListener {
            val item =
                CarouselItem(
                    imageUrl = DataSet.three[1].first,
                    caption = DataSet.three[1].second,
                )

            binding.carousel1.addData(item)
            binding.carousel2.addData(item)
            binding.carousel3.addData(item)
            binding.carousel4.addData(item)
        }

        binding.btnMultiAppend.setOnClickListener {
            binding.carousel1.addData(listOne.take(3))
            binding.carousel2.addData(listTwo.take(3))
            binding.carousel3.addData(listThree.take(3))
            binding.carousel4.addData(listFour.take(3))
        }

        // --------------------------------

        binding.btnLoadData.setOnClickListener {
            binding.carousel1.setData(listOne)
            binding.carousel2.setData(listTwo)
            binding.carousel3.setData(listThree)
            binding.carousel4.setData(listFour)
        }

        binding.btnClearData.setOnClickListener {
            binding.carousel1.setData(emptyList())
            binding.carousel2.setData(emptyList())
            binding.carousel3.setData(emptyList())
            binding.carousel4.setData(emptyList())
        }

        // --------------------------------

        binding.btn1.setOnClickListener {
            binding.carousel1.currentPosition = 0
            binding.carousel2.currentPosition = 0
            binding.carousel3.currentPosition = 0
            binding.carousel4.currentPosition = 0
        }

        binding.btn2.setOnClickListener {
            binding.carousel1.currentPosition = 1
            binding.carousel2.currentPosition = 1
            binding.carousel3.currentPosition = 1
            binding.carousel4.currentPosition = 1
        }

        binding.btn3.setOnClickListener {
            binding.carousel1.currentPosition = 2
            binding.carousel2.currentPosition = 2
            binding.carousel3.currentPosition = 2
            binding.carousel4.currentPosition = 2
        }

        binding.btn4.setOnClickListener {
            binding.carousel1.currentPosition = 3
            binding.carousel2.currentPosition = 3
            binding.carousel3.currentPosition = 3
            binding.carousel4.currentPosition = 3
        }

        binding.btn5.setOnClickListener {
            binding.carousel1.currentPosition = 4
            binding.carousel2.currentPosition = 4
            binding.carousel3.currentPosition = 4
            binding.carousel4.currentPosition = 4
        }

        binding.btn6.setOnClickListener {
            binding.carousel1.currentPosition = 5
            binding.carousel2.currentPosition = 5
            binding.carousel3.currentPosition = 5
            binding.carousel4.currentPosition = 5
        }

        binding.btn7.setOnClickListener {
            binding.carousel1.currentPosition = 6
            binding.carousel2.currentPosition = 6
            binding.carousel3.currentPosition = 6
            binding.carousel4.currentPosition = 6
        }

        // --------------------------------

        binding.btnChangeToFinite.setOnClickListener {
            binding.carousel1.infiniteCarousel = false
            binding.carousel2.infiniteCarousel = false
            binding.carousel3.infiniteCarousel = false
            binding.carousel4.infiniteCarousel = false
        }

        binding.btnChangeToInfinite.setOnClickListener {
            binding.carousel1.infiniteCarousel = true
            binding.carousel2.infiniteCarousel = true
            binding.carousel3.infiniteCarousel = true
            binding.carousel4.infiniteCarousel = true
        }

        // --------------------------------

        binding.btnScaleOnScroll.setOnClickListener {
            binding.carousel1.scaleOnScroll = true
            binding.carousel2.scaleOnScroll = true
            binding.carousel3.scaleOnScroll = true
            binding.carousel4.scaleOnScroll = true
        }

        binding.btnNotScaleOnScroll.setOnClickListener {
            binding.carousel1.scaleOnScroll = false
            binding.carousel2.scaleOnScroll = false
            binding.carousel3.scaleOnScroll = false
            binding.carousel4.scaleOnScroll = false
        }

        // --------------------------------

        binding.btnGravityStart.setOnClickListener {
            binding.carousel1.carouselGravity = CarouselGravity.START
            binding.carousel2.carouselGravity = CarouselGravity.START
            binding.carousel3.carouselGravity = CarouselGravity.START
            binding.carousel4.carouselGravity = CarouselGravity.START
        }

        binding.btnGravityCenter.setOnClickListener {
            binding.carousel1.carouselGravity = CarouselGravity.CENTER
            binding.carousel2.carouselGravity = CarouselGravity.CENTER
            binding.carousel3.carouselGravity = CarouselGravity.CENTER
            binding.carousel4.carouselGravity = CarouselGravity.CENTER
        }
    }
}
