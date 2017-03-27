package com.vooda.ant.ui.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.api.RetrofitHelper;
import com.vooda.ant.base.BaseApplication;
import com.vooda.ant.base.RxBaseActivity;
import com.vooda.ant.bean.BaseBean;
import com.vooda.ant.bean.UserBean;
import com.vooda.ant.utils.RegexUtil;
import com.vooda.ant.utils.SPUtil;
import com.vooda.ant.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create by  leijiaxq
 * Date       2017/3/21 10:15
 * Describe   登录注册页面
 */
public class SignInActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar  mToolbar;
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.phone_et)
    EditText mPhoneEt;
    @BindView(R.id.password_et)
    EditText mPasswordEt;
    @BindView(R.id.sign_in_tv)
    TextView mSignInTv;
    @BindView(R.id.sign_up_tv)
    TextView mSignUpTv;
    @BindView(R.id.miss_password_tv)
    TextView mMissPasswordTv;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mTitleTv.setText("登录");

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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.sign_in_tv)
    public void clickSignInTv(View view) {
        String phone = mPhoneEt.getText().toString().trim();
        String passWord = mPasswordEt.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(passWord)) {
            ToastUtil.showShort(this, "账号或密码为空");
            return;
        }

        if (!RegexUtil.isMobilePhone(phone)) {
            ToastUtil.showShort(this, "手机号码格式不对");
            return;
        }

        if (!RegexUtil.isPassword(passWord)) {
            ToastUtil.showShort(this, "密码长度不对或者是含有非法字符");
            return;
        }

        signInNet(phone, passWord);

    }

    //登录
    private void signInNet(String phone, String passWord) {
        RetrofitHelper.getMeService()
                .signIn(phone, passWord)
                .compose(this.<UserBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(UserBean userBean) {
                        finishTask(userBean);
                    }
                });
    }

    @Override
    public void finishTask(BaseBean bean) {
        super.finishTask(bean);
        if (bean instanceof UserBean) {
            setUserBean((UserBean) bean);
        }


    }

    //设置userBean数据
    private void setUserBean(UserBean bean) {
        if (bean.data == null || bean.data.size() == 0) {
            ToastUtil.showShort(this, "用户信息有误");
            return;
        }

        BaseApplication.mUserInfoBean = bean.data.get(0);
        ToastUtil.showShort(this, "登录成功");
        saveUserInfoInXml(BaseApplication.mUserInfoBean);

        finish();


    }

    private void saveUserInfoInXml(UserBean.UserInfoBean bean) {
        SPUtil.put(this, Constants.USERID,bean.UserID);
        SPUtil.put(this, Constants.USERNAME,bean.UserName);
        SPUtil.put(this, Constants.NICKNAME,bean.NickName);
        SPUtil.put(this, Constants.HEADURL,bean.HeadUrl);
        SPUtil.put(this, Constants.PASSWORD,bean.Password);
        SPUtil.put(this, Constants.REGISTERDATE,bean.RegisterDate);
        SPUtil.put(this, Constants.LASTLOGINDATE,bean.LastLoginDate);
        SPUtil.put(this, Constants.ISSTATUS,bean.IsStatus);
        SPUtil.put(this, Constants.OPENID,bean.OpenID);
        SPUtil.put(this, Constants.ISID,bean.IsID);
        SPUtil.put(this, Constants.SEX,bean.Sex);
        SPUtil.put(this, Constants.REMARK,bean.Remark);
    }

}
