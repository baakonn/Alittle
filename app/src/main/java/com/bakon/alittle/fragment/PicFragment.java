package com.bakon.alittle.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bakon.alittle.R;
import com.bakon.alittle.bean.NewsBean;
import com.bakon.alittle.util.ToastUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicFragment extends Fragment {
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;

    private int type = 0;
    private Activity context;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mMainView = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, mMainView);
        context = getActivity();
        initData();
        initView();
        return mMainView;
    }

    private void initData() {
        type  = getArguments().getInt("type", 0);
//        ToastUtil.show(getActivity(), "type = "+type);
    }
    private BaseQuickAdapter baseQuickAdapter;
    private List<NewsBean> newsList;


    private void initView() {
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                ToastUtil.showShort(context, "onItemClick==" + position +"  name="+newsList.get(position).who +"  type"+type);
//                Intent intent = new Intent(context, MDPanoramaIphoneActivity.class);
//                intent.putExtra("id", fileNameList.get(position));
//                intent.putExtra("type", 2);
//                startActivity(intent);
            }
        });

        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_comm, newsList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, Object obj) {
                NewsBean newsBean = (NewsBean) obj;
                baseViewHolder.setText(R.id.item_title, newsBean.desc)
                              .setText(R.id.item_name, newsBean.who)
                              .setText(R.id.item_name, newsBean.publishedAt.substring(0,9));
               if(newsBean.images != null && newsBean.images.length > 0) {
                   Glide.with(PicFragment.this)
                           .load(newsBean.images[0])
//                           .override()
                           .error(R.color.baby_pink)
                           .into((ImageView) baseViewHolder.getView(R.id.item_imageview));
               }
            }
        };

        mRecyclerView.setAdapter(baseQuickAdapter);

    }

}
