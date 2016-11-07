package com.bakon.alittle.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bakon.alittle.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicFragment extends Fragment {
    @BindView(R.id.llt)
    LinearLayout mLinearLayout;
    @BindView(R.id.fragment_text)
    TextView mtTextView;
    @BindView(R.id.fragment_recycle)
    RecyclerView mreRecyclerView;

    private int type = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mMainView = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, mMainView);
        initData();
        initView();
        return mMainView;
    }

    private void initData() {
        type  = getArguments().getInt("type", 0);
    }

    private void initView() {
        mtTextView.setText("第"+type+"个");
//        switch (type){
//            case 1:
//                mtTextView.setText("第"+type+"个");
//                break;
//            case 2:
//                mtTextView.setText("第"+type+"个");
//                break;
//            case 0:
//            default:
//                mtTextView.setText("第"+type+"个");
//                break;
//        }

    }

}
