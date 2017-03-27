package com.vooda.ant.ui.fragment.market;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.api.RetrofitHelper;
import com.vooda.ant.api.RxBus;
import com.vooda.ant.base.BaseApplication;
import com.vooda.ant.base.RxLazyFragment;
import com.vooda.ant.bean.BaseBean;
import com.vooda.ant.bean.CartAddBean;
import com.vooda.ant.bean.CartBean;
import com.vooda.ant.bean.ProductBean;
import com.vooda.ant.ui.activity.MarketActivity;
import com.vooda.ant.ui.activity.ProductDetailsActivity;
import com.vooda.ant.ui.adapter.ProductAdapter;
import com.vooda.ant.utils.AddCartAnimUtil;
import com.vooda.ant.utils.LogUtil;
import com.vooda.ant.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Create by  leijiaxq
 * Date       2017/3/14 14:46
 * Describe   已完成
 */
public class PriceFragment extends RxLazyFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.market_cart_iv)
    ImageView mMarketCartIv;
    @BindView(R.id.market_cart_tv)
    TextView mMarketCartTv;
    @BindView(R.id.market_total_tv)
    TextView mMarketTotalTv;
    @BindView(R.id.market_settle_btn)
    Button mMarketSettleBtn;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.swip_refresh)
    SwipeRefreshLayout mSwipRefresh;
    @BindView(R.id.fab)
    FloatingActionButton mFab;


    private List<ProductBean.DataEntity> mDatas = new ArrayList<>();
    private List<CartBean.DataBean> mCartDatas = new ArrayList<>();

    private int mMarketKey;
    private ProductAdapter mAdapter;

    MarketActivity mMarketActivity;

    private int mCartCount = 0;

    private int mPosition = 0;


    @Override
    public int getLayoutResId() {
        return R.layout.fragment_market_price;
    }

    @Override
    public void initView(Bundle state) {
        mMarketActivity = (MarketActivity) getActivity();

        mSwipRefresh.setOnRefreshListener(this);
        mSwipRefresh.setColorSchemeColors(getResources().getIntArray(R.array.gplus_colors));

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager)
                        .findLastVisibleItemPosition();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (!mMarketActivity.mIsAllLoaded && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1 && mDatas.size() >= Constants.SIZE) {
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    loadMore();
                }
                int firstCompletelyVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstCompletelyVisibleItemPosition();
                if (firstCompletelyVisibleItemPosition != 0) {
                    mFab.setVisibility(View.VISIBLE);
                } else {
                    mFab.setVisibility(View.GONE);
                }
            }
        });

        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new ProductAdapter(mActivity, mDatas);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {

            //加入购物车
            @Override
            public void onItemAddClick(View view, int position) {
//                ToastUtil.showShort(mActivity, "加入购物车");
                mPosition = position;

                if (mDatas.size() > position) {
                    ProductBean.DataEntity bean = mDatas.get(position);
                    if (bean.StockCount > 0) {
                        bean.StockCount--;

                        //加入购物车
                        getAddCart(position);
                    } else {
                        ToastUtil.showShort(mActivity, "库存不足");
                        return;
                    }
                } else {
                    ToastUtil.showShort(mActivity, "数据错误");
                    return;

                }


                // 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                int[] start_location = new int[2];
                // 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                view.getLocationInWindow(start_location);

                // 开始执行动画
                AddCartAnimUtil.getInstance().setAnim(start_location, mActivity, mMarketCartIv);

                AddCartAnimUtil.getInstance().setAddCartListener(new AddCartAnimUtil.AddCartListener() {
                    @Override
                    public void onAnimStart() {
                    }

                    @Override
                    public void onAnimEnd() {
                        mCartCount++;
                        mMarketCartTv.setText(mCartCount + "");
                    }
                });

            }

            //跳转到商品详情
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, ProductDetailsActivity.class);
                ProductBean.DataEntity bean = mDatas.get(position);
                intent.putExtra("product_bean", bean);
                startActivity(intent);
            }
        });
    }


    @Override
    public void loadData() {
        mMarketKey = ((MarketActivity) getActivity()).getMarketKey();
        getProductListNet();

        getCartListDataNet();


        //用于--从商品详情页返回市场时 ,从新获取购物车列表数据
        RxBus.getDefault().toObservable(ProductBean.DataEntity.class)
                .compose(this.<ProductBean.DataEntity>bindToLifecycle())
                .subscribe(new Action1<ProductBean.DataEntity>() {
                    @Override
                    public void call(ProductBean.DataEntity dataEntity) {
                        getCartListDataNet();
                    }
                });

    }




    //获取商品列表数据
    private void getProductListNet() {
        RetrofitHelper.getBaseApi()
                .getProductListNet(mMarketKey, mMarketActivity.mPageIndex, mMarketActivity.mOrderType, mMarketActivity.mOrder, mMarketActivity.mParentCID)
                .compose(this.<ProductBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ProductBean>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                        mSwipRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.d(e.toString());
                        mSwipRefresh.setRefreshing(false);
                        showNetError();
                    }

                    @Override
                    public void onNext(ProductBean bean) {
                        if (Constants.OK.equals(bean.result)) {
                            finishTask(bean);
                        } else {
                            ToastUtil.showShort(mActivity, TextUtils.isEmpty(bean.message) ? "" : bean.message);
                        }
                    }
                });

    }

    private void getAddCart(int position) {

        if (BaseApplication.mUserInfoBean != null && BaseApplication.mUserInfoBean.UserID != 0) {
            if (mDatas.size() > position) {
                ProductBean.DataEntity bean = mDatas.get(position);
                RetrofitHelper.getBaseApi().getAddShopCartNet(bean.ProID, BaseApplication.mUserInfoBean.UserID, 1)
                        .compose(this.<CartAddBean>bindToLifecycle())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<CartAddBean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                showNetError();
                            }

                            @Override
                            public void onNext(CartAddBean cartAddBean) {
                                if (Constants.OK.endsWith(cartAddBean.result)) {
                                    finishTask(cartAddBean);
                                } else {
                                    ToastUtil.showShort(mActivity, TextUtils.isEmpty(cartAddBean.message) ? "" : cartAddBean.message);
                                }
                            }
                        });

            } else {
                ToastUtil.showShort(mActivity, "数据有误");

            }
        } else {
            ToastUtil.showShort(mActivity, "请先登录");
        }
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
    public void onRefresh() {
        mMarketActivity.mRefresh = true;
        mMarketActivity.mPageIndex = 1;
        mMarketActivity.mIsAllLoaded = false;
        mAdapter.isAllLoad = false;

        mSwipRefresh.setRefreshing(true);

        getProductListNet();
    }

    private void loadMore() {
        mMarketActivity.mPageIndex++;
        getProductListNet();
    }

    @Override
    protected void finishTask(BaseBean bean) {
        super.finishTask(bean);
        if (bean instanceof ProductBean) {
            setProductListData((ProductBean) bean);
        } else if (bean instanceof CartBean) {
            setCartListData((CartBean) bean);
        }
    }

    //设置购物车的数据列表
    private void setCartListData(CartBean bean) {
        if (bean.data == null) {
            bean.data = new ArrayList<>();
        }
        mCartDatas.clear();
        mCartDatas.addAll(bean.data);

        int size = 0;
        for (int i = 0, length = mCartDatas.size(); i < length; i++) {
            CartBean.DataBean bean2 = mCartDatas.get(i);
            size += bean2.BuyNum;
        }
        if (size > 0) {
            mMarketCartTv.setVisibility(View.VISIBLE);
            mMarketCartTv.setText(size + "");
        }
        mCartCount = size;

    }

    //设置商品列表数据
    private void setProductListData(ProductBean bean) {

        if (bean.data == null) {
            //            ToastUtil.showShort(mActivity, Constants.ERROR);
            //            return;
            bean.data = new ArrayList<>();

        }
        if (bean.data.size() < Constants.SIZE) {   //数据加载完了
            mMarketActivity.mIsAllLoaded = true;
            mAdapter.isAllLoad = true;
        }

        if (mMarketActivity.mRefresh == true || mMarketActivity.mPageIndex == 1) {
            mMarketActivity.mRefresh = false;
            mDatas.clear();
            mRecyclerView.scrollToPosition(0);
        }

        mDatas.addAll(bean.data);
        mAdapter.notifyDataSetChanged();

    }

    @OnClick(R.id.fab)
    public void clickFab(View view) {
//        mRecyclerView.scrollToPosition(0);
        mRecyclerView.smoothScrollToPosition(0);
    }
}
