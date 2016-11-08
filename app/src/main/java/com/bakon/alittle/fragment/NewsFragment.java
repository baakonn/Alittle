package com.bakon.alittle.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bakon.alittle.NewsView;
import com.bakon.alittle.R;
import com.bakon.alittle.bean.NewsBean;
import com.bakon.alittle.presenter.NewsPresenterImpl;
import com.bakon.alittle.util.ToastUtil;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFragment extends Fragment implements NewsView,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;




    public static NewsFragment newInstance(int type) {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    private int type = 0;
    private Activity context;
    private NewsPresenterImpl mNewsPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mMainView = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, mMainView);
        context = getActivity();
        mNewsPresenter = new NewsPresenterImpl();
        initData();
        initView();
        return mMainView;
    }

    private void initData() {
        type  = getArguments().getInt("type", 0);
//        ToastUtil.show(getActivity(), "type = "+type);
    }
    private BaseQuickAdapter baseQuickAdapter;
    private List<NewsBean> mNewsList = new ArrayList<>();


    private void initView() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                ToastUtil.showShort(context, "onItemClick==" + position +"  name="+mNewsList.get(position).who +"  type"+type);
//                Intent intent = new Intent(context, MDPanoramaIphoneActivity.class);
//                intent.putExtra("id", fileNameList.get(position));
//                intent.putExtra("type", 2);
//                startActivity(intent);
            }
        });

        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_comm, mNewsList) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, Object obj) {
                NewsBean newsBean = (NewsBean) obj;
                baseViewHolder.setText(R.id.item_title, newsBean.desc)
                              .setText(R.id.item_name, newsBean.who)
                              .setText(R.id.item_name, newsBean.publishedAt.substring(0,9));
               if(newsBean.images != null && newsBean.images.length > 0) {
                   Glide.with(NewsFragment.this)
                           .load(newsBean.images[0])
//                           .override()
                           .error(R.color.baby_pink)
                           .into((ImageView) baseViewHolder.getView(R.id.item_imageview));
               }
            }
        };

        mRecyclerView.setAdapter(baseQuickAdapter);
        onRefresh();
    }



    @Override
    public void addNews(List<NewsBean> newsList) {
        if (newsList != null && !newsList.isEmpty()) {
            mNewsList.addAll(newsList);
            baseQuickAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }
    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoadFailMsg() {

    }

    @Override
    public void onRefresh() {
        mNewsPresenter.loadNews("", type);
    }
}
