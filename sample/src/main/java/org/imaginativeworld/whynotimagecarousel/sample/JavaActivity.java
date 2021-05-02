package org.imaginativeworld.whynotimagecarousel.sample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.button.MaterialButton;

import org.imaginativeworld.whynotimagecarousel.Utils;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselOnScrollListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselGravity;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.model.CarouselType;
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

        binding.carousel.registerLifecycle(getLifecycle());

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

        binding.carousel.setImageScaleType(ImageView.ScaleType.CENTER);
        binding.carousel.setCarouselBackground(new ColorDrawable(Color.parseColor("#333333")));
        binding.carousel.setImagePlaceholder(ContextCompat.getDrawable(
                this,
                R.drawable.ic_wb_cloudy_with_padding
        ));

        binding.carousel.setPreviousButtonLayout(R.layout.custom_previous_button_layout);
        binding.carousel.setPreviousButtonId(R.id.custom_btn_previous);
        binding.carousel.setPreviousButtonMargin(Utils.dpToPx(8, context)); // px value of dp
        binding.carousel.setNextButtonLayout(R.layout.custom_next_button_layout);
        binding.carousel.setNextButtonId(R.id.custom_btn_next);
        binding.carousel.setNextButtonMargin(Utils.dpToPx(8, context)); // px value of dp
        binding.carousel.setShowNavigationButtons(false);

        binding.carousel.setCarouselGravity(CarouselGravity.CENTER);
        binding.carousel.setCarouselType(CarouselType.SHOWCASE);
        binding.carousel.setScaleOnScroll(false);
        binding.carousel.setScalingFactor(.15f);
        binding.carousel.setAutoWidthFixing(true);
        binding.carousel.setAutoPlay(false);
        binding.carousel.setAutoPlayDelay(3000); // Milliseconds
        binding.carousel.setInfiniteCarousel(true);
        binding.carousel.setTouchToPause(true);
        binding.carousel.setCarouselPaddingStart(Utils.dpToPx(0, context));
        binding.carousel.setCarouselPaddingEnd(Utils.dpToPx(0, context));

        binding.carousel.setOnScrollListener(new CarouselOnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy, int position, @Nullable CarouselItem carouselItem) {
                // ...
            }

            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState, int position, @Nullable CarouselItem carouselItem) {
                // ...
            }
        });
        binding.carousel.setCarouselListener(new CarouselListener() {
            @Override
            public void onBindViewHolder(@NotNull ViewBinding binding, @NotNull ImageView.ScaleType imageScaleType, @NotNull CarouselItem item, int position) {
                // ...
            }

            @Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NotNull LayoutInflater layoutInflater, @NotNull ViewGroup parent) {
                // ...
                return null;
            }
        });

        CircleIndicator2 indicator = findViewById(R.id.custom_indicator);
        binding.carousel.setIndicator(indicator);

        MaterialButton previousBtn = findViewById(R.id.btn_goto_previous);
        previousBtn.setOnClickListener(v -> binding.carousel.previous());

        MaterialButton nextBtn = findViewById(R.id.btn_goto_next);
        nextBtn.setOnClickListener(v -> binding.carousel.next());

        List<CarouselItem> list = new ArrayList<>();

        int index = 1;
        for (String item : DataSet.INSTANCE.getOne()) {
            list.add(
                    new CarouselItem(
                            item,
                            "Image " + index++ + " of " + DataSet.INSTANCE.getOne().size()
                    )
            );
        }

        binding.carousel.setData(list);

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
