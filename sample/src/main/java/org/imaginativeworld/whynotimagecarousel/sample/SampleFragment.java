package org.imaginativeworld.whynotimagecarousel.sample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.google.android.material.button.MaterialButton;

import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener;
import org.imaginativeworld.whynotimagecarousel.listener.CarouselOnScrollListener;
import org.imaginativeworld.whynotimagecarousel.model.CarouselGravity;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.model.CarouselType;
import org.imaginativeworld.whynotimagecarousel.sample.databinding.FragmentSampleBinding;
import org.imaginativeworld.whynotimagecarousel.utils.Utils;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator2;

public class SampleFragment extends Fragment {

    private FragmentSampleBinding binding;

    private Context context;

    private boolean isStarted = false;

    public SampleFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSampleBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        context = requireContext();

        binding.carousel.registerLifecycle(getViewLifecycleOwner());

        binding.carousel.setShowTopShadow(false);
        binding.carousel.setTopShadowAlpha(0.6f); // 0 to 1, 1 means 100%
        binding.carousel.setTopShadowHeight(Utils.dpToPx(32, context)); // px value of dp

        binding.carousel.setShowBottomShadow(true);
        binding.carousel.setBottomShadowAlpha(0.7f); // 0 to 1, 1 means 100%
        binding.carousel.setBottomShadowHeight(Utils.dpToPx(48, context)); // px value of dp

        binding.carousel.setShowCaption(true);
        binding.carousel.setCaptionMargin(Utils.dpToPx(8, context)); // px value of dp
        binding.carousel.setCaptionTextSize(Utils.spToPx(16, context)); // px value of sp

        binding.carousel.setShowIndicator(false);
        binding.carousel.setIndicatorMargin(Utils.dpToPx(0, context)); // px value of dp

        binding.carousel.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

        binding.carousel.setCarouselBackground(new ColorDrawable(Color.parseColor("#333333")));
        binding.carousel.setImagePlaceholder(ContextCompat.getDrawable(
                context,
                R.drawable.ic_wb_cloudy_with_padding
        ));

        binding.carousel.setCarouselPadding(Utils.dpToPx(0, context));
        binding.carousel.setCarouselPaddingStart(Utils.dpToPx(0, context));
        binding.carousel.setCarouselPaddingTop(Utils.dpToPx(0, context));
        binding.carousel.setCarouselPaddingEnd(Utils.dpToPx(0, context));
        binding.carousel.setCarouselPaddingBottom(Utils.dpToPx(0, context));

        binding.carousel.setShowNavigationButtons(false);
        binding.carousel.setPreviousButtonLayout(R.layout.custom_previous_button_layout);
        binding.carousel.setPreviousButtonId(R.id.custom_btn_previous);
        binding.carousel.setPreviousButtonMargin(Utils.dpToPx(8, context)); // px value of dp
        binding.carousel.setNextButtonLayout(R.layout.custom_next_button_layout);
        binding.carousel.setNextButtonId(R.id.custom_btn_next);
        binding.carousel.setNextButtonMargin(Utils.dpToPx(8, context)); // px value of dp

        binding.carousel.setCarouselType(CarouselType.SHOWCASE);

        binding.carousel.setCarouselGravity(CarouselGravity.CENTER);

        binding.carousel.setScaleOnScroll(false);
        binding.carousel.setScalingFactor(.15f);
        binding.carousel.setAutoWidthFixing(true);
        binding.carousel.setAutoPlay(false);
        binding.carousel.setAutoPlayDelay(3000); // Milliseconds
        binding.carousel.setInfiniteCarousel(true);
        binding.carousel.setTouchToPause(true);

        binding.carousel.setOnScrollListener(new CarouselOnScrollListener() {
            @Override
            public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy, int position, @org.jetbrains.annotations.Nullable CarouselItem carouselItem) {
                // ...
            }

            @Override
            public void onScrollStateChanged(@NotNull RecyclerView recyclerView, int newState, int position, @org.jetbrains.annotations.Nullable CarouselItem carouselItem) {
                // ...
            }
        });

        binding.carousel.setCarouselListener(new CarouselListener() {
            @org.jetbrains.annotations.Nullable
            @Override
            public ViewBinding onCreateViewHolder(@NotNull LayoutInflater layoutInflater, @NotNull ViewGroup parent) {
                // ...
                return null;
            }

            @Override
            public void onBindViewHolder(@NotNull ViewBinding binding, @NotNull CarouselItem item, int position) {
                // ...
            }

            @Override
            public void onLongClick(int position, @NotNull CarouselItem carouselItem) {
                // ...
            }

            @Override
            public void onClick(int position, @NotNull CarouselItem carouselItem) {
                // ...
            }
        });

        CircleIndicator2 indicator = binding.customIndicator;
        binding.carousel.setIndicator(indicator);

        MaterialButton previousBtn = binding.btnGotoPrevious;
        previousBtn.setOnClickListener(v -> binding.carousel.previous());

        MaterialButton nextBtn = binding.btnGotoNext;
        nextBtn.setOnClickListener(v -> binding.carousel.next());

        List<CarouselItem> list = new ArrayList<>();

        // Dummy header
        Map<String, String> headers = new HashMap<>();
        headers.put("header_key", "header_value");

        int index = 1;
        for (String item : DataSet.INSTANCE.getOne()) {
            list.add(
                    new CarouselItem(
                            item,
                            "Image " + index++ + " of " + DataSet.INSTANCE.getOne().size(),
                            headers
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