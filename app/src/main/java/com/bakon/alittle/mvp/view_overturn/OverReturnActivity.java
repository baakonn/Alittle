package com.bakon.alittle.mvp.view_overturn;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bakon.alittle.R;
import com.bakon.base_lib.baseutil.ToastUtil;
import com.bakon.base_lib.mvp.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * MainActivity
 */
public class OverReturnActivity extends BaseActivity {
    @BindView(R.id.main)
    FrameLayout main;
    @BindView(R.id.font)
    LinearLayout font;
    @BindView(R.id.back)
    LinearLayout back;
    @BindView(R.id.btn_return)
    TextView btnReturn;

    private AnimatorSet mRightOutAnimatorSet, mLeftInAnimatorSet;
    private boolean mIsShowBack = false;

    public void setupActivityComponent() {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_overreturn;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        setAnimators(); // 设置动画
        setCameraDistance(); // 设置镜头距离
    }

    private void setAnimators() {
        mRightOutAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_right_out);
        mLeftInAnimatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.anim_left_in);

        // 设置点击事件
        mRightOutAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                btnReturn.setClickable(false);
                font.setClickable(false);
                back.setClickable(false);

                font.setVisibility(View.VISIBLE);
                back.setVisibility(View.VISIBLE);
            }

        });

        mLeftInAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                btnReturn.setClickable(true);
                font.setClickable(true);
                back.setClickable(true);

                if (mIsShowBack) {
                    font.setVisibility(View.GONE);
                } else {
                    back.setVisibility(View.GONE);
                }


            }
        });
    }

    private void setCameraDistance() {
        int distance = 16000;
        float scale = getResources().getDisplayMetrics().density * distance;
        font.setCameraDistance(scale);
        back.setCameraDistance(scale);
    }

    @OnClick({R.id.btn_return, R.id.back, R.id.font})
    void btnOnclick(View view) {
        switch (view.getId()) {
            case R.id.btn_return:
                if (!mIsShowBack) {  // 正面朝上
                    mRightOutAnimatorSet.setTarget(font);
                    mLeftInAnimatorSet.setTarget(back);
                    mRightOutAnimatorSet.start();
                    mLeftInAnimatorSet.start();
                    mIsShowBack = true;
                } else { // 背面朝上
                    mRightOutAnimatorSet.setTarget(back);
                    mLeftInAnimatorSet.setTarget(font);
                    mRightOutAnimatorSet.start();
                    mLeftInAnimatorSet.start();
                    mIsShowBack = false;
                }
                break;
            case R.id.back:
                ToastUtil.showCenterGravityToast("我是背面");
                break;
            case R.id.font:
                ToastUtil.showCenterGravityToast("我是正面");
                break;
        }
    }


    @Override
    protected void onDestroy() {
        mLeftInAnimatorSet.end();
        mRightOutAnimatorSet.end();
        super.onDestroy();
    }
}
