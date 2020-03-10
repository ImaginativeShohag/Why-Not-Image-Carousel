package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
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
import androidx.annotation.Px
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.*
import me.relex.circleindicator.CircleIndicator2
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class ImageCarousel(
    @NotNull context: Context,
    @Nullable private var attributeSet: AttributeSet?
) : ConstraintLayout(context, attributeSet) {

    val TAG = "ImageCarousel"

    private var adapter: CarouselAdapter? = null

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

    private lateinit var carouselView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvCaption: TextView
    private lateinit var btnPrevious: View
    private lateinit var btnNext: View
    private lateinit var viewTopShadow: View
    private lateinit var viewBottomShadow: View
    private lateinit var previousButtonContainer: FrameLayout
    private lateinit var nextButtonContainer: FrameLayout
    private lateinit var snapHelper: SnapHelper

    private var autoPlayHandler: Handler = Handler()

    private var data: List<CarouselItem>? = null
    private var dataSize = 0
    private var indicator: CircleIndicator2? = null

    var onItemClickListener: OnItemClickListener? = null
        set(value) {
            field = value

            adapter?.listener = onItemClickListener
        }

    var onScrollListener: CarouselOnScrollListener? = null

    /**
     * Get of set current item position
     */
    var currentPosition = -1
        get() {
            return snapHelper.getSnapPosition(recyclerView.layoutManager)
        }
        set(value) {
            val position = when {
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

            field = position

            if (position != -1) {
                recyclerView.smoothScrollToPosition(position)
            }
        }

    /**
     * ****************************************************************
     * Attributes
     * ****************************************************************
     */
    var showTopShadow = false
        set(value) {
            field = value

            viewTopShadow.visibility = if (showTopShadow) View.VISIBLE else View.GONE
        }

    var topShadowAlpha: Float = 0f
        set(value) {
            field = getValueFromZeroToOne(value)

            viewTopShadow.alpha = topShadowAlpha
        }

    var showBottomShadow = false
        set(value) {
            field = value

            viewBottomShadow.visibility = if (showBottomShadow) View.VISIBLE else View.GONE
        }

    var bottomShadowAlpha: Float = 0f
        set(value) {
            field = getValueFromZeroToOne(value)

            viewBottomShadow.alpha = bottomShadowAlpha
        }

    var showCaption = false
        set(value) {
            field = value

            tvCaption.visibility = if (showCaption) View.VISIBLE else View.GONE
        }

    var showIndicator = false
        set(value) {
            field = value

            initIndicator()
        }

    var showNavigationButtons = false
        set(value) {
            field = value

            btnPrevious.visibility = if (showNavigationButtons) View.VISIBLE else View.GONE
            btnNext.visibility = if (showNavigationButtons) View.VISIBLE else View.GONE
        }

    var imageScaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_CROP
        set(value) {
            field = value

            initAdapter()
        }

    var carouselBackground: Drawable? = null
        set(value) {
            field = value

            recyclerView.background = carouselBackground
        }

    var imagePlaceholder: Drawable? = null
        set(value) {
            field = value

            initAdapter()
        }

    @LayoutRes
    var itemLayout: Int = R.layout.item_carousel
        set(value) {
            field = value

            initAdapter()
        }

    @IdRes
    var imageViewId: Int = R.id.img
        set(value) {
            field = value

            initAdapter()
        }

    @LayoutRes
    var previousButtonLayout: Int = R.layout.previous_button_layout
        set(value) {
            field = value

            LayoutInflater.from(context).apply {
                inflate(previousButtonLayout, previousButtonContainer, true)
            }
        }

    @IdRes
    var previousButtonId: Int = R.id.btn_next
        set(value) {
            field = value

            btnPrevious = carouselView.findViewById(previousButtonId)
        }

    /**
     * Margin dp value.
     */
    @Dimension
    @Px
    var previousButtonMargin: Int = 0
        set(value) {
            field = value

            val previousButtonParams =
                previousButtonContainer.layoutParams as LayoutParams
            previousButtonParams.setMargins(previousButtonMargin.toPx(context), 0, 0, 0)
            previousButtonContainer.layoutParams = previousButtonParams
        }

    @LayoutRes
    var nextButtonLayout: Int = R.layout.next_button_layout
        set(value) {
            field = value

            LayoutInflater.from(context).apply {
                inflate(nextButtonLayout, nextButtonContainer, true)
            }
        }

    @IdRes
    var nextButtonId: Int = R.id.btn_previous
        set(value) {
            field = value

            btnNext = carouselView.findViewById(nextButtonId)
        }

    /**
     * Margin dp value.
     */
    @Dimension
    @Px
    var nextButtonMargin: Int = 0
        set(value) {
            field = value

            val nextButtonParams = nextButtonContainer.layoutParams as LayoutParams
            nextButtonParams.setMargins(0, 0, nextButtonMargin.toPx(context), 0)
            nextButtonContainer.layoutParams = nextButtonParams
        }

    var carouselType: CarouselType = CarouselType.BLOCK
        set(value) {
            field = value

            if (carouselType == CarouselType.BLOCK) {
                snapHelper = PagerSnapHelper()
            } else if (carouselType == CarouselType.SHOWCASE) {
                snapHelper = LinearSnapHelper()
            }

            snapHelper.apply {
                attachToRecyclerView(recyclerView)
            }
        }

    var scaleOnScroll: Boolean = false
        set(value) {
            field = value

            recyclerView.layoutManager?.apply {
                if (this is CarouselLinearLayoutManager) {
                    this.scaleOnScroll = this@ImageCarousel.scaleOnScroll
                }
            }
        }

    var scalingFactor: Float = 0f
        set(value) {
            field = getValueFromZeroToOne(value)

            recyclerView.layoutManager?.apply {
                if (this is CarouselLinearLayoutManager) {
                    this.scalingFactor = this@ImageCarousel.scalingFactor
                }
            }
        }

    var autoWidthFixing: Boolean = false
        set(value) {
            field = value

            initAdapter()
        }

    var autoPlay: Boolean = false
        set(value) {
            field = value
            // Note: We do not need to invalidate the view for this.
        }

    var autoPlayDelay: Int = 0
        set(value) {
            field = value
            // Note: We do not need to invalidate the view for this.
        }


    init {
        initViews()
        initAttributes()
        initAdapter()
//        initIndicator()
        initListeners()
        initAutoPlay()
    }

    private fun initAdapter() {
        // Set layout manager


        // Init other attributes
//        recyclerView.setHasFixedSize(true)
//        recyclerView.background = carouselBackground


        // Set adapter
        adapter = CarouselAdapter(
            context = context,
            recyclerView = recyclerView,
            carouselType = carouselType,
            autoWidthFixing = autoWidthFixing,
            itemLayout = itemLayout,
            imageViewId = imageViewId,
            listener = onItemClickListener,
            imageScaleType = imageScaleType,
            imagePlaceholder = imagePlaceholder
        )
        recyclerView.adapter = adapter

        if (showIndicator) {
            indicator?.apply {
                adapter?.registerAdapterDataObserver(this.adapterDataObserver)
            }
        }

        // Init SnapHelper and Indicator
//        if (carouselType == CarouselType.BLOCK) {
//            snapHelper = PagerSnapHelper()
//        } else if (carouselType == CarouselType.SHOWCASE) {
//            snapHelper = LinearSnapHelper()
//        }
//
//        snapHelper.apply {
//            attachToRecyclerView(recyclerView)
//        }

    }

    private fun initViews() {
        carouselView = LayoutInflater.from(context).inflate(R.layout.image_carousel, this)

        recyclerView = carouselView.findViewById(R.id.recyclerView)
        tvCaption = carouselView.findViewById(R.id.tv_caption)
        viewTopShadow = carouselView.findViewById(R.id.view_top_shadow)
        viewBottomShadow = carouselView.findViewById(R.id.view_bottom_shadow)
        previousButtonContainer = carouselView.findViewById(R.id.previous_button_container)
        nextButtonContainer = carouselView.findViewById(R.id.next_button_container)

        recyclerView.layoutManager =
            CarouselLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                .apply {
                    scaleOnScroll = this@ImageCarousel.scaleOnScroll
                    scalingFactor = this@ImageCarousel.scalingFactor
                }
        recyclerView.setHasFixedSize(true)

        // Inflate views
//        LayoutInflater.from(context).apply {
//            inflate(previousButtonLayout, previousButtonContainer, true)
//            inflate(nextButtonLayout, nextButtonContainer, true)
//        }
//
//        btnPrevious = carouselView.findViewById(previousButtonId)
//        btnNext = carouselView.findViewById(nextButtonId)


        // Init Alpha
//        viewTopShadow.alpha = topShadowAlpha
//        viewBottomShadow.alpha = bottomShadowAlpha


        // Init Margins
//        val previousButtonParams =
//            previousButtonContainer.layoutParams as LayoutParams
//        previousButtonParams.setMargins(previousButtonMargin.toPx(context), 0, 0, 0)
//        previousButtonContainer.layoutParams = previousButtonParams

//        val nextButtonParams = nextButtonContainer.layoutParams as LayoutParams
//        nextButtonParams.setMargins(0, 0, nextButtonMargin.toPx(context), 0)
//        nextButtonContainer.layoutParams = nextButtonParams


        // Init visibility
//        viewTopShadow.visibility = if (showTopShadow) View.VISIBLE else View.GONE
//        viewBottomShadow.visibility = if (showBottomShadow) View.VISIBLE else View.GONE
//        tvCaption.visibility = if (showCaption) View.VISIBLE else View.GONE
//        btnPrevious.visibility = if (showNavigationButtons) View.VISIBLE else View.GONE
//        btnNext.visibility = if (showNavigationButtons) View.VISIBLE else View.GONE
    }

    private fun initListeners() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (showCaption) {
                    val position = snapHelper.getSnapPosition(recyclerView.layoutManager)

                    if (position >= 0) {
                        val dataItem = adapter?.getItem(position)

                        dataItem?.apply {
                            tvCaption.text = this.caption
                        }
                    }
                }

                onScrollListener?.onScrolled(recyclerView, dx, dy)

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                val position = snapHelper.getSnapPosition(recyclerView.layoutManager)
                val carouselItem = data?.get(position)

                onScrollListener?.onScrollStateChanged(
                    recyclerView,
                    newState,
                    position,
                    carouselItem
                )

            }
        })

        btnPrevious.setOnClickListener {
            previous()
        }

        btnNext.setOnClickListener {
            next()
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

                showTopShadow = getBoolean(
                    R.styleable.ImageCarousel_showTopShadow,
                    true
                )

                topShadowAlpha = getFloat(
                    R.styleable.ImageCarousel_topShadowAlpha,
                    0.6f
                )

                showBottomShadow = getBoolean(
                    R.styleable.ImageCarousel_showBottomShadow,
                    true
                )

                bottomShadowAlpha = getFloat(
                    R.styleable.ImageCarousel_bottomShadowAlpha,
                    0.6f
                )

                showCaption = getBoolean(
                    R.styleable.ImageCarousel_showCaption,
                    true
                )

                carouselType = carouselTypeArray[
                        getInteger(
                            R.styleable.ImageCarousel_carouselType,
                            CarouselType.BLOCK.ordinal
                        )
                ]

                showIndicator = getBoolean(
                    R.styleable.ImageCarousel_showIndicator,
                    true
                )

                imageScaleType = scaleTypeArray[
                        getInteger(
                            R.styleable.ImageCarousel_imageScaleType,
                            ImageView.ScaleType.CENTER_CROP.ordinal
                        )
                ]

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
                    4.toPx(context).toFloat()
                ).toInt()

                Log.e(TAG, "previousButtonMargin: $previousButtonMargin")

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
                    4.toPx(context).toFloat()
                ).toInt()

                showNavigationButtons = getBoolean(
                    R.styleable.ImageCarousel_showNavigationButtons,
                    true
                )

                scaleOnScroll = getBoolean(
                    R.styleable.ImageCarousel_scaleOnScroll,
                    false
                )

                scalingFactor = getFloat(
                    R.styleable.ImageCarousel_scalingFactor,
                    .15F
                )

                autoWidthFixing = getBoolean(
                    R.styleable.ImageCarousel_autoWidthFixing,
                    true
                )

                autoPlay = getBoolean(
                    R.styleable.ImageCarousel_autoPlay,
                    false
                )

                autoPlayDelay = getInt(
                    R.styleable.ImageCarousel_autoPlayDelay,
                    3000
                )

            } finally {
                recycle()
            }

        }
    }

    /**
     * Initialize and start/stop auto play based on `autoPlay` value.
     */
    private fun initAutoPlay() {
        if (autoPlay) {

            autoPlayHandler.postDelayed(object : Runnable {
                override fun run() {
                    if (currentPosition == dataSize - 1) {
                        currentPosition = 0
                    } else {
                        currentPosition++
                    }

                    autoPlayHandler.postDelayed(this, autoPlayDelay.toLong())
                }
            }, autoPlayDelay.toLong())

        } else {

            autoPlayHandler.removeCallbacksAndMessages(null)

        }
    }

    private fun initIndicator() {

        if (showIndicator) {

            // If no custom indicator added, then default indicator will be shown.
            if (indicator == null) {
                indicator = carouselView.findViewById(R.id.indicator)
            }

            indicator?.apply {
                attachToRecyclerView(recyclerView, snapHelper)

                adapter?.let {carouselAdapter ->

                    if(carouselAdapter.hasObservers()) {
//                        carouselAdapter.unregisterAdapterDataObserver()
                    }

                    carouselAdapter.registerAdapterDataObserver(this.adapterDataObserver)
                }

                visibility = View.VISIBLE
            }

        } else {

            indicator?.visibility = View.GONE

        }


    }

    private fun getValueFromZeroToOne(value: Float): Float {
        return when {
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
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        stop()
    }

    // ----------------------------------------------------------------

    /**
     * Add data to the carousel.
     */
    fun addData(data: List<CarouselItem>) {
        adapter?.apply {
            addAll(data)

            this@ImageCarousel.data = data
            this@ImageCarousel.dataSize = data.size
        }
    }

    // ----------------------------------------------------------------

    /**
     * @return Current indicator or null.
     */
    fun getIndicator(): CircleIndicator2? {
        return indicator
    }

    /**
     * Set custom indicator.
     */
    fun setIndicator(newIndicator: CircleIndicator2) {
        indicator?.apply {
            // if we remove it form the view, then the caption textView constraint won't work.
            this.visibility = View.GONE
        }

        this.indicator = newIndicator

        initIndicator()
    }

    // ----------------------------------------------------------------

    /**
     * Goto previous image.
     */
    fun previous() {
        currentPosition--
    }

    // ----------------------------------------------------------------

    /**
     * Goto next image.
     */
    fun next() {
        currentPosition++
    }

    // ----------------------------------------------------------------

    /**
     * Start auto play.
     */
    fun start() {
        autoPlay = true

        initAutoPlay()
    }

    // ----------------------------------------------------------------

    /**
     * Stop auto play.
     */
    fun stop() {
        autoPlay = false

        initAutoPlay()
    }

}