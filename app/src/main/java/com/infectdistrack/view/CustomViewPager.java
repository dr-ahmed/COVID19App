package com.infectdistrack.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {

    private boolean pagingEnabled;
    private Covid19FormActivity parentActivity;

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setParentActivity(Covid19FormActivity parentActivity) {
        this.parentActivity = parentActivity;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (pagingEnabled)
            return super.onTouchEvent(event);

        return pagingEnabled;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {

        switch (getCurrentItem()) {
            case 0: {
                pagingEnabled = parentActivity.isPart1FormDone();
                if (pagingEnabled) {
                    parentActivity.getCovid19FormPart1().setValues();
                    return super.onInterceptTouchEvent(event);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    parentActivity.getCovid19FormPart1().IsCheckboxChecked();
                    return pagingEnabled;
                }
            }
            break;
            case 1: {
                pagingEnabled = parentActivity.isPart2FormDone();
                if (pagingEnabled) {
                    parentActivity.getCovid19FormPart2().setValues();
                    return super.onInterceptTouchEvent(event);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    parentActivity.getCovid19FormPart2().isFieldEmpty();
                    return pagingEnabled;
                }
            }
            break;
            case 2: {
                pagingEnabled = parentActivity.isPart3FormDone();
                if (pagingEnabled) {
                    parentActivity.getCovid19FormPart3().setValues();
                    return super.onInterceptTouchEvent(event);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    parentActivity.getCovid19FormPart3().IsCheckboxChecked();
                    return pagingEnabled;
                }
            }
            break;
            case 3:
                return super.onInterceptTouchEvent(event);
            default: {
            }
        }

        return pagingEnabled;
    }

    public void setPagingEnabled(boolean pagingEnabled) {
        this.pagingEnabled = pagingEnabled;
    }
}