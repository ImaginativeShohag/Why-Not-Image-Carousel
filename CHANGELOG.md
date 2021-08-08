Change Log
==========

## Version 2.0.4 *(2021-08-08)* 🚀

* 🐞
  Fixed [#43](https://github.com/ImaginativeShohag/Why-Not-Image-Carousel/issues/43): `currentPosition`
  will now give real data position. For virtual data position in `infiniteCarousel`
  use `currentVirtualPosition`.
* 🐞 Fixed [#45](https://github.com/ImaginativeShohag/Why-Not-Image-Carousel/issues/45): `autoPlay`
  will now work after slide.

## Version 2.0.3 *(2021-07-25)*

* 🐞 Fixed: wrong `position` value in `onScrollStateChanged` listener.

## Version 2.0.2 *(2021-05-31)*

* 🆕 `registerLifecycle(lifecycleOwner: LifecycleOwner)` method added.
* 🛠️ Java sample converted into a `Fragment` example.

## Version 2.0.0, 2.0.1 *(2021-05-22)*

* 🆕 Previous boring custom layout system removed. And view-binding supported custom layout system added. See `CarouselListener` in the sample app for details. 🎉
* 🆕 The carousel is now supported Infinite ∞ looping (Infinite Carousel) 🥳! It's default now. You can disable it by setting `infiniteCarousel` to `false`.
* 🆕 Carousel now supports touch-to-pause auto-play. It is default now. You can disable it by setting `touchToPause` to `false`. 🎊
* 🆕 `carouselGravity` attribute added. So you can set carousel item view gravity `START` if you want.
* 🆕 Carousel padding attributes added. You can use `carouselPadding`, `carouselPaddingStart`, `carouselPaddingTop`, `carouselPaddingEnd`, and `carouselPaddingBottom` to set carousel padding.
* 🆕 The `ImageCarousel` is now a lifecycle component. You can register any lifecycle with `registerLifecycle()` method. It is recommended if you enable `autoPlay` and `infiniteCarousel`. So that when the application is in the pause state, the carousel will be paused and resumed on the app resume. It is also used to correctly initialize the infinite carousel when the app is in the background.
* 🆕 You can now use `setData()` to set `CarouselItem` list. And `addData()` to add single or multiple items to the carousel. If you set/add data only once, then you can continue using `addData()` method.
* 🆕 You can now get the carousel items using `getData()` method.
* 🆕 Sample app re-designed. Various use cases and inspiring 💡 examples are added. 😎
* ⚠️ `OnItemClickListener` renamed to `CarouselListener` and two new method (`onCreateViewHolder()` and `onBindViewHolder()`) added for the latest custom view feature. `setOnItemClickListener()` also renamed to `setCarouselListener()`.
* ⚠️ `CarouselListener` (previously `OnItemClickListener`) package changed from `org.imaginativeworld.whynotimagecarousel` to `org.imaginativeworld.whynotimagecarousel.listener`.
* ⚠️ `CarouselItem` package changed from `org.imaginativeworld.whynotimagecarousel` to `org.imaginativeworld.whynotimagecarousel.model`.
* 🛠️ New parameters `position` and `carouselItem` add to `onScrolled` method in  `CarouselOnScrollListener`.
* 🛠️ The default carousel background changed to transparent. You can always change the background of the carousel using `carouselBackground`. The previous value was `#333333`.
* 🛠️ Left and right navigation default button icon replaced with a rounded chevron icon.

## Version 1.3.0 *(2021-03-25)*

Header field is added to the `CarouselItem`.

Examples:

```kotlin
// Kotlin
val headers = mutableMapOf<String, String>()
headers["header_key"] = "header_value"

val carouselItem1 = CarouselItem(
    imageUrl = "https://images.unsplash.com/photo-1549577434-d7615fd4ceac?w=1080",
    caption = "Photo by Jeremy Bishop on Unsplash",
    headers = headers
)
val carouselItem2 = CarouselItem(
    imageUrl = "https://images.unsplash.com/photo-1549577434-d7615fd4ceac?w=1080",
    headers = headers
)
```

```java
// Java
Map<String, String> headers = new HashMap<>();
headers.put("header_key", "header_value");

CarouselItem carouselItem1 = new CarouselItem(
        "https://images.unsplash.com/photo-1549577434-d7615fd4ceac?w=1080",
        "Photo by Jeremy Bishop on Unsplash",
        headers
);

CarouselItem carouselItem2 = new CarouselItem(
        "https://images.unsplash.com/photo-1549577434-d7615fd4ceac?w=1080",
        headers
);
```

## Version 1.2.1 *(2021-02-04)*

We move our library from **jitpack.io** to **maven** repository. So no need to add any repositories for using the library.

View binding added to the sample. Dependent libraries updated.

## Version 1.1.0 *(2020-06-03)*

Image drawable support added.

## Version 1.0.0, 1.0.1, 1.0.2 *(2020-03-12)*

The initial release of the library.