package org.imaginativeworld.whynotimagecarousel.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.sample.databinding.ActivityTestBinding

class TestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --------------------------------

        binding.btnPreviousAll.setOnClickListener {
            binding.carousel1.currentPosition--
            binding.carousel2.currentPosition--
        }

        binding.btnNextAll.setOnClickListener {
            binding.carousel1.currentPosition++
            binding.carousel2.currentPosition++
        }

        // --------------------------------

        binding.btn1.setOnClickListener {
            binding.carousel1.currentPosition = 0
            binding.carousel2.currentPosition = 0
        }

        binding.btn2.setOnClickListener {
            binding.carousel1.currentPosition = 1
            binding.carousel2.currentPosition = 1
        }

        binding.btn3.setOnClickListener {
            binding.carousel1.currentPosition = 2
            binding.carousel2.currentPosition = 2
        }

        binding.btn4.setOnClickListener {
            binding.carousel1.currentPosition = 3
            binding.carousel2.currentPosition = 3
        }

        binding.btn5.setOnClickListener {
            binding.carousel1.currentPosition = 4
            binding.carousel2.currentPosition = 4
        }

        binding.btn6.setOnClickListener {
            binding.carousel1.currentPosition = 5
            binding.carousel2.currentPosition = 5
        }

        binding.btn7.setOnClickListener {
            binding.carousel1.currentPosition = 6
            binding.carousel2.currentPosition = 6
        }

        // --------------------------------

        val listOne = mutableListOf<CarouselItem>()

        for ((index, item) in DataSet.one.withIndex()) {
            listOne.add(
                CarouselItem(
                    imageUrl = item,
                    caption = "Image ${index + 1} of ${DataSet.one.size}",
                )
            )
        }

        binding.carousel1.setData(listOne)

        // --------------------------------

        val listTwo = mutableListOf<CarouselItem>()

        for ((index, item) in DataSet.one.withIndex()) {
            listTwo.add(
                CarouselItem(
                    imageUrl = item,
                    caption = "Image ${index + 1} of ${DataSet.one.size}",
                )
            )
        }

        binding.carousel2.setData(listOne)
    }
}
