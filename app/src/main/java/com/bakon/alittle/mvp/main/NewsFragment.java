package com.bakon.alittle.mvp.main;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bakon.alittle.R;
import com.bakon.alittle.bean.NewsBean;
import com.bakon.alittle.util.Constant;
import com.bakon.base_lib.base.BaseFragment;
import com.bakon.base_lib.baseutil.LogUtil;
import com.bakon.base_lib.baseutil.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class NewsFragment extends BaseFragment<MainPresenter> implements SwipeRefreshLayout.OnRefreshListener {
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


    @Override
    public int getLayoutID() {
        return R.layout.fragment_layout;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initData();
        initView();
        initListener();
    }

    @Override
    protected MainPresenter getPresenter() {
        //这里可以new 其他Presenter
        return new MainPresenter();
    }

    private void initData() {
        mType = getArguments().getInt("type", 0);
    }

    private BaseQuickAdapter baseQuickAdapter;
    private List<NewsBean> mNewsList = new ArrayList<NewsBean>();
    private LinearLayoutManager mLayoutManager;

    private void initView() {
        mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        baseQuickAdapter = new BaseQuickAdapter(R.layout.item_comm, mNewsList) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, Object obj) {
                NewsBean newsBean = (NewsBean) obj;
                baseViewHolder.setText(R.id.item_title, newsBean.desc)
                        .setText(R.id.item_name, newsBean.who)
                        .setText(R.id.item_time, newsBean.publishedAt.substring(0, 10));

                if (mType == Constant.TAB_TYPE.SURPRISE) {
                    //福利不显示desc
                    baseViewHolder.setVisible(R.id.item_title, false);
                    Glide.with(NewsFragment.this)
                            .load(newsBean.url)
                            .error(R.drawable.fi)
                            .into((ImageView) baseViewHolder.getView(R.id.item_imageview));
                } else {
                    baseViewHolder.setBackgroundColor(R.id.item_imageview, R.color.default_img_color);
                    if (newsBean.images != null && newsBean.images.size() > 0) {
                        Glide.with(NewsFragment.this)
                                .load(newsBean.images.get(0))
                                .asBitmap()
                                .into(new SimpleTarget<Bitmap>() {
                                    @Override
                                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                        //取色
                                        colorChange(resource, baseViewHolder);
                                    }
                                });
                    }
                }
            }
        };
        //自定义动画
        baseQuickAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        baseQuickAdapter.openLoadAnimation(new BaseAnimation() {
//            @Override
//            public Animator[] getAnimators(View view) {
//                return new Animator[]{
//                        ObjectAnimator.ofFloat(view, "translationY", view.getMeasuredHeight() * 4, 0),
//                        ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1f),
//                        ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f),
//                        ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f)
//                };
//            }
//        });

        mRecyclerView.setAdapter(baseQuickAdapter);
        onRefresh();
    }

    private int lastVisibleItem;

    private void initListener() {
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                //                ToastUtil.showShort(context, "onItemClick==" + position+ "  type"+type);
                Intent intent = new Intent(context, GankDetailsActivity.class);
                intent.putExtra("url", mNewsList.get(position).url);
                startActivity(intent);
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
                    mPresenter.loadNews(mType, pagerNum);
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
        if (bitmap == null) {
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

    public void addNews(List<NewsBean> newsList) {

        if (pagerNum == 1) {
            mNewsList.clear();
        }
        if (newsList != null && !newsList.isEmpty()) {
            mNewsList.addAll(newsList);
            baseQuickAdapter.notifyDataSetChanged();
        }

    }

    public void showProgress() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    public void hideProgress() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public void showLoadFailMsg(String msg) {
        ToastUtil.showShort("msg");
    }

    @Override
    public void onRefresh() {
        //第一页开始
        pagerNum = 1;
        mPresenter.loadNews(mType, pagerNum);
    }


    @Override
    protected boolean useEventBus() {
        return false;
    }
}
