package org.imaginativeworld.whynotimagecarousel.sample

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf.allOf
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTestForTestActivity {
    @get:Rule
    val scenario = ActivityScenarioRule(TestActivity::class.java)

    @Before
    fun init() {
        onView(withId(R.id.btn_load_data)).perform(click())
    }

    @Test
    fun testInitialCaptions() {
        val expectedCaption = "Image 1 of 7"
        checkCaption(R.id.carousel1, expectedCaption)
        checkCaption(R.id.carousel2, expectedCaption)
        checkCaption(R.id.carousel3, expectedCaption)
        checkCaption(R.id.carousel4, expectedCaption)
    }

    @Test
    fun testPositionButtons() {
        checkPositionX(R.id.btn_1, 1)
        checkPositionX(R.id.btn_2, 2)
        checkPositionX(R.id.btn_3, 3)
        checkPositionX(R.id.btn_4, 4)
        checkPositionX(R.id.btn_5, 5)
        checkPositionX(R.id.btn_6, 6)
        checkPositionX(R.id.btn_7, 7)
    }

    @Test
    fun testEmbeddedPreviousAndNextButtons() {
        // Reset Carousel
        onView(withId(R.id.btn_1)).perform(click())

        // Click Next Button
        clickCarouselView(R.id.carousel1, R.id.btn_next)
        clickCarouselView(R.id.carousel2, R.id.btn_next)
        clickCarouselView(R.id.carousel3, R.id.btn_next)
        clickCarouselView(R.id.carousel4, R.id.btn_next)

        var expectedPosition = 1
        checkAdapterPosition(R.id.carousel1, expectedPosition)
        checkAdapterPosition(R.id.carousel2, expectedPosition)
        checkAdapterPosition(R.id.carousel3, expectedPosition)
        checkAdapterPosition(R.id.carousel4, expectedPosition)

        // Click Previous Button
        clickCarouselView(R.id.carousel1, R.id.btn_previous)
        clickCarouselView(R.id.carousel2, R.id.btn_previous)
        clickCarouselView(R.id.carousel3, R.id.btn_previous)
        clickCarouselView(R.id.carousel4, R.id.btn_previous)

        expectedPosition = 0
        checkAdapterPosition(R.id.carousel1, expectedPosition)
        checkAdapterPosition(R.id.carousel2, expectedPosition)
        checkAdapterPosition(R.id.carousel3, expectedPosition)
        checkAdapterPosition(R.id.carousel4, expectedPosition)
    }

    @Test
    fun testFiniteCarousel() {
        // Click Clear
        onView(withId(R.id.btn_clear_data)).perform(click())

        // Set carousel type finite
        onView(withId(R.id.btn_change_to_finite)).perform(click())

        // Click Load
        onView(withId(R.id.btn_load_data)).perform(click())

        // Click Previous Button
        clickCarouselView(R.id.carousel1, R.id.btn_previous)
        clickCarouselView(R.id.carousel2, R.id.btn_previous)
        clickCarouselView(R.id.carousel3, R.id.btn_previous)
        clickCarouselView(R.id.carousel4, R.id.btn_previous)

        var expectedPosition = 0
        checkAdapterPosition(R.id.carousel1, expectedPosition)
        checkAdapterPosition(R.id.carousel2, expectedPosition)
        checkAdapterPosition(R.id.carousel3, expectedPosition)
        checkAdapterPosition(R.id.carousel4, expectedPosition)

        // Go to last position
        onView(withId(R.id.btn_7)).perform(click())

        // Click Next Button
        clickCarouselView(R.id.carousel1, R.id.btn_next)
        clickCarouselView(R.id.carousel2, R.id.btn_next)
        clickCarouselView(R.id.carousel3, R.id.btn_next)
        clickCarouselView(R.id.carousel4, R.id.btn_next)

        expectedPosition = 6
        checkAdapterPosition(R.id.carousel1, expectedPosition)
        checkAdapterPosition(R.id.carousel2, expectedPosition)
        checkAdapterPosition(R.id.carousel3, expectedPosition)
        checkAdapterPosition(R.id.carousel4, expectedPosition)
    }

    @Test
    fun testInfiniteCarousel() {
        // Click Clear
        onView(withId(R.id.btn_clear_data)).perform(click())

        // Set carousel type finite
        onView(withId(R.id.btn_change_to_infinite)).perform(click())

        // Click Load
        onView(withId(R.id.btn_load_data)).perform(click())

        // Click Previous Button
        clickCarouselView(R.id.carousel1, R.id.btn_previous)
        clickCarouselView(R.id.carousel2, R.id.btn_previous)
        clickCarouselView(R.id.carousel3, R.id.btn_previous)
        clickCarouselView(R.id.carousel4, R.id.btn_previous)

        var expectedPosition = 6
        checkAdapterPosition(R.id.carousel1, expectedPosition)
        checkAdapterPosition(R.id.carousel2, expectedPosition)
        checkAdapterPosition(R.id.carousel3, expectedPosition)
        checkAdapterPosition(R.id.carousel4, expectedPosition)

        // Go to last position
        onView(withId(R.id.btn_7)).perform(click())

        // Click Next Button
        clickCarouselView(R.id.carousel1, R.id.btn_next)
        clickCarouselView(R.id.carousel2, R.id.btn_next)
        clickCarouselView(R.id.carousel3, R.id.btn_next)
        clickCarouselView(R.id.carousel4, R.id.btn_next)

        expectedPosition = 0
        checkAdapterPosition(R.id.carousel1, expectedPosition)
        checkAdapterPosition(R.id.carousel2, expectedPosition)
        checkAdapterPosition(R.id.carousel3, expectedPosition)
        checkAdapterPosition(R.id.carousel4, expectedPosition)
    }

    @Test
    fun testCurrentPositionIncrementAndDecrement() {
        // Start from first position
        onView(withId(R.id.btn_1)).perform(click())

        // Click previous
        onView(withId(R.id.btn_previous_all)).perform(click())

        var expectedPosition = 0
        checkAdapterPosition(R.id.carousel1, expectedPosition)
        checkAdapterPosition(R.id.carousel2, expectedPosition)
        checkAdapterPosition(R.id.carousel3, expectedPosition)
        checkAdapterPosition(R.id.carousel4, expectedPosition)

        // Click next 7 times
        for (i in 1..7) {
            onView(withId(R.id.btn_next_all)).perform(click())
        }

        expectedPosition = 6
        checkAdapterPosition(R.id.carousel1, expectedPosition)
        checkAdapterPosition(R.id.carousel2, expectedPosition)
        checkAdapterPosition(R.id.carousel3, expectedPosition)
        checkAdapterPosition(R.id.carousel4, expectedPosition)
    }

    @Test
    fun testSingleAppend() {
        // Click Clear
        onView(withId(R.id.btn_clear_data)).perform(click())

        // Load single item
        for (i in 1..3) {
            onView(withId(R.id.btn_single_append)).perform(click())

            checkAdapterItemSize(R.id.carousel1, i)
            checkAdapterItemSize(R.id.carousel2, i)
            checkAdapterItemSize(R.id.carousel3, i)
            checkAdapterItemSize(R.id.carousel4, i)
        }
    }

    @Test
    fun testMultiAppend() {
        // Click Clear
        onView(withId(R.id.btn_clear_data)).perform(click())

        // Load single item
        for (i in 1..3) {
            onView(withId(R.id.btn_multi_append)).perform(click())

            val expectedItemSize = i * 3
            checkAdapterItemSize(R.id.carousel1, expectedItemSize)
            checkAdapterItemSize(R.id.carousel2, expectedItemSize)
            checkAdapterItemSize(R.id.carousel3, expectedItemSize)
            checkAdapterItemSize(R.id.carousel4, expectedItemSize)
        }
    }
}

// ====================================================================

/**
 * Use it for waiting.
 *
 * Example:
 * onView(isRoot()).perform(waitFor(5000))
 */
fun waitFor(delay: Long): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription(): String = "wait for $delay milliseconds"
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}

/**
 * Invoke click and wait for UI gets idle.
 */
fun forceClick(): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            return allOf(isClickable(), isEnabled())
        }

        override fun getDescription(): String {
            return "force click"
        }

        override fun perform(uiController: UiController?, view: View?) {
            view?.performClick()
            uiController?.loopMainThreadUntilIdle()
        }
    }
}

// ====================================================================

fun clickCarouselView(carouselId: Int, viewId: Int) {
    onView(allOf(withId(viewId), isDescendantOfA(withId(carouselId))))
        .perform(click())
}

// ====================================================================

fun checkPositionX(btnId: Int, x: Int) {
    onView(withId(btnId)).perform(click())

    // Check the caption
    val expectedCaption = "Image $x of 7"
    checkCaption(R.id.carousel1, expectedCaption)
    checkCaption(R.id.carousel2, expectedCaption)
    checkCaption(R.id.carousel3, expectedCaption)
    checkCaption(R.id.carousel4, expectedCaption)

    // Check adapter position
    val expectedPosition = x - 1
    checkAdapterPosition(R.id.carousel1, expectedPosition)
    checkAdapterPosition(R.id.carousel2, expectedPosition)
    checkAdapterPosition(R.id.carousel3, expectedPosition)
    checkAdapterPosition(R.id.carousel4, expectedPosition)
}

// ====================================================================

fun checkCaption(carouselId: Int, expectedCaption: String) {
    onView(allOf(withId(R.id.tv_caption), isDescendantOfA(withId(carouselId))))
        .check(matches(withText(expectedCaption)))
}

// ====================================================================

fun checkAdapterPosition(carouselId: Int, expectedPosition: Int) {
    val action = CarouselCurrentItemCheckAction()
    onView(withId(carouselId)).perform(action)
    val carouselCurrentPosition = action.returnValue
    assert(carouselCurrentPosition == expectedPosition)
}

class CarouselCurrentItemCheckAction : ViewAction {
    var returnValue: Int = -1

    override fun getConstraints(): Matcher<View> {
        return isAssignableFrom(ImageCarousel::class.java)
    }

    override fun getDescription(): String {
        return "Get current position of the Image Carousel."
    }

    override fun perform(uiController: UiController?, view: View?) {
        val carousel = view as ImageCarousel
        returnValue = carousel.currentPosition
    }
}

// ====================================================================

fun checkAdapterItemSize(carouselId: Int, expectedItemSize: Int) {
    val action = CarouselTotalItemCountAction()
    onView(withId(carouselId)).perform(action)
    val carouselCurrentItemSize = action.returnValue
    assert(carouselCurrentItemSize == expectedItemSize)
}

class CarouselTotalItemCountAction : ViewAction {
    var returnValue: Int = -1

    override fun getConstraints(): Matcher<View> {
        return isAssignableFrom(ImageCarousel::class.java)
    }

    override fun getDescription(): String {
        return "Get current item size of the Image Carousel."
    }

    override fun perform(uiController: UiController?, view: View?) {
        val carousel = view as ImageCarousel
        returnValue = carousel.getData()?.size ?: -1
    }
}
