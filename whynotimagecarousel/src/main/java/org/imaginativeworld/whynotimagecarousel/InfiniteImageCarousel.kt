package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
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
import org.imaginativeworld.whynotimagecarousel.adapter.InfiniteCarouselAdapter
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.listener.CarouselOnScrollListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselGravity
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.model.CarouselType
import org.imaginativeworld.whynotimagecarousel.utils.CarouselLinearLayoutManager
import org.imaginativeworld.whynotimagecarousel.utils.LinearStartSnapHelper
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class InfiniteImageCarousel(
    @NotNull context: Context,
    @Nullable private var attributeSet: AttributeSet?
) : ConstraintLayout(context, attributeSet) {

    companion object {
        const val TAG = "ImageCarousel"
    }

    private var adapter: InfiniteCarouselAdapter? = null

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

    private val carouselGravityArray = arrayOf(
        CarouselGravity.START,
        CarouselGravity.CENTER
    )

    private lateinit var carouselView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvCaption: TextView
    private lateinit var viewTopShadow: View
    private lateinit var viewBottomShadow: View
    private lateinit var previousButtonContainer: FrameLayout
    private lateinit var nextButtonContainer: FrameLayout
    private var snapHelper: SnapHelper? = null

    private var btnPrevious: View? = null
    private var btnNext: View? = null

    private var autoPlayHandler: Handler = Handler()
    private var data: List<CarouselItem>? = null
    private var dataSize = 0

    var carouselListener: CarouselListener? = null
        set(value) {
            field = value

            adapter?.listener = carouselListener
        }

    var onScrollListener: CarouselOnScrollListener? = null
        set(value) {
            field = value

            initOnScrollStateChange()
        }

    /**
     * Get or set current item position
     */
    var currentPosition = -1
        get() {
            return snapHelper?.getSnapPosition(recyclerView.layoutManager) ?: -1
        }
        set(value) {
            val position = when {
                value >= Int.MAX_VALUE -> {
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

    @Dimension(unit = Dimension.PX)
    var topShadowHeight: Int = 0
        set(value) {
            field = value

            val topShadowParams = viewTopShadow.layoutParams as LayoutParams
            topShadowParams.height = topShadowHeight
            viewTopShadow.layoutParams = topShadowParams
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

    @Dimension(unit = Dimension.PX)
    var bottomShadowHeight: Int = 0
        set(value) {
            field = value

            val bottomShadowParams = viewBottomShadow.layoutParams as LayoutParams
            bottomShadowParams.height = bottomShadowHeight
            viewBottomShadow.layoutParams = bottomShadowParams
        }

    var showCaption = false
        set(value) {
            field = value

            tvCaption.visibility = if (showCaption) View.VISIBLE else View.GONE
        }

    @Dimension(unit = Dimension.PX)
    var captionMargin: Int = 0
        set(value) {
            field = value

            val captionMarginParams = tvCaption.layoutParams as LayoutParams
            captionMarginParams.setMargins(
                0,
                0,
                0,
                captionMargin
            )
            captionMarginParams.goneBottomMargin = captionMargin
            tvCaption.layoutParams = captionMarginParams
        }

    @Dimension(unit = Dimension.PX)
    var captionTextSize: Int = 0
        set(value) {
            field = value

            tvCaption.setTextSize(TypedValue.COMPLEX_UNIT_PX, captionTextSize.toFloat())
        }

    var showNavigationButtons = false
        set(value) {
            field = value

            previousButtonContainer.visibility =
                if (showNavigationButtons) View.VISIBLE else View.GONE
            nextButtonContainer.visibility =
                if (showNavigationButtons) View.VISIBLE else View.GONE
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
    var previousButtonLayout: Int = R.layout.previous_button_layout
        set(value) {
            field = value

            btnPrevious = null

            previousButtonContainer.removeAllViews()
            LayoutInflater.from(context).apply {
                inflate(previousButtonLayout, previousButtonContainer, true)
            }
        }

    @IdRes
    var previousButtonId: Int = R.id.btn_next
        set(value) {
            field = value

            btnPrevious = carouselView.findViewById(previousButtonId)

            btnPrevious?.setOnClickListener {
                previous()
            }
        }

    @Dimension(unit = Dimension.PX)
    var previousButtonMargin: Int = 0
        set(value) {
            field = value

            val previousButtonParams = previousButtonContainer.layoutParams as LayoutParams
            previousButtonParams.setMargins(
                previousButtonMargin,
                0,
                0,
                0
            )
            previousButtonContainer.layoutParams = previousButtonParams
        }

    @LayoutRes
    var nextButtonLayout: Int = R.layout.next_button_layout
        set(value) {
            field = value

            btnNext = null

            nextButtonContainer.removeAllViews()
            LayoutInflater.from(context).apply {
                inflate(nextButtonLayout, nextButtonContainer, true)
            }
        }

    @IdRes
    var nextButtonId: Int = R.id.btn_previous
        set(value) {
            field = value

            btnNext = carouselView.findViewById(nextButtonId)

            btnNext?.setOnClickListener {
                next()
            }
        }

    @Dimension(unit = Dimension.PX)
    var nextButtonMargin: Int = 0
        set(value) {
            field = value

            val nextButtonParams = nextButtonContainer.layoutParams as LayoutParams
            nextButtonParams.setMargins(
                0,
                0,
                nextButtonMargin,
                0
            )
            nextButtonContainer.layoutParams = nextButtonParams
        }

    var carouselType: CarouselType = CarouselType.BLOCK
        set(value) {
            field = value

            updateSnapHelper()
        }

    var carouselGravity: CarouselGravity = CarouselGravity.CENTER
        set(value) {
            field = value

            recyclerView.layoutManager?.apply {
                if (this is CarouselLinearLayoutManager) {
                    this.isOffsetStart = carouselGravity == CarouselGravity.START
                }
            }

            updateSnapHelper()
        }

    private fun updateSnapHelper() {
        snapHelper?.attachToRecyclerView(null)

        snapHelper = if (carouselType == CarouselType.BLOCK) {
            Log.e(TAG, "CarouselType.BLOCK")
            PagerSnapHelper()
        } else { // CarouselType.SHOWCASE
            if (carouselGravity == CarouselGravity.START) {
                Log.e(TAG, "CarouselGravity.START")
                LinearStartSnapHelper()
            } else { // CarouselGravity.CENTER
                Log.e(TAG, "CarouselGravity.CENTER")
                LinearSnapHelper()
            }
        }

        snapHelper?.also {
            try {
                it.attachToRecyclerView(recyclerView)
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }

    var scaleOnScroll: Boolean = false
        set(value) {
            field = value

            recyclerView.layoutManager?.apply {
                if (this is CarouselLinearLayoutManager) {
                    this.scaleOnScroll = this@InfiniteImageCarousel.scaleOnScroll
                }
            }
        }

    var scalingFactor: Float = 0f
        set(value) {
            field = getValueFromZeroToOne(value)

            recyclerView.layoutManager?.apply {
                if (this is CarouselLinearLayoutManager) {
                    this.scalingFactor = this@InfiniteImageCarousel.scalingFactor
                }
            }
        }

    var autoWidthFixing: Boolean = false
        set(value) {
            field = value

            initAdapter()
        }

    // Note: We do not need to invalidate the view for this.
    var autoPlay: Boolean = false

    // Note: We do not need to invalidate the view for this.
    var autoPlayDelay: Int = 0

    init {
        initViews()
        initAttributes()
        initAdapter()
        initListeners()
        initAutoPlay()
    }

    private fun initViews() {
        carouselView = LayoutInflater.from(context).inflate(R.layout.infinite_image_carousel, this)

        recyclerView = carouselView.findViewById(R.id.recyclerView)
        tvCaption = carouselView.findViewById(R.id.tv_caption)
        viewTopShadow = carouselView.findViewById(R.id.view_top_shadow)
        viewBottomShadow = carouselView.findViewById(R.id.view_bottom_shadow)
        previousButtonContainer = carouselView.findViewById(R.id.previous_button_container)
        nextButtonContainer = carouselView.findViewById(R.id.next_button_container)

        recyclerView.layoutManager =
            CarouselLinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                .apply {
                    scaleOnScroll = this@InfiniteImageCarousel.scaleOnScroll
                    scalingFactor = this@InfiniteImageCarousel.scalingFactor
                }
        recyclerView.setHasFixedSize(true)

        // For marquee effect
        tvCaption.isSelected = true
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

                topShadowHeight = getDimension(
                    R.styleable.ImageCarousel_topShadowHeight,
                    32.dpToPx(context).toFloat()
                ).toInt()

                showBottomShadow = getBoolean(
                    R.styleable.ImageCarousel_showBottomShadow,
                    true
                )

                bottomShadowAlpha = getFloat(
                    R.styleable.ImageCarousel_bottomShadowAlpha,
                    0.6f
                )

                bottomShadowHeight = getDimension(
                    R.styleable.ImageCarousel_bottomShadowHeight,
                    64.dpToPx(context).toFloat()
                ).toInt()

                showCaption = getBoolean(
                    R.styleable.ImageCarousel_showCaption,
                    true
                )

                captionMargin = getDimension(
                    R.styleable.ImageCarousel_captionMargin,
                    0.dpToPx(context).toFloat()
                ).toInt()

                captionTextSize = getDimension(
                    R.styleable.ImageCarousel_captionTextSize,
                    14.spToPx(context).toFloat()
                ).toInt()

                carouselType = carouselTypeArray[
                    getInteger(
                        R.styleable.ImageCarousel_carouselType,
                        CarouselType.BLOCK.ordinal
                    )
                ]

                carouselGravity = carouselGravityArray[
                    getInteger(
                        R.styleable.ImageCarousel_carouselGravity,
                        CarouselGravity.CENTER.ordinal
                    )
                ]

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
                    4.dpToPx(context).toFloat()
                ).toInt()

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
                    4.dpToPx(context).toFloat()
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

    private fun initAdapter() {
        adapter = InfiniteCarouselAdapter(
            recyclerView = recyclerView,
            carouselType = carouselType,
            carouselGravity = carouselGravity,
            autoWidthFixing = autoWidthFixing,
            listener = carouselListener,
            imageScaleType = imageScaleType,
            imagePlaceholder = imagePlaceholder
        )
        recyclerView.adapter = adapter

        data?.apply {
            adapter?.replaceData(this)

            recyclerView.scrollToPosition(this.size / 2)
        }
    }

    private fun initListeners() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (showCaption) {
                    val position = snapHelper?.getSnapPosition(recyclerView.layoutManager) ?: -1

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

                onScrollListener?.apply {
                    val position = snapHelper?.getSnapPosition(recyclerView.layoutManager) ?: -1

                    if (position != -1) {
                        var carouselItem: CarouselItem? = null

                        data?.let { items ->
                            carouselItem = items[position % items.size]
                        }

                        onScrollStateChanged(
                            recyclerView,
                            newState,
                            position,
                            carouselItem
                        )
                    }
                }
            }
        })
    }

    /**
     * Initialize and start/stop auto play based on `autoPlay` value.
     */
    private fun initAutoPlay() {
        if (autoPlay) {

            autoPlayHandler.postDelayed(
                object : Runnable {
                    override fun run() {
                        if (currentPosition == Int.MAX_VALUE - 1) {
                            currentPosition = 0
                        } else {
                            currentPosition++
                        }

                        autoPlayHandler.postDelayed(this, autoPlayDelay.toLong())
                    }
                },
                autoPlayDelay.toLong()
            )
        } else {

            autoPlayHandler.removeCallbacksAndMessages(null)
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

    /**
     * This is called because when data added for the first time,
     * the related view is not changed as expected.
     */
    private fun initOnScrollStateChange() {
        data?.apply {
            if (isNotEmpty()) {
                onScrollListener?.onScrollStateChanged(
                    recyclerView,
                    RecyclerView.SCROLL_STATE_IDLE,
                    0,
                    this[0]
                )
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        stop()
    }

    // ----------------------------------------------------------------

    /**
     * Set new data to the carousel. It removes all previous data.
     */
    fun setData(data: List<CarouselItem>) {
        adapter?.apply {
            replaceData(data)

            this@InfiniteImageCarousel.data = data
            this@InfiniteImageCarousel.dataSize = data.size

            initOnScrollStateChange()
        }
    }

    /**
     * Add multiple data to the carousel.
     */
    fun addData(data: List<CarouselItem>) {
        adapter?.apply {
            appendData(data)

            this@InfiniteImageCarousel.data = data
            this@InfiniteImageCarousel.dataSize = data.size

            initOnScrollStateChange()
        }
    }

    /**
     * Add single data to the carousel.
     */
    fun addData(item: CarouselItem) {
        adapter?.apply {
            val data = appendData(item)

            this@InfiniteImageCarousel.data = data
            this@InfiniteImageCarousel.dataSize = data.size

            initOnScrollStateChange()
        }
    }

    // ----------------------------------------------------------------

    fun initStartPosition() {
        val halfSize = Integer.MAX_VALUE / 2
        val offset = halfSize % (if (dataSize == 0) 1 else dataSize)

        recyclerView.scrollToPosition(halfSize - offset - 1)
        Handler().postDelayed(
            { recyclerView.smoothScrollToPosition(halfSize - offset) },
            100
        )
    }

    // ----------------------------------------------------------------

    /**
     * Goto previous item.
     */
    fun previous() {
        currentPosition--
    }

    // ----------------------------------------------------------------

    /**
     * Goto next item.
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
