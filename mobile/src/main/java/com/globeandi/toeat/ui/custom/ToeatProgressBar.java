package com.globeandi.toeat.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


//TODO:: finish implementing this custom view
public class ToeatProgressBar extends View {

    //Called when it is created Programatically (new DualProgressView(this))
    public ToeatProgressBar(Context context) {
        super(context);
    }

    //Called when view in inflated via XML. param attrs which contain collection of your attributes you passed via XML
    public ToeatProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //this invoked manually to apply any default style you want to apply for your widget
    public ToeatProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    // if you are extending a View, you are responsible for drawing the View on the Screen
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    //onMeasure is the place you decide how much width and height is needed for your view
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }


}
