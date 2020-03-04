package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.Dimension
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import com.google.android.material.button.MaterialButton
import me.relex.circleindicator.CircleIndicator2
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

/**
 * TODO 1# currentItem get set
 * TODO 2# auto play
 * TODO 3# on scroll listener
 */

class ImageCarousel(
    @NotNull context: Context,
    @Nullable private var attributeSet: AttributeSet?
) : ConstraintLayout(context, attributeSet) {

    private val TAG = "ImageCarousel"

    private lateinit var adapter: CarouselAdapter

    private val scaleTypeArray = arrayOf(
        ImageView.ScaleType.MATRIX,
        ImageView.ScaleType.FIT_XY,
        ImageView.ScaleType.FIT_START,
        ImageView.ScaleType.FIT_CENTER,
        ImageView.ScaleType.FIT_END,
        ImageView.ScaleType.CENTER,
        ImageView.ScaleType.CENTER_CROP,
        ImageView.ScaleType.CENTER_INSIDE
    )

    private val carouselTypeArray = arrayOf(
        CarouselType.BLOCK,
        CarouselType.SHOWCASE
    )

    var onItemClickListener: OnItemClickListener? = null
        set(value) {
            field = value
            adapter.listener = value
        }

    private lateinit var recyclerView: RecyclerView
    private lateinit var tvCaption: TextView
    private lateinit var indicator: CircleIndicator2
    private lateinit var btnPrevious: MaterialButton
    private lateinit var btnNext: MaterialButton
    private lateinit var viewTopShadow: View
    private lateinit var viewBottomShadow: View
    private lateinit var previousButtonContainer: FrameLayout
    private lateinit var nextButtonContainer: FrameLayout

    private var dataSize = 0

    /**
     * Get of Set current item position
     */
    var currentPosition = -1
        get() {
            return indicator.getSnapPosition(recyclerView.layoutManager)
        }
        set(value) {
            val finalValue = when {
                value >= dataSize -> {
                    -1
                }
                value < 0 -> {
                    -1
                }
                else -> {
                    value
                }
            }

            field = finalValue

            Log.e(TAG, "finalValue: $finalValue")

            if (finalValue != -1) {
                recyclerView.smoothScrollToPosition(finalValue)
            }
        }

    var showTopShadow = false
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var showBottomShadow = false
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var showCaption = false
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var showIndicator = false
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var showNavigationButtons = false
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var imageScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var carouselBackground: Drawable? = null
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var imagePlaceholder: Drawable? = null
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    @LayoutRes
    var itemLayout: Int = R.layout.item_carousel
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    @IdRes
    var imageViewId: Int = R.id.img
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    @LayoutRes
    var previousButtonLayout: Int = R.layout.previous_button_layout
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    @IdRes
    var previousButtonId: Int = R.id.btn_next
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    @Dimension
    var previousButtonMargin: Float = 0F
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    @LayoutRes
    var nextButtonLayout: Int = R.layout.next_button_layout
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    @IdRes
    var nextButtonId: Int = R.id.btn_previous
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    @Dimension
    var nextButtonMargin: Float = 0F
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var carouselType: CarouselType = CarouselType.BLOCK
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var scaleOnScroll: Boolean = false
        set(value) {
            field = value
            invalidate()
            requestLayout()
        }

    var scalingFactor: Float = 0f
        set(value) {
            field = when {
                value < 0 -> {
                    0f
                }
                value > 1 -> {
                    1f
                }
                else -> {
                    value
                }
            }
            invalidate()
            requestLayout()
        }


    init {
        initAttributes()
        initViews()
        initRecyclerView()
        initListeners()
    }

    private fun initRecyclerView() {
        // Set layout manager
        recyclerView.layoutManager =
            CarouselLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false).apply {
                scaleOnScroll = this@ImageCarousel.scaleOnScroll
                scalingFactor = this@ImageCarousel.scalingFactor
            }


        // Init other attributes
        recyclerView.background = carouselBackground


        // Set adapter
        adapter = CarouselAdapter(
            context,
            recyclerView,
            carouselType,
            itemLayout,
            imageViewId,
            onItemClickListener,
            imageScaleType,
            imagePlaceholder
        )
        recyclerView.adapter = adapter


        // Init SnapHelper and Indicator
        var pagerSnapHelper: SnapHelper? = null
        if (carouselType == CarouselType.BLOCK) {
            pagerSnapHelper = PagerSnapHelper()
        } else if (carouselType == CarouselType.SHOWCASE) {
            pagerSnapHelper = LinearSnapHelper()
        }
        pagerSnapHelper?.apply {
            attachToRecyclerView(recyclerView)
            indicator.attachToRecyclerView(recyclerView, this)
            adapter.registerAdapterDataObserver(indicator.adapterDataObserver)
        }
    }

    private fun initViews() {

        val carouselView = LayoutInflater.from(context).inflate(R.layout.image_carousel, this)

        recyclerView = carouselView.findViewById(R.id.recyclerView)
        tvCaption = carouselView.findViewById(R.id.tv_caption)
        indicator = carouselView.findViewById(R.id.indicator)
        viewTopShadow = carouselView.findViewById(R.id.view_top_shadow)
        viewBottomShadow = carouselView.findViewById(R.id.view_bottom_shadow)
        previousButtonContainer = carouselView.findViewById(R.id.previous_button_container)
        nextButtonContainer = carouselView.findViewById(R.id.next_button_container)

        // Inflate views
        val inflater = LayoutInflater.from(context)
        inflater.inflate(previousButtonLayout, previousButtonContainer, true)
        inflater.inflate(nextButtonLayout, nextButtonContainer, true)

        btnPrevious = carouselView.findViewById(previousButtonId)
        btnNext = carouselView.findViewById(nextButtonId)


        // Init Margins
        val previousButtonParams =
            previousButtonContainer.layoutParams as ConstraintLayout.LayoutParams
        previousButtonParams.setMargins(previousButtonMargin.toPx(context), 0, 0, 0)
        previousButtonContainer.layoutParams = previousButtonParams

        val nextButtonParams = nextButtonContainer.layoutParams as ConstraintLayout.LayoutParams
        nextButtonParams.setMargins(0, 0, nextButtonMargin.toPx(context), 0)
        nextButtonContainer.layoutParams = nextButtonParams


        // Init visibility
        viewTopShadow.visibility = if (showTopShadow) View.VISIBLE else View.GONE
        viewBottomShadow.visibility = if (showBottomShadow) View.VISIBLE else View.GONE
        tvCaption.visibility = if (showCaption) View.VISIBLE else View.GONE
        indicator.visibility = if (showIndicator) View.VISIBLE else View.GONE
        btnPrevious.visibility = if (showNavigationButtons) View.VISIBLE else View.GONE
        btnNext.visibility = if (showNavigationButtons) View.VISIBLE else View.GONE
    }

    private fun initListeners() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val position =
                    indicator.getSnapPosition(this@ImageCarousel.recyclerView.layoutManager)

                Log.d(TAG, "position: $position")

                if (position >= 0) {
                    val dataItem = adapter.getItem(position)

                    dataItem?.apply {
                        tvCaption.text = this.caption
                    }
                }

            }
        })

        btnPrevious.setOnClickListener {
            val position = indicator.getSnapPosition(recyclerView.layoutManager)

            if (position > 0) {
                recyclerView.smoothScrollToPosition(position - 1)
            }
        }

        btnNext.setOnClickListener {
            recyclerView.adapter?.apply {
                val position = indicator.getSnapPosition(recyclerView.layoutManager)

                if (position < this.itemCount - 1) {
                    recyclerView.smoothScrollToPosition(position + 1)
                }
            }
        }
    }

    private fun initAttributes() {
        context.theme.obtainStyledAttributes(
            attributeSet,
            R.styleable.ImageCarousel,
            0,
            0
        ).apply {

            try {
                imageScaleType = scaleTypeArray[
                        getInteger(
                            R.styleable.ImageCarousel_imageScaleType,
                            ImageView.ScaleType.CENTER_CROP.ordinal
                        )
                ]

                showTopShadow = getBoolean(R.styleable.ImageCarousel_showTopShadow, true)

                showBottomShadow = getBoolean(
                    R.styleable.ImageCarousel_showBottomShadow,
                    true
                )

                showCaption = getBoolean(R.styleable.ImageCarousel_showCaption, true)

                showIndicator = getBoolean(R.styleable.ImageCarousel_showIndicator, true)

                showNavigationButtons = getBoolean(
                    R.styleable.ImageCarousel_showNavigationButtons,
                    true
                )

                carouselBackground = getDrawable(
                    R.styleable.ImageCarousel_carouselBackground
                ) ?: ColorDrawable(Color.parseColor("#333333"))

                imagePlaceholder = getDrawable(
                    R.styleable.ImageCarousel_imagePlaceholder
                ) ?: ContextCompat.getDrawable(context, R.drawable.ic_picture)

                itemLayout = getResourceId(
                    R.styleable.ImageCarousel_itemLayout,
                    R.layout.item_carousel
                )

                imageViewId = getResourceId(
                    R.styleable.ImageCarousel_imageViewId,
                    R.id.img
                )

                previousButtonLayout = getResourceId(
                    R.styleable.ImageCarousel_previousButtonLayout,
                    R.layout.previous_button_layout
                )

                previousButtonId = getResourceId(
                    R.styleable.ImageCarousel_previousButtonId,
                    R.id.btn_previous
                )

                previousButtonMargin = getDimension(
                    R.styleable.ImageCarousel_previousButtonMargin,
                    4F
                )

                nextButtonLayout = getResourceId(
                    R.styleable.ImageCarousel_nextButtonLayout,
                    R.layout.next_button_layout
                )

                nextButtonId = getResourceId(
                    R.styleable.ImageCarousel_nextButtonId,
                    R.id.btn_next
                )

                nextButtonMargin = getDimension(
                    R.styleable.ImageCarousel_nextButtonMargin,
                    4F
                )

                carouselType = carouselTypeArray[
                        getInteger(
                            R.styleable.ImageCarousel_carouselType,
                            CarouselType.BLOCK.ordinal
                        )
                ]

                scaleOnScroll = getBoolean(
                    R.styleable.ImageCarousel_scaleOnScroll,
                    false
                )

                scalingFactor = getFloat(
                    R.styleable.ImageCarousel_scalingFactor,
                    .15f
                )

            } finally {
                recycle()
            }

        }
    }

    // ----------------------------------------------------------------

    fun addData(data: List<CarouselItem>) {
        adapter.addAll(data)

        dataSize = data.size
    }

    // ----------------------------------------------------------------

    fun previous() {
        currentPosition = currentPosition - 1

        Log.e(TAG, "currentPosition: $currentPosition")
    }

    // ----------------------------------------------------------------

    fun next() {
        currentPosition = currentPosition + 1

        Log.e(TAG, "currentPosition: $currentPosition")
    }

}