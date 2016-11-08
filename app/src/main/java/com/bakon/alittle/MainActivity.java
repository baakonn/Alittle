package com.bakon.alittle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.bakon.alittle.fragment.PicFragment;
import com.bakon.alittle.widget.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bakon.alittle.util.Constant.backPic;
import static com.bakon.alittle.util.Constant.tabName;

/**
 * MainActivity
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawerlayout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.drawer_view)
    LinearLayout mDrawerContent;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip mPagerSlidingTabStrip;

    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private List<Fragment> fragmentList = new ArrayList<>();

    private void initViews() {
        Fragment picFragment = new PicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 0);
        picFragment.setArguments(bundle);

        Fragment picFragment1 = new PicFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putInt("type", 1);
        picFragment1.setArguments(bundle1);

        Fragment picFragment2 = new PicFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putInt("type", 2);
        picFragment2.setArguments(bundle2);

        Fragment picFragment3 = new PicFragment();
        Bundle bundle3 = new Bundle();
        bundle3.putInt("type", 3);
        picFragment3.setArguments(bundle3);

        Fragment picFragment4 = new PicFragment();
        Bundle bundle4 = new Bundle();
        bundle4.putInt("type", 4);
        picFragment4.setArguments(bundle4);

        fragmentList.add(picFragment);
        fragmentList.add(picFragment1);
        fragmentList.add(picFragment2);
        fragmentList.add(picFragment3);
        fragmentList.add(picFragment4);

        //mToolbar init
//        mToolbar.setLogo(R.drawable.ic_first);
        mToolbar.setTitle("A Little");// 标题的文字需在setSupportActionBar之前，不然会无效
//        mToolbar.setSubtitle("副标题");
        setSupportActionBar(mToolbar);

        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建返回键，并实现打开关/闭监听
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);


        //mViewPager init
        HomeViewPageAdapter adapter = new HomeViewPageAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(adapter);

        mPagerSlidingTabStrip.setViewPager(mViewPager);
        mPagerSlidingTabStrip.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int pos) {
                colorChange(pos);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });

        initTabsValue();
    }

    /**
     * mPagerSlidingTabStrip默认值配置
     */
    private void initTabsValue() {
        // tab底线高度
        mPagerSlidingTabStrip.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                1, getResources().getDisplayMetrics()));
        // 游标高度
        mPagerSlidingTabStrip.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                5, getResources().getDisplayMetrics()));
        // 选中的文字颜色
        mPagerSlidingTabStrip.setSelectedTextColor(Color.WHITE);
        // 正常文字颜色
        mPagerSlidingTabStrip.setTextColor(Color.BLACK);
//        mPagerSlidingTabStrip.setTextSize(42);
        //初始第一个
        colorChange(0);
    }

    /**
     * 修改ui色调
     * @param position
     */
    private void colorChange(int position) {
        // 用来提取颜色的Bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), backPic[position]);
        // Palette的部分
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {//提取完之后的回调方法
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch vibrant = palette.getVibrantSwatch();
                if (vibrant == null) {
                    return;
                }
                /* 界面颜色UI统一性处理,看起来更Material一些 */
                mPagerSlidingTabStrip.setBackgroundColor(vibrant.getRgb());
                mPagerSlidingTabStrip.setTextColor(vibrant.getTitleTextColor());
                // 其中状态栏、游标、底部导航栏的颜色需要加深一下，也可以不加，具体情况在代码之后说明
                mPagerSlidingTabStrip.setIndicatorColor(colorBurn(vibrant.getRgb()));

                mToolbar.setBackgroundColor(vibrant.getRgb());
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    Window window = getWindow();
                    // 很明显，这两货是新API才有的。
                    window.setStatusBarColor(colorBurn(vibrant.getRgb()));
                    window.setNavigationBarColor(colorBurn(vibrant.getRgb()));
                }

//                mDrawerContent.setBackgroundColor(vibrant.getRgb());
            }
        });
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     *                  Android中我们一般使用它的16进制，
     *                  例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     *                  red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     *                  所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    /**
     * HomeViewPageAdapter
     */
    public class HomeViewPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments;

        public HomeViewPageAdapter(FragmentManager fm, List<Fragment> mFragments) {
            super(fm);
            this.mFragments = mFragments;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabName[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

    }

}
