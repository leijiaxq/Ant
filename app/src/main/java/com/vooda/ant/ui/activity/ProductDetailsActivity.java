package com.vooda.ant.ui.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.api.RetrofitHelper;
import com.vooda.ant.api.RxBus;
import com.vooda.ant.base.BaseApplication;
import com.vooda.ant.base.RxBaseActivity;
import com.vooda.ant.bean.BaseBean;
import com.vooda.ant.bean.CartAddBean;
import com.vooda.ant.bean.CartBean;
import com.vooda.ant.bean.ColletAddBean;
import com.vooda.ant.bean.ColletDeleteBean;
import com.vooda.ant.bean.ColletJudeBean;
import com.vooda.ant.bean.CommentBean;
import com.vooda.ant.bean.CommentCountBean;
import com.vooda.ant.bean.ProductBean;
import com.vooda.ant.ui.adapter.CommentAdapter;
import com.vooda.ant.utils.AddCartAnimUtil;
import com.vooda.ant.utils.DataFormatUtil;
import com.vooda.ant.utils.ImageUtil;
import com.vooda.ant.utils.ScreenUtil;
import com.vooda.ant.utils.ToastUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create by  leijiaxq
 * Date       2017/3/16 13:46
 * Describe   商品详情页
 */
public class ProductDetailsActivity extends RxBaseActivity {


    @BindView(R.id.product_banner)
    Banner               mProductBanner;
    @BindView(R.id.toolbar)
    Toolbar              mToolbar;
    @BindView(R.id.appBar)
    AppBarLayout         mAppBar;
    @BindView(R.id.product_fab)
    FloatingActionButton mProductFab;
    @BindView(R.id.recycler_view)
    RecyclerView         mRecyclerView;
    @BindView(R.id.market_cart_iv)
    ImageView            mMarketCartIv;
    @BindView(R.id.market_cart_tv)
    TextView             mMarketCartTv;
    @BindView(R.id.market_total_tv)
    TextView             mMarketTotalTv;
    @BindView(R.id.market_settle_btn)
    Button               mMarketSettleBtn;

    @BindView(R.id.item_introduction_tv)
    TextView       mItemIntroductionTv;
    @BindView(R.id.item_name_tv)
    TextView       mItemNameTv;
    @BindView(R.id.item_price_tv)
    TextView       mItemPriceTv;
    @BindView(R.id.item_number_tv)
    TextView       mItemNumberTv;
    @BindView(R.id.item_add_iv)
    ImageView      mItemAddIv;
    @BindView(R.id.item_commet_number_tv)
    TextView       mItemCommetNumberTv;
    @BindView(R.id.item_goog_tv)
    TextView       mItemGoogTv;
    @BindView(R.id.item_midle_tv)
    TextView       mItemMidleTv;
    @BindView(R.id.item_bad_tv)
    TextView       mItemBadTv;
    @BindView(R.id.market_cart_fl)
    FrameLayout    mMarketCartFl;
    @BindView(R.id.market_settle_layout)
    RelativeLayout mMarketSettleLayout;
    @BindView(R.id.market_settle_line)
    View           mMarketSettleLine;

    private ProductBean.DataEntity mBean;

    private List<String> mBannersList;

    private int                          mPageIndex   = 1;
    private boolean                      mIsAllLoaded = false;
    private List<CommentBean.DataEntity> mDatas       = new ArrayList<>();
    private CommentAdapter mAdapter;

    //商品评论的类型，0全部，1好评，2中评，3差评
    private int mType = 0;

    //商品是否收藏，true,已收藏，false,未收藏
    private boolean mIsCollet = false;

    private List<CartBean.DataBean> mCartDatas = new ArrayList<>();


    private int mCartCount = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_product_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mBean = getIntent().getParcelableExtra("product_bean");

        if (mBean !=null) {
            mItemNameTv.setText(TextUtils.isEmpty(mBean.ProductName) ? "" : mBean.ProductName);
            String formatPrice = DataFormatUtil.getFormatInstance().format(mBean.Price);

            mItemPriceTv.setText(formatPrice + "/" + mBean.UnitName);
            mItemNumberTv.setText("销售" + mBean.CommentCount + "份");
            mItemIntroductionTv.setText(TextUtils.isEmpty(mBean.Description) ? "" : mBean.Description);

        }


        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this,
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

                if (!mIsAllLoaded && visibleItemCount > 0 && newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition >= totalItemCount - 1 && mDatas.size() >= Constants.SIZE) {
                    mRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                    loadMore();
                }
            }
        });

        initAdapter();
    }


    private void initAdapter() {
        mAdapter = new CommentAdapter(this, mDatas, mBean);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new CommentAdapter.OnItemClickListener() {
            @Override
            public void onItemAddClick(View view, int position) {

                if (mBean !=null) {
                    if (mBean.StockCount > 0) {
                        mBean.StockCount--;

                        //加入购物车
                        getAddCart();
                    } else {
                        ToastUtil.showShort(ProductDetailsActivity.this, "库存不足");
                        return;
                    }
                } else {
                    ToastUtil.showShort(ProductDetailsActivity.this, "数据错误");
                    return;

                }

                // 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                int[] start_location = new int[2];
                // 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                view.getLocationInWindow(start_location);

                // 开始执行动画
                AddCartAnimUtil.getInstance().setAnim(start_location, ProductDetailsActivity.this, mMarketCartIv);

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

            @Override
            public void onGoodClick(int position) {
                getRelatedCommentDetail(1);
            }

            @Override
            public void onMiddleClick(int position) {
                getRelatedCommentDetail(2);
            }

            @Override
            public void onBadClick(int position) {
                getRelatedCommentDetail(3);
            }

            @Override
            public void onItemImageViewClick(View view, int position, int position2) {

            }
        });
    }

    //获取相关评论
    private void getRelatedCommentDetail(int type) {
        mType = type;
        mPageIndex = 1;
        mIsAllLoaded = false;
        getProductCommentDetailNet();
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
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        RxBus.getDefault().post(new ProductBean.DataEntity());

        super.onBackPressed();
    }

    @Override
    public void loadData() {
        super.loadData();
        mBannersList = new ArrayList<>();
        if (mBean != null) {
            if (!TextUtils.isEmpty(mBean.ImageUrl)) {
                String[] split = mBean.ImageUrl.split(",");
                for (int i = 0; i < split.length; i++) {
                    String str = split[i];
                    if (!str.startsWith("http://")) {
                        mBannersList.add(Constants.BASE_URL + str);
                    } else {
                        mBannersList.add(str);
                    }
                }
            }
        }
        int screenWidth = ScreenUtil.getScreenWidth(this);

        ViewGroup.LayoutParams params = mProductBanner.getLayoutParams();
        params.height = screenWidth;
        mProductBanner.setLayoutParams(params);


        setHomeBanner();
        if (mBean != null) {
            getProductCommentCountNet();

            getProductCommentDetailNet();

            if (BaseApplication.mUserInfoBean != null) {
                judeProductIsCollet();
            }
        }

        getCartListDataNet();

    }

    //获取评论详情数据
    private void getProductCommentDetailNet() {
        RetrofitHelper.getBaseApi()
                .getProductCommenDetailNet(mBean.ProID, mType, mPageIndex)
                .compose(this.<CommentBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(CommentBean bean) {
                        if (Constants.OK.equals(bean.result)) {
                            finishTask(bean);
                        } else {
                            ToastUtil.showShort(ProductDetailsActivity.this, TextUtils.isEmpty(bean.message) ? "" : bean.message);
                        }

                    }
                });
    }

    //获取商品评论数量
    private void getProductCommentCountNet() {
        RetrofitHelper.getBaseApi()
                .getProductCommenNumberNet(mBean.ProID)
                .compose(this.<CommentCountBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CommentCountBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(CommentCountBean bean) {
                        if (Constants.OK.equals(bean.result)) {
                            finishTask(bean);
                        } else {
                            ToastUtil.showShort(ProductDetailsActivity.this, TextUtils.isEmpty(bean.message) ? "" : bean.message);
                        }

                    }
                });
    }

    //设置首页轮播图
    private void setHomeBanner() {
        mProductBanner.setImages(mBannersList)
                //                .setBannerTitles(App.titles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setIndicatorGravity(BannerConfig.CENTER)
                .setDelayTime(5000)
                .setImageLoader(new ImageUtil.GlideImageLoader())
                .start();

    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        mProductBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        mProductBanner.stopAutoPlay();
    }


    private void loadMore() {
        mPageIndex++;
        getProductCommentDetailNet();

    }

    @Override
    public void finishTask(BaseBean bean) {
        super.finishTask(bean);
        if (bean instanceof CommentCountBean) {
            setCommentCountData((CommentCountBean) bean);
        } else if (bean instanceof CommentBean) {
            setCommentDetailData((CommentBean) bean);
        } else if (bean instanceof ColletJudeBean) {
            setColletJudeBeanData((ColletJudeBean) bean);
        } else if (bean instanceof ColletAddBean) {
            setAddColletBeanData((ColletAddBean) bean);
        } else if (bean instanceof ColletDeleteBean) {
            setDeleteBeanData((ColletDeleteBean) bean);
        }else if (bean instanceof CartBean) {
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

    //取消商品收藏
    private void setDeleteBeanData(ColletDeleteBean bean) {
        mIsCollet = false;
        setColletImageByFlag();
    }

    //添加商品收藏
    private void setAddColletBeanData(ColletAddBean bean) {
        mIsCollet = true;
        setColletImageByFlag();
    }

    //设置商品收藏的状态
    private void setColletJudeBeanData(ColletJudeBean bean) {
        if (!TextUtils.isEmpty(bean.message)) {
            if ("该商品已收藏.".equals(bean.message)) {
                mIsCollet = true;
            } else {
                mIsCollet = false;
            }
        } else {
            mIsCollet = false;
        }
        setColletImageByFlag();
    }

    private void setColletImageByFlag() {
        mProductFab.setImageResource(mIsCollet ? R.drawable.ic_star_black : R.drawable.ic_star_white);
    }

    //设置商品评论详情
    private void setCommentDetailData(CommentBean bean) {
        if (bean.data == null) {
            bean.data = new ArrayList<>();
            //            return;
        }
        if (bean.data.size() < Constants.SIZE) {
            mIsAllLoaded = true;
        }
        if (mPageIndex == 1) {
            mDatas.clear();
        }
        mAdapter.isAllLoad = mIsAllLoaded;

        mDatas.addAll(bean.data);
        mAdapter.notifyDataSetChanged();

    }

    //设置商品评论数量
    private void setCommentCountData(CommentCountBean bean) {
        if (bean.data != null && bean.data.size() != 0) {
           /* if (mBean != null) {
                CommentCountBean.DataEntity bean2 = bean.data.get(0);
                mBean.mGood = bean2.GoodCount;
                mBean.mMiddle = bean2.NormalCount;
                mBean.mBad = bean2.BadCount;
                mAdapter.notifyItemChanged(0);
            }*/
            CommentCountBean.DataEntity bean2 = bean.data.get(0);

            mItemGoogTv.setText("好评(" + bean2.GoodCount + ")份");
            mItemMidleTv.setText("中评(" + bean2.NormalCount + ")份");
            mItemBadTv.setText("差评(" + bean2.BadCount + ")份");

            int totalComment = bean2.GoodCount + bean2.NormalCount + bean2.BadCount;
            mItemCommetNumberTv.setText("商品评价(" + totalComment + ")");

        }
    }

    @OnClick(R.id.item_add_iv)
    public void clickAdd(View view) {
        if (mBean !=null) {
            if (mBean.StockCount > 0) {
                mBean.StockCount--;

                //加入购物车
                getAddCart();
            } else {
                ToastUtil.showShort(ProductDetailsActivity.this, "库存不足");
                return;
            }
        } else {
            ToastUtil.showShort(ProductDetailsActivity.this, "数据错误");
            return;

        }


        // 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
        int[] start_location = new int[2];
        // 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
        view.getLocationInWindow(start_location);

        // 开始执行动画
        AddCartAnimUtil.getInstance().setAnim(start_location, ProductDetailsActivity.this, mMarketCartIv);

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

    @OnClick(R.id.item_goog_tv)
    public void clickGood(View view) {
        getRelatedCommentDetail(1);
    }

    @OnClick(R.id.item_midle_tv)
    public void clickMidle(View view) {
        getRelatedCommentDetail(2);
    }

    @OnClick(R.id.item_bad_tv)
    public void clickBad(View view) {
        getRelatedCommentDetail(3);
    }

    @OnClick(R.id.product_fab)
    public void clickFab(View view) {
        if (BaseApplication.mUserInfoBean == null) {
            ToastUtil.showShort(this, "请先登录");
            return;
        }
        if (mIsCollet) {

            showHintAboutCollet();

        } else {
            addColletProductNet();
        }
    }

    //弹出有关收藏的提示框
    private void showHintAboutCollet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("是否取消收藏");

        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                removeColletProductNet();
            }
        });
        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    //移除商品收藏
    private void removeColletProductNet() {
        RetrofitHelper.getBaseApi()
                .getDeleteCollectDataNet(mBean.ProID, BaseApplication.mUserInfoBean.UserID, 2, "")
                .compose(this.<ColletDeleteBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ColletDeleteBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(ColletDeleteBean bean) {
                        if (Constants.OK.equals(bean.result)) {
                            finishTask(bean);
                        } else {
                            ToastUtil.showShort(ProductDetailsActivity.this, TextUtils.isEmpty(bean.message) ? "" : bean.message);
                        }
                    }
                });


    }


    //添加商品收藏
    private void addColletProductNet() {
        RetrofitHelper.getBaseApi()
                .getAddCollectDataNet(mBean.ProID, BaseApplication.mUserInfoBean.UserID)
                .compose(this.<ColletAddBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ColletAddBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(ColletAddBean colletAddBean) {
                        if (Constants.OK.equals(colletAddBean.result)) {
                            finishTask(colletAddBean);
                        } else {
                            ToastUtil.showShort(ProductDetailsActivity.this, TextUtils.isEmpty(colletAddBean.message) ? "" : colletAddBean.message);
                        }
                    }
                });
    }

    //判断商品是否已经收藏
    private void judeProductIsCollet() {
        RetrofitHelper.getBaseApi()
                .JudeCollectDataNet(mBean.ProID, BaseApplication.mUserInfoBean.UserID)
                .compose(this.<ColletJudeBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ColletJudeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(ColletJudeBean colletJudeBean) {
                        if (Constants.OK.equals(colletJudeBean.result)) {
                            finishTask(colletJudeBean);
                        } else {
                            ToastUtil.showShort(ProductDetailsActivity.this, TextUtils.isEmpty(colletJudeBean.message) ? "" : colletJudeBean.message);
                        }
                    }
                });
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
                                ToastUtil.showShort(ProductDetailsActivity.this, TextUtils.isEmpty(cartBean.message) ? "" : cartBean.message);
                            }
                        }
                    });
        }
    }

    private void getAddCart() {

        if (BaseApplication.mUserInfoBean != null && BaseApplication.mUserInfoBean.UserID != 0) {
            if (mBean !=null) {
                RetrofitHelper.getBaseApi().getAddShopCartNet(mBean.ProID, BaseApplication.mUserInfoBean.UserID, 1)
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
                                    ToastUtil.showShort(ProductDetailsActivity.this, TextUtils.isEmpty(cartAddBean.message) ? "" : cartAddBean.message);
                                }
                            }
                        });

            } else {
                ToastUtil.showShort(ProductDetailsActivity.this, "数据有误");

            }
        } else {
            ToastUtil.showShort(ProductDetailsActivity.this, "请先登录");
        }
    }

}

