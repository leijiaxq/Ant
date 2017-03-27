package com.vooda.ant.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.base.RxLazyFragment;
import com.vooda.ant.ui.adapter.OrderFragmentPagerAdapter;
import com.vooda.ant.ui.fragment.order.CompletedFragment;
import com.vooda.ant.ui.fragment.order.ObligationFragment;
import com.vooda.ant.ui.fragment.order.WaitReceiveFragment;
import com.vooda.ant.ui.fragment.order.WaitSendFragment;
import com.vooda.ant.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Create by  leijiaxq
 * Date       2017/3/13 15:48
 * Describe
 */
public class OrderFragment extends RxLazyFragment {


    @BindView(R.id.toolbar)
    Toolbar   mToolbar;
    @BindView(R.id.title_tv)
    TextView  mTitleTv;
    @BindView(R.id.order_tl)
    TabLayout mOrderTl;
    @BindView(R.id.order_vp)
    ViewPager mOrderVp;

    AppCompatActivity mAppCompatActivity;

    private List<String> mTitles = new ArrayList<>();
    private OrderFragmentPagerAdapter mAdapter;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_order;
    }

    @Override
    public void initView(Bundle state) {
        mTitleTv.setText("我的订单");
        setHasOptionsMenu(true);
        mAppCompatActivity = (AppCompatActivity) mActivity;
        mAppCompatActivity.setSupportActionBar(mToolbar);

        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            //            actionBar.setHomeAsUpIndicator(R.drawable.);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(mAppCompatActivity.getResources().getDrawable(R.drawable.back_white_img));
        }
    }

    @Override
    public void loadData() {
        mTitles.add("待付款");
        mTitles.add("待发货");
        mTitles.add("待收货");
        mTitles.add("已完成");

        initContentFragment();
    }

    //初始化有几个fragment
    private void initContentFragment() {
        ArrayList<Fragment> mFragments = new ArrayList<>();

        mFragments.add(new ObligationFragment());
        mFragments.add(new WaitSendFragment());
        mFragments.add(new WaitReceiveFragment());
        mFragments.add(new CompletedFragment());

        mAdapter = new OrderFragmentPagerAdapter(getChildFragmentManager(),mFragments, mTitles);
        mOrderVp.setOffscreenPageLimit(1);
        mOrderVp.setAdapter(mAdapter);
        mOrderTl.setupWithViewPager(mOrderVp);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            ToastUtil.showShort(mActivity, "返回");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
