package com.vooda.ant.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.base.FragmentFactory;
import com.vooda.ant.base.RxBaseActivity;
import com.vooda.ant.base.RxLazyFragment;
import com.vooda.ant.bean.MarketSyntheBean;
import com.vooda.ant.ui.fragment.market.PriceFragment;
import com.vooda.ant.ui.widget.pop.SelectSynthePop;
import com.vooda.ant.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by  leijiaxq
 * Date       2017/3/14 15:14
 * Describe   市场
 */

public class MarketActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar      mToolbar;
    @BindView(R.id.title_tv)
    TextView     mTitleTv;
    @BindView(R.id.market_synthesize_layout)
    LinearLayout mMarketSynthesizeLayout;
    @BindView(R.id.market_price_iv)
    ImageView    mMarketPriceIv;
    @BindView(R.id.market_price_layout)
    LinearLayout mMarketPriceLayout;
    @BindView(R.id.market_filtrate_layout)
    LinearLayout mMarketFiltrateLayout;
    @BindView(R.id.market_synth_line)
    TextView     mMarketSynthLine;
    @BindView(R.id.market_price_line)
    TextView     mMarketPriceLine;
    @BindView(R.id.market_filtrate_line)
    TextView     mMarketFiltrateLine;
    @BindView(R.id.market_line_layout)
    LinearLayout mMarketLineLayout;
    @BindView(R.id.market_fl)
    FrameLayout  mMarketFl;
    @BindView(R.id.market_synthesize_tv)
    TextView     mMarketSynthesizeTv;


    public int mMarket_key;

    private RxLazyFragment mContent;

    public int     mOrderType   = 1;    //		1:最新 2：评价，3：销量 4：价格	编辑
    public int     mOrder       = 0;            //	排序方式0：升序1：降序	编辑
    public String  mParentCID   = "";
    public int     mPageIndex   = 1;
    public boolean mIsAllLoaded = false;
    public boolean mRefresh     = true;

    private List<MarketSyntheBean> mSyntheDatas;

    private int mFragmentIndex = FragmentFactory.FRAGMENT_MARKET_FILTRATE;


    @Override
    public int getLayoutId() {
        return R.layout.activity_market;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mMarket_key = getIntent().getIntExtra("market_key", 1);

        String titleName = "";
        switch (mMarket_key) {
            case 1:
                titleName = "菜市场";
                break;
            case 2:
                titleName = "超市";
                break;
            case 3:
                titleName = "下午茶";
                break;
            default:
                titleName = "菜市场";
                break;
        }
        mTitleTv.setText(titleName);

        mMarketFiltrateLayout.setSelected(true);
        mMarketFiltrateLine.setSelected(true);

        replaceFragment();

    }

    @Override
    public void initToolBar() {
        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(getResources().getDrawable(R.drawable.back_white_img));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_search, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                ToastUtil.showShort(this, "搜索");
                return true;
            case android.R.id.home:
                //                ToastUtil.showShort(this, "返回");
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    /**
     * fragment切换
     */
    public void replaceFragment() {
        RxLazyFragment to = FragmentFactory.getMarketFragment(mFragmentIndex);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mContent != to) {
            if (mContent == null) {
                transaction.add(R.id.market_fl, to).commitAllowingStateLoss();
            } else {
                if (!to.isAdded()) {    // 先判断是否被add过
                    transaction.hide(mContent).add(R.id.market_fl, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mContent).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }
            }
            mContent = to;
        }
    }

    @OnClick(R.id.market_synthesize_layout)
    public void clickSynthesize(View view) {
        mMarketPriceLayout.setSelected(false);
        mMarketPriceLine.setSelected(false);

        mMarketFiltrateLayout.setSelected(false);
        mMarketFiltrateLine.setSelected(false);

        mMarketSynthesizeLayout.setSelected(true);
        mMarketSynthLine.setSelected(true);

        if (mSyntheDatas == null) {
            mSyntheDatas = new ArrayList<>();
            mSyntheDatas.add(new MarketSyntheBean("最新"));
            mSyntheDatas.add(new MarketSyntheBean("评价"));
            mSyntheDatas.add(new MarketSyntheBean("销量"));
        }

        SelectSynthePop selectSynthePop = new SelectSynthePop(this, mSyntheDatas);
        selectSynthePop.setShareListener(new SelectSynthePop.ShareListener() {
            @Override
            public void onItem(int position) {
                mOrderType = position + 1;
                MarketSyntheBean bean = mSyntheDatas.get(position);

                mMarketSynthesizeTv.setText(TextUtils.isEmpty(bean.text) ? "" : bean.text);
                resetPriceFragmentData();
            }
        });
        selectSynthePop.showAsDropDown(mMarketSynthLine, 0, 0);

    }


    boolean mPriceflag = false;

    @OnClick(R.id.market_price_layout)
    public void clickPrice(View view) {
        mMarketPriceLayout.setSelected(true);
        mMarketPriceLine.setSelected(true);

        mMarketFiltrateLayout.setSelected(false);
        mMarketFiltrateLine.setSelected(false);

        mMarketSynthesizeLayout.setSelected(false);
        mMarketSynthLine.setSelected(false);

        int flag = 0;

        if (mPriceflag) {
            mMarketPriceIv.setImageDrawable(getResources().getDrawable(R.drawable.shoushuo1));
            flag = 0;
        } else {
            mMarketPriceIv.setImageDrawable(getResources().getDrawable(R.drawable.zhangkai1));
            flag = 1;
        }
        mPriceflag = !mPriceflag;

        mOrder = flag;

        resetPriceFragmentData();
        //        replaceFragment(FragmentFactory.FRAGMENT_MARKET_PRICE);
    }

    @OnClick(R.id.market_filtrate_layout)
    public void clickFiltrate(View view) {
        mMarketPriceLayout.setSelected(false);
        mMarketPriceLine.setSelected(false);

        mMarketFiltrateLayout.setSelected(true);
        mMarketFiltrateLine.setSelected(true);

        mMarketSynthesizeLayout.setSelected(false);
        mMarketSynthLine.setSelected(false);

        mFragmentIndex = FragmentFactory.FRAGMENT_MARKET_FILTRATE;
        replaceFragment();

    }

    public int getMarketKey() {
        return mMarket_key;
    }

    //重置priceFragment中的数据
    public void resetPriceFragmentData() {
        PriceFragment priceFragment = (PriceFragment) FragmentFactory.getMarketFragment(FragmentFactory.FRAGMENT_MARKET_PRICE);
        boolean flag = false;
        if (priceFragment.isAdded()) {
            flag = true;
        }
        mFragmentIndex = FragmentFactory.FRAGMENT_MARKET_PRICE;
        replaceFragment();

        if (flag) {
            priceFragment.onRefresh();
        }
    }

    @Override
    public void onBackPressed() {
        if (mFragmentIndex != FragmentFactory.FRAGMENT_MARKET_FILTRATE) {
            mFragmentIndex = FragmentFactory.FRAGMENT_MARKET_FILTRATE;
            replaceFragment();
        } else {
            super.onBackPressed();
//            finish();
        }
    }
}
