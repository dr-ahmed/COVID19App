package com.infectdistrack.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;

public class CustomViewPager extends ViewPager {

    private boolean pagingEnabled;
    private Covid19FormActivity parentActivity;
    private final String NOTICE = "Champ obligatoire manquant !";

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // Lorsque le user swiipe du dernier fragment vers celui qui le precede, le resetErrorLabel permet de
                // faire disparaitre le message d'erreur affiché sur ce fragment
                parentActivity.resetErrorLabel();
                switch (position) {
                    case 0:
                        parentActivity.setFragmentCurrentLabel("Diagnostic clinique");
                        break;
                    case 1:
                        parentActivity.setFragmentCurrentLabel("Facteurs associés");
                        // Mettre à jour la visibilité du layout des symptomes
                        parentActivity.getCovid19FormPart2().setSymptomsLayoutVisibility();
                        break;
                    case 2:
                        parentActivity.setFragmentCurrentLabel("Dépistage ou contrôle");
                        break;
                    case 3:
                        parentActivity.setFragmentCurrentLabel("Évolution");
                        break;
                    default: {

                    }
                }
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
                    parentActivity.resetErrorLabel();
                    parentActivity.getCovid19FormPart1().setValues();
                    return super.onInterceptTouchEvent(event);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    parentActivity.setErrorMessage(NOTICE);
                    return pagingEnabled;
                }
            }
            break;
            case 1: {
                pagingEnabled = parentActivity.isPart2FormDone();
                if (pagingEnabled) {
                    parentActivity.resetErrorLabel();
                    parentActivity.getCovid19FormPart2().setValues();
                    return super.onInterceptTouchEvent(event);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    parentActivity.setErrorMessage(NOTICE);
                    return pagingEnabled;
                }
            }
            break;
            case 2: {
                pagingEnabled = parentActivity.isPart3FormDone();
                if (pagingEnabled) {
                    parentActivity.resetErrorLabel();
                    parentActivity.getCovid19FormPart3().setValues();
                    return super.onInterceptTouchEvent(event);
                } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    parentActivity.setErrorMessage(NOTICE);
                    return pagingEnabled;
                }
            }
            break;
            case 3:
                boolean part4IsDone = parentActivity.isPart4FormDone();
                if (part4IsDone)
                    parentActivity.resetErrorLabel();
                else if (event.getAction() == MotionEvent.ACTION_MOVE)
                    parentActivity.setErrorMessage(NOTICE);

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