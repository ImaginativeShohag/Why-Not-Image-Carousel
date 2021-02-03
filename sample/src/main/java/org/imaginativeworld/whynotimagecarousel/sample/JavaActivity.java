package org.imaginativeworld.whynotimagecarousel.sample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.CarouselOnScrollListener;
import org.imaginativeworld.whynotimagecarousel.CarouselType;
import org.imaginativeworld.whynotimagecarousel.OnItemClickListener;
import org.imaginativeworld.whynotimagecarousel.Utils;
import org.imaginativeworld.whynotimagecarousel.sample.databinding.ActivityJavaBinding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

public class JavaActivity extends AppCompatActivity {

    private ActivityJavaBinding binding;

    private Context context;

    private boolean isStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJavaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        context = this;

        binding.carousel.setShowTopShadow(false);
        binding.carousel.setTopShadowAlpha(0.6f); // 0 to 1, 1 means 100%
        binding.carousel.setTopShadowHeight(Utils.dpToPx(32, context)); // px value of dp
        binding.carousel.setShowBottomShadow(false);
        binding.carousel.setBottomShadowAlpha(0.6f); // 0 to 1, 1 means 100%
        binding.carousel.setBottomShadowHeight(Utils.dpToPx(64, context)); // px value of dp
        binding.carousel.setShowCaption(true);
        binding.carousel.setCaptionMargin(Utils.dpToPx(16, context)); // px value of dp
        binding.carousel.setCaptionTextSize(Utils.spToPx(16, context)); // px value of sp
        binding.carousel.setShowIndicator(false);
        binding.carousel.setIndicatorMargin(Utils.dpToPx(0, context)); // px value of dp
        binding.carousel.setShowNavigationButtons(true);
        binding.carousel.setImageScaleType(ImageView.ScaleType.CENTER);
        binding.carousel.setCarouselBackground(new ColorDrawable(Color.parseColor("#333333")));
        binding.carousel.setImagePlaceholder(ContextCompat.getDrawable(
                this,
                R.drawable.ic_wb_cloudy_with_padding
        ));
        binding.carousel.setItemLayout(R.layout.custom_fixed_size_item_layout);
        binding.carousel.setImageViewId(R.id.image_view);
        binding.carousel.setPreviousButtonLayout(R.layout.custom_previous_button_layout);
        binding.carousel.setPreviousButtonId(R.id.custom_btn_previous);
        binding.carousel.setPreviousButtonMargin(Utils.dpToPx(8, context)); // px value of dp
        binding.carousel.setNextButtonLayout(R.layout.custom_next_button_layout);
        binding.carousel.setNextButtonId(R.id.custom_btn_next);
        binding.carousel.setNextButtonMargin(Utils.dpToPx(8, context)); // px value of dp
        binding.carousel.setCarouselType(CarouselType.SHOWCASE);
        binding.carousel.setScaleOnScroll(true);
        binding.carousel.setScalingFactor(.15f);
        binding.carousel.setAutoWidthFixing(true);
        binding.carousel.setAutoPlay(false);
        binding.carousel.setAutoPlayDelay(3000); // Milliseconds
        binding.carousel.setOnScrollListener(new CarouselOnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
                // ...
            }

            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState, int position, @Nullable CarouselItem carouselItem) {
                // ...
            }
        });
        binding.carousel.setOnItemClickListener(new OnItemClickListener() {
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
        binding.carousel.setIndicator(indicator);

        MaterialButton previousBtn = findViewById(R.id.btn_goto_previous);
        previousBtn.setOnClickListener(v -> binding.carousel.previous());

        MaterialButton nextBtn = findViewById(R.id.btn_goto_next);
        nextBtn.setOnClickListener(v -> binding.carousel.next());

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

        binding.carousel.addData(list);

        // ----------------------------------------------------------------

        binding.fabPlay.setOnClickListener(v -> {
            if (isStarted) {

                isStarted = false;
                binding.carousel.stop();

                binding.fabPlay.setText("Start");

            } else {

                isStarted = true;
                binding.carousel.start();

                binding.fabPlay.setText("Stop");

            }
        });

    }
}
