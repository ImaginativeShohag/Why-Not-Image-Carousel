package org.imaginativeworld.whynotimagecarousel.sample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.CarouselOnScrollListener;
import org.imaginativeworld.whynotimagecarousel.CarouselType;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener;
import org.imaginativeworld.whynotimagecarousel.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

public class JavaActivity extends AppCompatActivity {

    private Context context;

    private ExtendedFloatingActionButton fab;

    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java);

        context = this;

        fab = findViewById(R.id.btn_play);

        ImageCarousel carousel = findViewById(R.id.carousel);

        carousel.setShowTopShadow(false);
        carousel.setTopShadowAlpha(0.6f); // 0 to 1, 1 means 100%
        carousel.setTopShadowHeight(Utils.dpToPx(32, context)); // px value of dp
        carousel.setShowBottomShadow(false);
        carousel.setBottomShadowAlpha(0.6f); // 0 to 1, 1 means 100%
        carousel.setBottomShadowHeight(Utils.dpToPx(64, context)); // px value of dp
        carousel.setShowCaption(true);
        carousel.setCaptionMargin(Utils.dpToPx(16, context)); // px value of dp
        carousel.setCaptionTextSize(Utils.spToPx(16, context)); // px value of sp
        carousel.setShowIndicator(false);
        carousel.setIndicatorMargin(Utils.dpToPx(0, context)); // px value of dp
        carousel.setShowNavigationButtons(true);
        carousel.setImageScaleType(ImageView.ScaleType.CENTER);
        carousel.setCarouselBackground(new ColorDrawable(Color.parseColor("#333333")));
        carousel.setImagePlaceholder(ContextCompat.getDrawable(
                this,
                R.drawable.ic_wb_cloudy_with_padding
        ));
        carousel.setItemLayout(R.layout.custom_fixed_size_item_layout);
        carousel.setImageViewId(R.id.image_view);
        carousel.setPreviousButtonLayout(R.layout.custom_previous_button_layout);
        carousel.setPreviousButtonId(R.id.custom_btn_previous);
        carousel.setPreviousButtonMargin(Utils.dpToPx(8, context)); // px value of dp
        carousel.setNextButtonLayout(R.layout.custom_next_button_layout);
        carousel.setNextButtonId(R.id.custom_btn_next);
        carousel.setNextButtonMargin(Utils.dpToPx(8, context)); // px value of dp
        carousel.setCarouselType(CarouselType.SHOWCASE);
        carousel.setScaleOnScroll(true);
        carousel.setScalingFactor(.15f);
        carousel.setAutoWidthFixing(true);
        carousel.setAutoPlay(false);
        carousel.setAutoPlayDelay(3000); // Milliseconds
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

        CircleIndicator2 indicator = findViewById(R.id.custom_indicator);
        carousel.setIndicator(indicator);

        MaterialButton previousBtn = findViewById(R.id.btn_goto_previous);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carousel.previous();
            }
        });

        MaterialButton nextBtn = findViewById(R.id.btn_goto_next);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carousel.next();
            }
        });

        List<CarouselItem> list = new ArrayList<>();

        int max = 10;

        for (int i = 1; i <= max; i++) {
            if (i % 2 == 0) {
                list.add(
                        new CarouselItem(
                                "https://images.unsplash.com/photo-1581357825340-32259110788a?w=1080",
                                "Image " + i + " of " + max
                        )
                );
            } else {
                list.add(
                        new CarouselItem(
                                "https://images.unsplash.com/photo-1581441117193-63e8f6547081?w=1080",
                                "Image " + i + " of " + max
                        )
                );
            }
        }

        carousel.addData(list);

        // ----------------------------------------------------------------

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStarted) {

                    isStarted = false;
                    carousel.stop();

                    fab.setText("Start");

                } else {

                    isStarted = true;
                    carousel.start();

                    fab.setText("Stop");

                }
            }
        });

    }
}
