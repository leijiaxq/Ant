package com.vooda.ant.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.base.RxLazyFragment;

import butterknife.BindView;

/**
 * Create by  leijiaxq
 * Date       2017/3/13 15:48
 * Describe
 */
public class CartFragment extends RxLazyFragment {


    @BindView(R.id.toolbar)
    Toolbar  mToolbar;
    @BindView(R.id.title_tv)
    TextView mTitleTv;

    AppCompatActivity mAppCompatActivity;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView(Bundle state) {
        mTitleTv.setText("购物车");

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

    }

}
