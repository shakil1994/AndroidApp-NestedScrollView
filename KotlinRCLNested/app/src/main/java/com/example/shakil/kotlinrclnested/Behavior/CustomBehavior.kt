package com.example.shakil.kotlinrclnested.Behavior

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.support.v4.view.ViewCompat
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.shakil.kotlinrclnested.R
import edmt.dev.advancednestedscrollview.MaxHeightRecyclerView
import edmt.dev.advancednestedscrollview.MyViewGroupUtils

class CustomBehavior(context: Context, attrs: AttributeSet): CoordinatorLayout.Behavior<NestedScrollView>(context, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: NestedScrollView, dependency: View): Boolean {
        return dependency.id == R.id.toolbar_container
    }

    override fun onLayoutChild(parent: CoordinatorLayout, child: NestedScrollView, layoutDirection: Int): Boolean {
        parent.onLayoutChild(child, layoutDirection)

        val fabHalfHeight = child.findViewById<View>(R.id.fab).height / 2
        setTopMargin(child.findViewById<CardView>(R.id.card_view), fabHalfHeight)

        val rvMaxHeight = (child.height - fabHalfHeight - child.findViewById<View>(R.id.card_title).height
                - child.findViewById<View>(R.id.card_sub_title).height)
        val rv = child.findViewById<MaxHeightRecyclerView>(R.id.card_recycler_view)
        rv.setMaxHeight(rvMaxHeight)

        val cardContainer = child.findViewById<View>(R.id.card_container)
        val toolbarContainerHeight = parent.getDependencies(child)[0].height
        setPaddingTop(cardContainer, rvMaxHeight - toolbarContainerHeight)
        ViewCompat.offsetTopAndBottom(child, toolbarContainerHeight)
        setPaddingBottom(rv, toolbarContainerHeight)
        return true
    }

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: NestedScrollView, ev: MotionEvent): Boolean {
        return ev.actionMasked == MotionEvent.ACTION_DOWN &&
                isTouchInChildBounds(parent, child, ev) &&
                !isTouchInChildBounds(parent, child.findViewById(R.id.card_view), ev) &&
                !isTouchInChildBounds(parent, child.findViewById(R.id.fab), ev)
    }

    private fun isTouchInChildBounds(parent: ViewGroup, child: View, ev: MotionEvent): Boolean {
        return MyViewGroupUtils.isPointInChildBounds(parent, child, ev.x.toInt(), ev.y.toInt())
    }

    private fun setPaddingBottom(view: View?, bottom: Int) {
        if (view!!.paddingBottom != bottom) {
            view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, bottom)
        }
    }

    private fun setPaddingTop(view: View?, top: Int) {
        if (view!!.paddingTop != top) {
            view.setPadding(view.paddingLeft, top, view.paddingRight, view.paddingBottom)
        }
    }

    private fun setTopMargin(view: View?, top: Int) {
        val lp = view!!.layoutParams as ViewGroup.MarginLayoutParams
        if (lp.topMargin != top) {
            lp.topMargin = top
            view.layoutParams = lp
        }
    }
}