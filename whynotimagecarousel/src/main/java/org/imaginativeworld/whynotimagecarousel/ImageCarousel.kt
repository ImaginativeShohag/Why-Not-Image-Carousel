package org.imaginativeworld.whynotimagecarousel

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import me.relex.circleindicator.CircleIndicator2
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

class ImageCarousel(
    @NotNull context: Context,
    @Nullable private var attributeSet: AttributeSet?
) : ConstraintLayout(context, attributeSet) {

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

    private lateinit var rvImages: RecyclerView
    private lateinit var tvCaption: TextView
    private lateinit var indicator: CircleIndicator2
    private lateinit var btnPrevious: MaterialButton
    private lateinit var btnNext: MaterialButton
    private lateinit var viewTopShadow: View
    private lateinit var viewBottomShadow: View

    private var showTopShadow = false
    private var showBottomShadow = false
    private var showCaption = false
    private var showIndicator = false
    private var showNavigationButtons = false
    private lateinit var imageScaleType: ImageView.ScaleType
    private lateinit var carouselBackground: Drawable
    private var imagePlaceholder: Drawable? = null

    init {
        initAttributes()
        initAdapter()
        initViews()
        initListeners()
    }

    private fun initAdapter() {
        adapter = CarouselAdapter(
            context,
            null,
            imageScaleType,
            imagePlaceholder
        )
    }

    private fun initViews() {

        val carouselView = LayoutInflater.from(context).inflate(R.layout.image_carousel, this)

        rvImages = carouselView.findViewById(R.id.rv_images)
        tvCaption = carouselView.findViewById(R.id.tv_caption)
        indicator = carouselView.findViewById(R.id.indicator)
        btnPrevious = carouselView.findViewById(R.id.btn_previous)
        btnNext = carouselView.findViewById(R.id.btn_next)
        viewTopShadow = carouselView.findViewById(R.id.view_top_shadow)
        viewBottomShadow = carouselView.findViewById(R.id.view_bottom_shadow)

        // Recycler view
        rvImages.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvImages.adapter = adapter
        rvImages.background = carouselBackground

        val pagerSnapHelper = PagerSnapHelper()
        pagerSnapHelper.attachToRecyclerView(rvImages)

        indicator.attachToRecyclerView(rvImages, pagerSnapHelper)
        adapter.registerAdapterDataObserver(indicator.adapterDataObserver)

        // Init visibility
        viewTopShadow.visibility = if (showTopShadow) View.VISIBLE else View.GONE
        viewBottomShadow.visibility = if (showBottomShadow) View.VISIBLE else View.GONE
        tvCaption.visibility = if (showCaption) View.VISIBLE else View.GONE
        indicator.visibility = if (showIndicator) View.VISIBLE else View.GONE
        btnPrevious.visibility = if (showNavigationButtons) View.VISIBLE else View.GONE
        btnNext.visibility = if (showNavigationButtons) View.VISIBLE else View.GONE
    }

    private fun initListeners() {
        rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                val position = indicator.getSnapPosition(rvImages.layoutManager)

                if (position >= 0) {
                    val dataItem = adapter.getItem(position)

                    dataItem?.apply {
                        tvCaption.text = this.caption
                    }
                }

            }
        })

        btnPrevious.setOnClickListener {
            val position = indicator.getSnapPosition(rvImages.layoutManager)

            if (position > 0) {
                rvImages.smoothScrollToPosition(position - 1)
            }
        }

        btnNext.setOnClickListener {
            rvImages.adapter?.apply {
                val position = indicator.getSnapPosition(rvImages.layoutManager)

                if (position < this.itemCount - 1) {
                    rvImages.smoothScrollToPosition(position + 1)
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
                setImageScaleType(
                    scaleTypeArray[
                            getInteger(
                                R.styleable.ImageCarousel_imageScaleType,
                                ImageView.ScaleType.CENTER_CROP.ordinal
                            )
                    ]
                )
                setShowTopShadow(getBoolean(R.styleable.ImageCarousel_showTopShadow, true))
                setShowBottomShadow(
                    getBoolean(
                        R.styleable.ImageCarousel_showBottomShadow,
                        true
                    )
                )
                setShowCaption(getBoolean(R.styleable.ImageCarousel_showCaption, true))
                setShowIndicator(getBoolean(R.styleable.ImageCarousel_showIndicator, true))
                setShowNavigationButtons(
                    getBoolean(
                        R.styleable.ImageCarousel_showNavigationButtons,
                        true
                    )
                )
                setImageBackground(
                    getDrawable(
                        R.styleable.ImageCarousel_carouselBackground
                    ) ?: ColorDrawable(Color.parseColor("#333333"))
                )
                setImagePlaceholder(
                    getDrawable(
                        R.styleable.ImageCarousel_imagePlaceholder
                    ) ?: ContextCompat.getDrawable(context, R.drawable.ic_picture)
                )
            } finally {
                recycle()
            }

        }
    }

    // ----------------------------------------------------------------

    public fun addData(data: List<CarouselItem>) {
        adapter.addAll(data)
    }

    // ----------------------------------------------------------------

    public fun isShowTopShadow(): Boolean {
        return showTopShadow
    }

    public fun setShowTopShadow(show: Boolean) {
        this.showTopShadow = show
        invalidate()
        requestLayout()
    }

    // ----------------------------------------------------------------

    public fun isShowBottomShadow(): Boolean {
        return showBottomShadow
    }

    public fun setShowBottomShadow(show: Boolean) {
        this.showBottomShadow = show
        invalidate()
        requestLayout()
    }

    // ----------------------------------------------------------------

    public fun isShowCaption(): Boolean {
        return showCaption
    }

    public fun setShowCaption(show: Boolean) {
        this.showCaption = show
        invalidate()
        requestLayout()
    }

    // ----------------------------------------------------------------

    public fun isShowIndicator(): Boolean {
        return showIndicator
    }

    public fun setShowIndicator(show: Boolean) {
        this.showIndicator = show
        invalidate()
        requestLayout()
    }

    // ----------------------------------------------------------------

    public fun isShowNavigationButtons(): Boolean {
        return showNavigationButtons
    }

    public fun setShowNavigationButtons(show: Boolean) {
        this.showNavigationButtons = show
        invalidate()
        requestLayout()
    }

    // ----------------------------------------------------------------

    public fun getImageScaleType(): ImageView.ScaleType {
        return imageScaleType
    }

    public fun setImageScaleType(scaleType: ImageView.ScaleType) {
        this.imageScaleType = scaleType
        invalidate()
        requestLayout()
    }

    // ----------------------------------------------------------------

    public fun getImageBackground(): Drawable {
        return carouselBackground
    }

    public fun setImageBackground(drawable: Drawable) {
        this.carouselBackground = drawable
        invalidate()
        requestLayout()
    }

    // ----------------------------------------------------------------

    public fun getImagePlaceholder(): Drawable? {
        return imagePlaceholder
    }

    public fun setImagePlaceholder(drawable: Drawable?) {
        this.imagePlaceholder = drawable
        invalidate()
        requestLayout()
    }


}