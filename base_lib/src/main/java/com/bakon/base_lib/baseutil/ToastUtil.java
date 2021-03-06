package com.bakon.base_lib.baseutil;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bakon.base_lib.R;
import com.bakon.base_lib.base.BaseApplication;


/**
 * ToastUtil
 */
public class ToastUtil {

    public static void show(Context context, int no_result) {
        Toast.makeText(context, no_result, Toast.LENGTH_LONG).show();
    }

    public static void show(int no_result) {
        Toast.makeText(BaseApplication.getInstance(), no_result, Toast.LENGTH_LONG).show();
    }

    public static void showShort(String no_result) {
        if (TextUtils.isEmpty(no_result)) {
            return;
        }
        Toast.makeText(BaseApplication.getInstance(), no_result, Toast.LENGTH_SHORT).show();
    }

    public static void showShort(Context context, String no_result) {
        if (TextUtils.isEmpty(no_result)) {
            return;
        }
        Toast.makeText(context, no_result, Toast.LENGTH_SHORT).show();
    }

    //整个App内共用的Toast提示
    public static Toast showToast(String msg) {
        msg = CommUtil.processStr(msg);
        int padding = (int) (10 * SystemUtil.getScreenDensity());
        Toast mToast = new Toast(BaseApplication.getInstance());
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        TextView title = new TextView(BaseApplication.getInstance());
        title.setBackgroundResource(R.drawable.stroke_round_corner);
        title.setPadding(2 * padding, (int) (1.5f * padding), 2 * padding, (int) (1.5f * padding));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        title.setId(R.id.title);
        title.setTextColor(BaseApplication.getInstance().getResources().getColor(R.color.white));
        int width = FrameLayout.LayoutParams.WRAP_CONTENT;
        int height = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) new FrameLayout.LayoutParams(width, height);
        title.setLayoutParams(lp);
        title.setText(msg);
        mToast.setView(title);
        mToast.show();
        return mToast;
    }

    public static Toast showDefaultGravityToast(String msg) {
        return showDefaultGravityToast(msg, false);
    }

    public static Toast showCenterGravityToast(String msg) {
        return showDefaultGravityToast(msg, true);
    }

    public static Toast showDefaultGravityToast(String msg, boolean isCenter) {
        msg = CommUtil.processStr(msg);
        int padding = (int) (10 * SystemUtil.getScreenDensity());
        Toast mDefaultGravityToast = new Toast(BaseApplication.getInstance());
        if (isCenter) {
            mDefaultGravityToast.setGravity(Gravity.CENTER, 0, 0);
        }
        mDefaultGravityToast.setDuration(Toast.LENGTH_SHORT);
        TextView title = new TextView(BaseApplication.getInstance());
        title.setBackgroundResource(R.drawable.stroke_round_corner);
        title.setPadding(2 * padding, (int) (1.5f * padding), 2 * padding, (int) (1.5f * padding));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        title.setId(R.id.title);
        title.setTextColor(Color.WHITE);
        int width = FrameLayout.LayoutParams.WRAP_CONTENT;
        int height = FrameLayout.LayoutParams.WRAP_CONTENT;
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) new FrameLayout.LayoutParams(width, height);
        title.setLayoutParams(lp);
        title.setText(msg);
        mDefaultGravityToast.setView(title);
        mDefaultGravityToast.show();
        return mDefaultGravityToast;
    }

    public static Toast showToast(String msg, int duration) {
        return showToast(msg);
    }

    public static Toast showToastWithImage(String msg, int imageId) {
        return showToastWithImageCancel(msg, imageId);
    }

    public static Toast showToastWithImage(String msg) {
        return showToastWithImageCancel(msg, R.drawable.yes);
    }


    public static Toast showToastWithImageCancel(String msg, int cancel_icon) {
        Toast mToastWithImage = new Toast(BaseApplication.getInstance());
        mToastWithImage.setGravity(Gravity.CENTER, 0, 0);
        View convertView = LayoutInflater.from(BaseApplication.getInstance()).inflate(R.layout.toast_layout, null);
        //图片
        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        icon.setImageResource(cancel_icon);
        //文字
        TextView title = (TextView) convertView.findViewById(R.id.tip);
        title.setText(msg);
        mToastWithImage.setView(convertView);
        mToastWithImage.show();
        return mToastWithImage;
    }
}
