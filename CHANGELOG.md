Change Log
==========

## Version 2.0.0 (In development)

* New: 
* Fix: 

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