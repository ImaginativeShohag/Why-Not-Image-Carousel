# Why Not! Image Carousel!

An easy, super simple and customizable image carousel view for Android.

[![](https://jitpack.io/v/ImaginativeShohag/Why-Not-Image-Carousel.svg)](https://jitpack.io/#ImaginativeShohag/Why-Not-Image-Carousel)
[![Android Arsenal]( https://img.shields.io/badge/Android%20Arsenal-Why%20Not!%20Image%20Carousel!-green.svg?style=flat )]( https://android-arsenal.com/details/1/8053)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

## Preview

|               Screenshot                |              Preview              |
| :-------------------------------------: | :-------------------------------: |
| ![Screenshot](/images/screenshot_1.png) | ![Preview](/images/preview_1.gif) |

## Usage

### Dependency

#### Step 1. Add the JitPack repository to your build file

Add it to your root **build.gradle** at the end of repositories:

```groovy
allprojects {
    repositories {
        // ...
        maven { url 'https://jitpack.io' }
    }
}
```

#### Step 2. Add the dependency

```groovy
dependencies {
    // Material Components for Android
    implementation 'com.google.android.material:material:1.1.0'
    
    // Optional: Circle Indicator (To fix the xml preview "Missing classes" error)
    implementation 'me.relex:circleindicator:2.1.4'

    implementation 'com.github.ImaginativeShohag:Why-Not-Image-Carousel:v1.0.2'
}
```

**Note 0.** Minimum SDK for this library is **API 21** (Android 5.0 Lollipop).

**Note 1.** Your application have to use **AndroidX** to use this library.

**Note 2.** Your have to use **\*.MaterialComponents.\*** in you styles.

### Finally

Add the view `org.imaginativeworld.whynotimagecarousel.ImageCarousel` in your layout:

```
<org.imaginativeworld.whynotimagecarousel.ImageCarousel
    android:id="@+id/carousel"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

Use the `CarouselItem` class for data item. Initialize the `ImageCarousel` with data using `addData()` function:

```kotlin
// Kotlin
val carousel: ImageCarousel = findViewById(R.id.carousel)

val list = mutableListOf<CarouselItem>()

list.add(
    CarouselItem(
        imageUrl = "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
        caption = "Photo by Aaron Wu on Unsplash"
    )
)
list.add(
    CarouselItem(
        imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080",
        caption = "Photo by Johannes Plenio on Unsplash"
    )
)
// ...

carousel.addData(list)
```

```java
// Java
ImageCarousel carousel = findViewById(R.id.carousel);

List<CarouselItem> list = new ArrayList<>();

list.add(
    new CarouselItem(
        imageUrl = "https://images.unsplash.com/photo-1532581291347-9c39cf10a73c?w=1080",
        caption = "Photo by Aaron Wu on Unsplash"
    )
)
list.add(
    new CarouselItem(
        imageUrl = "https://images.unsplash.com/photo-1534447677768-be436bb09401?w=1080",
        caption = "Photo by Johannes Plenio on Unsplash"
    )
)
// ...

carousel.addData(list);
```

That's all you need to use the library! :)

Detail examples can be found [here](https://github.com/ImaginativeShohag/Why-Not-Image-Carousel/tree/master/sample).

## `ImageCarousel` XML attributes

All the custom XML attributes for `ImageCarousel` view with default values are given below. All attributes are optional.

```
<org.imaginativeworld.whynotimagecarousel.ImageCarousel
    android:id="@+id/carousel"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    
    app:showTopShadow="true"
    app:topShadowAlpha="0.6"
    app:topShadowHeight="32dp"
    app:showBottomShadow="true"
    app:bottomShadowAlpha="0.6"
    app:bottomShadowHeight="64dp"
    app:showCaption="true"
    app:captionMargin="0dp"
    app:captionTextSize="14sp"
    app:showIndicator="true"
    app:indicatorMargin="0dp"
    app:showNavigationButtons="true"
    app:imageScaleType="fitCenter"
    app:carouselBackground="#333333"
    app:imagePlaceholder="@drawable/ic_picture"
    app:itemLayout="@layout/item_carousel"
    app:imageViewId="@id/img"
    app:previousButtonLayout="@layout/previous_button_layout"
    app:previousButtonId="@id/btn_previous"
    app:previousButtonMargin="4dp"
    app:nextButtonLayout="@layout/next_button_layout"
    app:nextButtonId="@id/btn_next"
    app:nextButtonMargin="4dp"
    app:carouselType="BLOCK"
    app:scaleOnScroll="false"
    app:scalingFactor="0.15"
    app:autoWidthFixing="true"
    app:autoPlay="false"
    app:autoPlayDelay="3000" />
```

## `ImageCarousel` functions

You can also set all the attributes programmatically. All the functions and their usages given below.

### Kotlin

```kotlin
val carousel: ImageCarousel = findViewById(R.id.carousel)

// Attributes
carousel.showTopShadow = true
carousel.topShadowAlpha = 0.6f // 0 to 1, 1 means 100%
carousel.topShadowHeight = 32.dpToPx(context) // px value of dp

carousel.showBottomShadow = true
carousel.bottomShadowAlpha = 0.6f // 0 to 1, 1 means 100%
carousel.bottomShadowHeight = 64.dpToPx(context) // px value of dp

carousel.showCaption = true
carousel.captionMargin = 0.dpToPx(context) // px value of dp
carousel.captionTextSize = 14.spToPx(context) // px value of sp

carousel.showIndicator = true
carousel.indicatorMargin = 0.dpToPx(context) // px value of dp

carousel.showNavigationButtons = true
carousel.imageScaleType = ImageView.ScaleType.CENTER_CROP
carousel.carouselBackground = ColorDrawable(Color.parseColor("#333333"))
carousel.imagePlaceholder = ContextCompat.getDrawable(
    context,
    R.drawable.ic_picture
)

// For custom item layout, the layout must be consist of an ImageView.
// Set the layout using "itemLayout" attribute and specify the ImageView id
// in the "imageViewId" attribute.
carousel.itemLayout = R.layout.item_carousel
carousel.imageViewId = R.id.img

// For custom previous or next button layout,
// set the layout using "previousButtonLayout" attribute and
// give the View/Button id in "previousButtonId" attribute.
carousel.previousButtonLayout = R.layout.previous_button_layout
carousel.previousButtonId = R.id.btn_previous
carousel.previousButtonMargin = 4.dpToPx(context) // px value of dp

carousel.nextButtonLayout = R.layout.next_button_layout
carousel.nextButtonId = R.id.btn_next
carousel.nextButtonMargin = 4.dpToPx(context) // px value of dp

carousel.carouselType = CarouselType.BLOCK
carousel.scaleOnScroll = false
carousel.scalingFactor = .15f // 0 to 1; 1 means 100

// If the width of a single item in ImageCarousel is not greater then
// half of the whole ImageCarousel view width, then the ImageCarousel
// will not work as expected, So it is recommended to set this value
// true all the time. So, the carousel will automatically increase the
// width of the items if necessary.
carousel.autoWidthFixing = true

// If you want auto slide, turn this feature on.
carousel.autoPlay = false
carousel.autoPlayDelay = 3000 // Milliseconds

// Scroll listener
carousel.onScrollListener = object : CarouselOnScrollListener {
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

// Item click listener
carousel.onItemClickListener = object : OnItemClickListener {
    override fun onClick(position: Int, carouselItem: CarouselItem) {
        // ...
    }

    override fun onLongClick(position: Int, dataObject: CarouselItem) {
        // ...
    }

}

// Goto next slide/item
carousel.next()

// Goto previous slide/item
carousel.previous()

// Start auto play
carousel.start()

// Stop auto play
carousel.stop()

// If you need custom indicator, use the CircleIndicator2 from CircleIndicator (https://github.com/ongakuer/CircleIndicator).
// Then pass the view to the ImageCarousel.
val customIndicator: CircleIndicator2 = findViewById(R.id.custom_indicator)
carousel.setIndicator(customIndicator)

// ...

carousel.addData(list)
```

### Java

```java
ImageCarousel carousel = findViewById(R.id.carousel);

// Attributes
carousel.setShowTopShadow(true);
carousel.setTopShadowAlpha(0.6f); // 0 to 1, 1 means 100%
carousel.setTopShadowHeight(Utils.dpToPx(32, context)); // px value of dp

carousel.setShowBottomShadow(true);
carousel.setBottomShadowAlpha(0.6f); // 0 to 1, 1 means 100%
carousel.setBottomShadowHeight(Utils.dpToPx(64, context)); // px value of dp

carousel.setShowCaption(true);
carousel.setCaptionMargin(Utils.dpToPx(0, context)); // px value of dp
carousel.setCaptionTextSize(Utils.spToPx(14, context)); // px value of sp

carousel.setShowIndicator(true);
carousel.setIndicatorMargin(Utils.dpToPx(0, context)); // px value of dp

carousel.setShowNavigationButtons(true);
carousel.setImageScaleType(ImageView.ScaleType.CENTER);
carousel.setCarouselBackground(new ColorDrawable(Color.parseColor("#333333")));
carousel.setImagePlaceholder(ContextCompat.getDrawable(
        context,
        R.drawable.ic_picture
));

// See kotlin code for details.
carousel.setItemLayout(R.layout.item_carousel);
carousel.setImageViewId(R.id.img);

// See kotlin code for details.
carousel.setPreviousButtonLayout(R.layout.previous_button_layout);
carousel.setPreviousButtonId(R.id.btn_previous);
carousel.setPreviousButtonMargin(Utils.dpToPx(4, context)); // px value of dp

carousel.setNextButtonLayout(R.layout.next_button_layout);
carousel.setNextButtonId(R.id.btn_next);
carousel.setNextButtonMargin(Utils.dpToPx(4, context)); // px value of dp

carousel.setCarouselType(CarouselType.BLOCK);
carousel.setScaleOnScroll(false);
carousel.setScalingFactor(.15f);

// See kotlin code for details.
carousel.setAutoWidthFixing(true);

// See kotlin code for details.
carousel.setAutoPlay(false);
carousel.setAutoPlayDelay(3000); // Milliseconds

// Scroll listener
carousel.setOnScrollListener(new CarouselOnScrollListener() {
    @Override
    public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
        // ...
    }

    @Override
    public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState, int position, @Nullable CarouselItem carouselItem) {
        // ...
    }
});

// Item click listener
carousel.setOnItemClickListener(new OnItemClickListener() {
    @Override
    public void onClick(int position, @NotNull CarouselItem carouselItem) {
        // ...
    }

    @Override
    public void onLongClick(int position, @NotNull CarouselItem dataObject) {
        // ...
    }
});

// Goto next slide/item
carousel.next()

// Goto previous slide/item
carousel.previous()

// Start auto play
carousel.start()

// Stop auto play
carousel.stop()

// See kotlin code for details.
CircleIndicator2 indicator = findViewById(R.id.custom_indicator);
carousel.setIndicator(indicator);

// ...

carousel.addData(list)
```

## Carousel Type

![Carousel type](/images/screenshot_carousel_type.png)

`ImageCarousel` has following types:

#### 1. `CarouselType.BLOCK`

If you need one item view at a time, then use this carousel type.

#### 2. `CarouselType.SHOWCASE`

If you need multiple item view at a time, use this carousel type.

You can also use the `scaleOnScroll` and `scalingFactor` attributes with this carousel type.

## Credits

This library is using the [CircleIndicator](https://github.com/ongakuer/CircleIndicator) library for the indicator.
Inspired by [CarouselView](https://github.com/jama5262/CarouselView) library.

## Changelog

### 1.0.0, 1.0.1, 1.0.2

The initial release of the library.

## License

```
Copyright 2020 Md. Mahmudul Hasan Shohag

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
