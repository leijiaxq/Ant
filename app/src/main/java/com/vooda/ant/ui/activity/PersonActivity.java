package com.vooda.ant.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.base.BaseApplication;
import com.vooda.ant.base.RxBaseActivity;
import com.vooda.ant.utils.ImageUtil;
import com.vooda.ant.utils.SPUtil;
import com.vooda.ant.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Create by  leijiaxq
 * Date       2017/3/21 13:58
 * Describe   个人中心
 */
public class PersonActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar        mToolbar;
    @BindView(R.id.title_tv)
    TextView       mTitleTv;
    @BindView(R.id.icon_iv)
    ImageView      mIconIv;
    @BindView(R.id.icon_layout)
    RelativeLayout mIconLayout;
    @BindView(R.id.nick_name_tv)
    TextView       mNickNameTv;
    @BindView(R.id.nick_name_layout)
    RelativeLayout mNickNameLayout;
    @BindView(R.id.gender_tv)
    TextView       mGenderTv;
    @BindView(R.id.gender_layout)
    RelativeLayout mGenderLayout;
    @BindView(R.id.password_tv)
    TextView       mPasswordTv;
    @BindView(R.id.phone_tv)
    TextView       mPhoneTv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_person;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mTitleTv.setText("个人资料");

        ImageUtil.getInstance().displayCricleImage(this, Constants.BASE_URL + BaseApplication.mUserInfoBean.HeadUrl, mIconIv);

        mGenderTv.setText(TextUtils.isEmpty(BaseApplication.mUserInfoBean.Sex) ? "" : BaseApplication.mUserInfoBean.Sex);
        mNickNameTv.setText(TextUtils.isEmpty(BaseApplication.mUserInfoBean.NickName) ? "" : BaseApplication.mUserInfoBean.NickName);
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
        getMenuInflater().inflate(R.menu.item_person, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_save:
                ToastUtil.showShort(this, "保存");
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //退出登录
    @OnClick(R.id.exit_tv)
    public void clickExit(View view) {
        SPUtil.remove(this, Constants.USERID);
        BaseApplication.mUserInfoBean = null;

        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.icon_layout)
    public void clickIconLayout(View view) {
        Intent intent = new Intent(PersonActivity.this, PersonIconActivity.class);
        startActivity(intent);
    }
}
