package com.vooda.ant.ui.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.base.BaseApplication;
import com.vooda.ant.base.FragmentFactory;
import com.vooda.ant.base.RxBaseActivity;
import com.vooda.ant.base.RxLazyFragment;
import com.vooda.ant.bean.UserBean;
import com.vooda.ant.utils.ActivityStackUtil;
import com.vooda.ant.utils.SPUtil;

import butterknife.BindView;

public class MainActivity extends RxBaseActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.home_rb)
    RadioButton mHomeRb;
    @BindView(R.id.order_rb)
    RadioButton mOrderRb;
    @BindView(R.id.cart_rb)
    RadioButton mCartRb;
    @BindView(R.id.me_rb)
    RadioButton mMeRb;
    @BindView(R.id.main_rg)
    RadioGroup  mMainRg;
    @BindView(R.id.main_fl_layout)
    FrameLayout mMainFlLayout;


    private RxLazyFragment mContent;


    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mMainRg.setOnCheckedChangeListener(this);
        replaceFragment(FragmentFactory.FRAGMENT_HOME);

        judeUserIsSignIn();
    }

    @Override
    public void initToolBar() {

    }


    /**
     * fragment切换
     */
    public void replaceFragment(int index) {
        RxLazyFragment to = FragmentFactory.getFragment(index);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (mContent != to) {
            if (mContent == null) {
                transaction.add(R.id.main_fl_layout, to).commitAllowingStateLoss();
            } else {
                if (!to.isAdded()) {    // 先判断是否被add过
                    transaction.hide(mContent).add(R.id.main_fl_layout, to).commitAllowingStateLoss(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    transaction.hide(mContent).show(to).commitAllowingStateLoss(); // 隐藏当前的fragment，显示下一个
                }
            }
            mContent = to;
        }
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.home_rb:
                replaceFragment(FragmentFactory.FRAGMENT_HOME);
                break;
            case R.id.order_rb:
                replaceFragment(FragmentFactory.FRAGMENT_ORDER);
                break;
            case R.id.cart_rb:
                replaceFragment(FragmentFactory.FRAGMENT_CART);
                break;
            case R.id.me_rb:
                replaceFragment(FragmentFactory.FRAGMENT_ME);
                break;
            default:
                break;
        }
    }


    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(),
                        "再按一次退出",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                ActivityStackUtil.AppExit();
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //判断用户是否已经登录,已登录,就取出存好的信息
    private void judeUserIsSignIn() {
        int userID = (int) SPUtil.get(this, Constants.USERID, 0);
        if (userID != 0) {//用户已经登录
            UserBean.UserInfoBean bean = new UserBean.UserInfoBean();

            String userName = (String) SPUtil.get(this, Constants.USERNAME, "");
            String nickName = (String) SPUtil.get(this, Constants.NICKNAME, "");
            String HeadUrl = (String) SPUtil.get(this, Constants.HEADURL, "");
            String Password = (String) SPUtil.get(this, Constants.PASSWORD, "");
            String Phone = (String) SPUtil.get(this, Constants.PHONE, "");
            String RegisterDate = (String) SPUtil.get(this, Constants.REGISTERDATE, "");
            String LastLoginDat = (String) SPUtil.get(this, Constants.LASTLOGINDATE, "");
            boolean IsStatus = (boolean) SPUtil.get(this, Constants.ISSTATUS, false);
            String OpenID = (String) SPUtil.get(this, Constants.OPENID, "");
            int IsID = (int) SPUtil.get(this, Constants.ISID, 0);
            String sex = (String) SPUtil.get(this, Constants.SEX, "");
            String remark = (String) SPUtil.get(this, Constants.REMARK, "");

            bean.UserID = userID;
            bean.UserName = userName;
            bean.NickName = nickName;
            bean.HeadUrl = HeadUrl;
            bean.Password = Password;
            bean.Phone = Phone;
            bean.RegisterDate = RegisterDate;
            bean.LastLoginDate = LastLoginDat;
            bean.IsStatus = IsStatus;
            bean.OpenID = OpenID;
            bean.IsID = IsID;
            bean.Sex = sex;
            bean.Remark = remark;

            BaseApplication.mUserInfoBean = bean;
        }


    }

}
