package com.example.shakil.kotlinrclnested

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.DecelerateInterpolator
import com.example.shakil.kotlinrclnested.Adapter.MyAdapter
import com.example.shakil.kotlinrclnested.Model.MyModel
import edmt.dev.advancednestedscrollview.AdvancedNestedScrollView
import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private var isShowingCardHeaderShadow: Boolean = false
    internal lateinit var modelList: MutableList<MyModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        generateModelList()

        val lm = LinearLayoutManager(this)
        card_recycler_view.layoutManager = lm
        card_recycler_view.adapter = MyAdapter(this, modelList)
        card_recycler_view.addItemDecoration(DividerItemDecoration(this, lm.orientation))

        card_recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val isRecyclerViewScrolledToTop =
                    lm.findFirstVisibleItemPosition() == 0 && lm.findViewByPosition(0)!!.top == 0
                if (!isRecyclerViewScrolledToTop && !isShowingCardHeaderShadow) {
                    isShowingCardHeaderShadow = true
                    showOrHideView(card_header_shadow, true)
                } else if (isRecyclerViewScrolledToTop && isShowingCardHeaderShadow) {
                    isShowingCardHeaderShadow = false
                    showOrHideView(card_header_shadow, false)
                }
            }
        })


        nested_scroll_view.overScrollMode = View.OVER_SCROLL_NEVER
        nested_scroll_view.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { nestedScrollView, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (scrollY == 0 && oldScrollY > 0) {
                card_recycler_view.scrollToPosition(0)
                card_header_shadow.alpha = 0f
                isShowingCardHeaderShadow = false
            }
        })
    }

    private fun showOrHideView(view: View?, isShow: Boolean) {
        view!!.animate().alpha(if (isShow) 1f else 0f).setDuration(100).interpolator = DecelerateInterpolator()
    }

    private fun generateModelList() {
        modelList = ArrayList()
        modelList.add(
            MyModel(
                "https://www.tripinsiders.net/wp-content/uploads/2016/12/sydnayxmas-768x512.jpg",
                "Sydney, Australia"
            )
        )

        modelList.add(
            MyModel(
                "https://www.tripinsiders.net/wp-content/uploads/2016/12/st-petersburg-christmas-tree-768x576.jpg",
                "Palace, Square"
            )
        )

        modelList.add(
            MyModel(
                "https://www.tripinsiders.net/wp-content/uploads/2016/12/washingtonxmas-768x432.jpg",
                "National Christmas Tree"
            )
        )

        modelList.add(
            MyModel(
                "https://www.tripinsiders.net/wp-content/uploads/2016/12/lituaniaxmas-768x432.jpg",
                "Vilnius"
            )
        )

        modelList.add(
            MyModel(
                "https://www.tripinsiders.net/wp-content/uploads/2016/12/polandxmas-768x432.jpg",
                "Warsaw, Poland"
            )
        )
    }
}
