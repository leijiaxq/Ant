package com.vooda.ant.ui.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.base.BaseApplication;
import com.vooda.ant.base.RxLazyFragment;
import com.vooda.ant.ui.activity.PersonActivity;
import com.vooda.ant.ui.activity.SignInActivity;
import com.vooda.ant.utils.ConvertUtil;
import com.vooda.ant.utils.ImageUtil;
import com.vooda.ant.utils.ScreenUtil;
import com.vooda.ant.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by  leijiaxq
 * Date       2017/3/13 15:48
 * Describe
 */
public class MeFragment extends RxLazyFragment {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toobar)
    CollapsingToolbarLayout mCollapsingToobar;
    @BindView(R.id.appBar)
    AppBarLayout mAppBar;
    @BindView(R.id.me_signin_tv)
    TextView mMeSigninTv;
    @BindView(R.id.me_icon_iv)
    ImageView mMeIconIv;
    @BindView(R.id.me_name_tv)
    TextView mMeNameTv;
    @BindView(R.id.me_icon_layout)
    LinearLayout mMeIconLayout;


    AppCompatActivity mAppCompatActivity;
    @Override
    public int getLayoutResId() {
        return R.layout.fragment_me;
    }

    @Override
    public void initView(Bundle state) {

        setHasOptionsMenu(true);
        mAppCompatActivity = (AppCompatActivity) mActivity;
        mAppCompatActivity.setSupportActionBar(mToolbar);

        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            //            actionBar.setHomeAsUpIndicator(R.drawable.);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(false);
//            actionBar.setHomeAsUpIndicator(mAppCompatActivity.getResources().getDrawable(R.drawable.back_white_img));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (BaseApplication.mUserInfoBean == null || BaseApplication.mUserInfoBean.UserID == 0) {
            mMeSigninTv.setVisibility(View.VISIBLE);
            mMeIconLayout.setVisibility(View.GONE);
        } else {
            mMeSigninTv.setVisibility(View.GONE);
            mMeIconLayout.setVisibility(View.VISIBLE);

            ImageUtil.getInstance().displayCricleImage(mActivity, Constants.BASE_URL + BaseApplication.mUserInfoBean.HeadUrl, mMeIconIv);
            mMeNameTv.setText(TextUtils.isEmpty(BaseApplication.mUserInfoBean.NickName) ? "" : BaseApplication.mUserInfoBean.NickName);

        }
    }

    @Override
    public void loadData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int actionBarHeight = ScreenUtil.getActionBarHeight(mActivity);
            int dimension = (int) mActivity.getResources().getDimension(R.dimen.padding_top);
            int statusBarHeight = ConvertUtil.dp2px(mActivity, 25);
            ViewGroup.LayoutParams params = mToolbar.getLayoutParams();
            params.height = dimension+actionBarHeight;
            mToolbar.setLayoutParams(params);

        }
    }

    @OnClick(R.id.me_signin_tv)
    public void clickSignIn(View view) {
        Intent intent = new Intent(mActivity, SignInActivity.class);
        startActivity(intent);
    }

    //进入个人中心
    @OnClick(R.id.me_icon_layout)
    public void clickIconLayout(View view) {
        Intent intent = new Intent(mActivity, PersonActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.me_about_tv)
    public void clickAbout(View view) {
        ToastUtil.showShort(mActivity, "关于");
    }


}
