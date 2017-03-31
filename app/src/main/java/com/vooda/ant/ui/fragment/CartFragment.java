package com.vooda.ant.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.api.RetrofitHelper;
import com.vooda.ant.base.BaseApplication;
import com.vooda.ant.base.RxLazyFragment;
import com.vooda.ant.bean.BaseBean;
import com.vooda.ant.bean.CartBean;
import com.vooda.ant.ui.adapter.CartAdapter;
import com.vooda.ant.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create by  leijiaxq
 * Date       2017/3/13 15:48
 * Describe
 */
public class CartFragment extends RxLazyFragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.title_tv)
    TextView mTitleTv;

    @BindView(R.id.cart_all_tv)
    TextView mCartAllTv;
    @BindView(R.id.market_total_tv)
    TextView mMarketTotalTv;
    @BindView(R.id.market_settle_btn)
    Button mMarketSettleBtn;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swip_refresh)
    SwipeRefreshLayout mSwipRefresh;

    AppCompatActivity mAppCompatActivity;

    public int mPageIndex = 1;
    public boolean mIsAllLoaded = false;
    public boolean mRefresh = true;
    private List<CartBean.DataBean> mDatas = new ArrayList<>();
    private CartAdapter mAdapter;

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

        mSwipRefresh.setOnRefreshListener(this);
        mSwipRefresh.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//
//                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
//                        .findLastVisibleItemPosition();
//                int visibleItemCount = layoutManager.getChildCount();
//                int totalItemCount = layoutManager.getItemCount();
//
//                if (!mIsAllLoaded && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
//                        && lastVisibleItemPosition >= totalItemCount - 1 && mDatas.size() >= Constants.SIZE) {
//                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
//                    loadMore();
//                }
//               /* int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
//                if (firstCompletelyVisibleItemPosition != 0) {
//                    mFab.setVisibility(View.VISIBLE);
//                } else {
//                    mFab.setVisibility(View.GONE);
//                }*/
//            }
//        });

        initAdapter();

    }


    @Override
    public void loadData() {
        getCartListDataNet();
    }


    @Override
    public void onRefresh() {
        mRefresh = true;
//        mPageIndex = 1;
        mIsAllLoaded = false;
        mAdapter.isAllLoad = false;

        mSwipRefresh.setRefreshing(true);

        getCartListDataNet();
    }


    private void initAdapter() {
        mAdapter = new CartAdapter(mActivity, mDatas);
        mRecyclerView.setAdapter(mAdapter);

    }

    private void getCartListDataNet() {

        if (BaseApplication.mUserInfoBean != null && BaseApplication.mUserInfoBean.UserID != 0) {
            RetrofitHelper.getCartApi()
                    .getCartListNet(BaseApplication.mUserInfoBean.UserID)
                    .compose(this.<CartBean>bindToLifecycle())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<CartBean>() {
                        @Override
                        public void onCompleted() {
                            mSwipRefresh.setRefreshing(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            showNetError();
                        }

                        @Override
                        public void onNext(CartBean cartBean) {
                            if (Constants.OK.endsWith(cartBean.result)) {
                                finishTask(cartBean);
                            } else {
                                ToastUtil.showShort(mActivity, TextUtils.isEmpty(cartBean.message) ? "" : cartBean.message);
                            }
                        }
                    });
        }
    }

    @Override
    protected void finishTask(BaseBean bean) {
        super.finishTask(bean);
        if (bean instanceof CartBean) {
            setCartListData((CartBean) bean);
        }

    }

    //设置购物车列表数据
    private void setCartListData(CartBean bean) {
        if (bean.data == null) {
            bean.data = new ArrayList<>();
        }
        mDatas.clear();
        mDatas.addAll(bean.data);
        mAdapter.isAllLoad = true;

        mAdapter.notifyDataSetChanged();

    }

}
