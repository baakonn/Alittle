package com.bakon.alittle.fragment;


import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bakon.alittle.NewsView;
import com.bakon.alittle.R;
import com.bakon.alittle.bean.NewsBean;
import com.bakon.alittle.presenter.NewsPresenter;
import com.bakon.alittle.presenter.NewsPresenterImpl;
import com.bakon.alittle.util.Constant;
import com.bakon.alittle.util.LogUtil;
import com.bakon.alittle.util.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.type;

public class NewsFragment extends Fragment implements NewsView,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.recycleview)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    //根据type，构造fragment
    public static NewsFragment newInstance(int type) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }
    //tab类型
    private int mType = 0;
    private Activity context;
    private NewsPresenter mNewsPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mMainView = inflater.inflate(R.layout.fragment_layout, container, false);
        ButterKnife.bind(this, mMainView);
        context = getActivity();
        mNewsPresenter = new NewsPresenterImpl(this);
        initData();
        initView();
        initListener();
        return mMainView;
    }



    private void initData() {
        mType  = getArguments().getInt("type", 0);

    }

    private BaseQuickAdapter baseQuickAdapter;
    private List<NewsBean> mNewsList = new ArrayList<NewsBean>();
    private LinearLayoutManager mLayoutManager;
    private void initView() {
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_comm, mNewsList) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, Object obj) {
                NewsBean newsBean = (NewsBean) obj;
                baseViewHolder.setText(R.id.item_title, newsBean.desc)
                              .setText(R.id.item_name, newsBean.who)
                              .setText(R.id.item_time, newsBean.publishedAt.substring(0,10));

                if (mType == Constant.TAB_TYPE.SURPRISE) {
                    Glide.with(NewsFragment.this)
                           .load(newsBean.url)
                           .error(R.drawable.fo)
                           .into((ImageView) baseViewHolder.getView(R.id.item_imageview));
                } else {
                    baseViewHolder.setBackgroundColor(R.id.item_imageview, R.color.default_img_color);
                    if(newsBean.images != null && newsBean.images.size() > 0) {
//                        Glide.with(NewsFragment.this)
//                                .load(newsBean.images.get(0))
//                                .error(R.drawable.fo)
//                                .into((ImageView) baseViewHolder.getView(R.id.item_imageview));

                        Glide.with(NewsFragment.this).load(newsBean.images.get(0))
                                .asBitmap().into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                colorChange(resource, baseViewHolder);
                            }
                        });
                    }
                }


            }
        };

        mRecyclerView.setAdapter(baseQuickAdapter);
        onRefresh();
    }
    private int lastVisibleItem;
    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void SimpleOnItemClick(BaseQuickAdapter baseQuickAdapter, View view, int position) {
                ToastUtil.showShort(context, "onItemClick==" + position+ "  type"+type);
//                Intent intent = new Intent(context, MDPanoramaIphoneActivity.class);
//                intent.putExtra("id", fileNameList.get(position));
//                intent.putExtra("type", 2);
//                startActivity(intent);
            }
        });


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == baseQuickAdapter.getItemCount()) {
                    //加载更多
                    pagerNum += 1;
                    mNewsPresenter.loadNews(mType, pagerNum);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void colorChange(Bitmap bitmap, final BaseViewHolder baseViewHolder) {
        if (bitmap == null ) {
            return;
        }
        // Palette的部分
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getDarkVibrantSwatch();
                if (vibrant == null) {
                    return;
                }
                LogUtil.d("colorChange-item");
                /* 界面颜色UI统一性处理,看起来更Material一些 */
                baseViewHolder.setBackgroundColor(R.id.item_imageview, vibrant.getRgb())
                        .setTextColor(R.id.item_title, vibrant.getTitleTextColor())
                        .setTextColor(R.id.item_name, vibrant.getBodyTextColor())
                        .setTextColor(R.id.item_time, vibrant.getBodyTextColor());
            }
        });

    }


    //第几页,第一页开始
    private int pagerNum = 1;
    @Override
    public void addNews(List<NewsBean> newsList) {

        if (pagerNum == 1) {
            mNewsList.clear();
        }
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
    public void showLoadFailMsg(String msg) {
        ToastUtil.show(context, "msg");
    }
    @Override
    public void onRefresh() {
        //第一页开始
        pagerNum = 1;
        mNewsPresenter.loadNews(mType, pagerNum);
    }
}
