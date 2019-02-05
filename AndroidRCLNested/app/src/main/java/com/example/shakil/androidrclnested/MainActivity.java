package com.example.shakil.androidrclnested;

import android.support.annotation.NonNull;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.example.shakil.androidrclnested.Adapter.MyAdapter;
import com.example.shakil.androidrclnested.Model.Model;

import java.util.ArrayList;
import java.util.List;

import edmt.dev.advancednestedscrollview.AdvancedNestedScrollView;
import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView;

public class MainActivity extends AppCompatActivity {

    private boolean isShowingCardHeaderShadow;
    List<Model> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        generateModelList();

        final MaxHeightRecyclerView rv = findViewById(R.id.card_recycler_view);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        rv.setLayoutManager(lm);
        rv.setAdapter(new MyAdapter(this, modelList));
        rv.addItemDecoration(new DividerItemDecoration(this, lm.getOrientation()));

        final View cardHeaderShadow = findViewById(R.id.card_header_shadow);
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                boolean isRecyclerViewScrolledToTop = lm.findFirstVisibleItemPosition() == 0
                        && lm.findViewByPosition(0).getTop() == 0;
                if (!isRecyclerViewScrolledToTop && !isShowingCardHeaderShadow) {
                    isShowingCardHeaderShadow = true;
                    showOrhideView(cardHeaderShadow, isShowingCardHeaderShadow);
                } else {
                    isShowingCardHeaderShadow = false;
                    showOrhideView(cardHeaderShadow, isShowingCardHeaderShadow);
                }
            }
        });

        AdvancedNestedScrollView advancedNestedScrollView = findViewById(R.id.nested_scroll_view);
        advancedNestedScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        advancedNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView nestedScrollView, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0 && oldScrollY > 0) {
                    rv.scrollToPosition(0);
                    cardHeaderShadow.setAlpha(0f);
                    isShowingCardHeaderShadow = false;
                }
            }
        });
    }

    private void showOrhideView(View view, boolean isShow) {
        view.animate().alpha(isShow ? 1f : 0f).setDuration(100).setInterpolator(new DecelerateInterpolator());
    }

    private void generateModelList() {
        modelList.add(new Model("https://www.tripinsiders.net/wp-content/uploads/2016/12/sydnayxmas-768x512.jpg", "Sydney, Australia"));

        modelList.add(new Model("https://www.tripinsiders.net/wp-content/uploads/2016/12/washingtonxmas-768x432.jpg", "National Christmas Tree"));

        modelList.add(new Model("https://www.tripinsiders.net/wp-content/uploads/2016/12/lituaniaxmas-768x432.jpg", "Vilnius"));

        modelList.add(new Model("https://www.tripinsiders.net/wp-content/uploads/2016/12/polandxmas-768x432.jpg", "Warsaw, Poland"));
    }
}
