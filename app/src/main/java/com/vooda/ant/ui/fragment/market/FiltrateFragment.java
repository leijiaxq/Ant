package com.vooda.ant.ui.fragment.market;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.api.RetrofitHelper;
import com.vooda.ant.base.RxLazyFragment;
import com.vooda.ant.bean.BaseBean;
import com.vooda.ant.bean.FiltrateItemBean;
import com.vooda.ant.bean.FiltrateTotalBean;
import com.vooda.ant.ui.activity.MarketActivity;
import com.vooda.ant.ui.adapter.FiltrateItemAdapter;
import com.vooda.ant.ui.adapter.FiltrateTotalAdapter;
import com.vooda.ant.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Create by  leijiaxq
 * Date       2017/3/14 14:46
 * Describe   已完成
 */
public class FiltrateFragment extends RxLazyFragment {


    @BindView(R.id.recycler_view1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.filtrate_total_tv)
    TextView     mFiltrateTotalTv;
    @BindView(R.id.recycler_view2)
    RecyclerView mRecyclerView2;
    private int mMarketKey;

    private List<FiltrateTotalBean.DataEntity> mDatas1 = new ArrayList<>();
    private List<FiltrateItemBean.DataEntity>  mDatas2 = new ArrayList<>();

    private FiltrateTotalAdapter mAdapter1;
    private FiltrateItemAdapter  mAdapter2;
    private MarketActivity       mMarketActivity;

    //用于类型位置的标记
    private int mTotalIndex = 0;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_market_filtrate;
    }

    @Override
    public void initView(Bundle state) {
        mRecyclerView1.setHasFixedSize(true);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(mActivity,
                LinearLayoutManager.VERTICAL, false));
        mRecyclerView1.setItemAnimator(new DefaultItemAnimator());

        initAdapter1();


        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(new GridLayoutManager(mActivity, 3));
        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        initAdapter2();


    }

    private void initAdapter1() {
        mAdapter1 = new FiltrateTotalAdapter(mActivity, mDatas1);
        mRecyclerView1.setAdapter(mAdapter1);

        mAdapter1.setOnItemClickListener(new FiltrateTotalAdapter.OnItemClickListener() {
            @Override
            public void onHeadItemClick(int position) {
                mTotalIndex = position;
                for (int i = 0, length = mDatas1.size(); i < length; i++) {
                    mDatas1.get(i).mFlag = false;
                }
                mAdapter1.setTotalState(true);
                mAdapter1.notifyDataSetChanged();
                mFiltrateTotalTv.setText("全部商品");

                getTotalTypeItemNet("all");

            }

            @Override
            public void onItemClick(int position) {
                mTotalIndex = position;
                for (int i = 0, length = mDatas1.size(); i < length; i++) {
                    if (position - 1 == i) {
                        mDatas1.get(i).mFlag = true;
                    } else {
                        mDatas1.get(i).mFlag = false;
                    }
                }
                mAdapter1.setTotalState(false);
                mAdapter1.notifyDataSetChanged();
                FiltrateTotalBean.DataEntity bean = mDatas1.get(position - 1);


                mFiltrateTotalTv.setText("全部" + bean.ClassName);

                getTotalTypeItemNet(bean.CID + "");

            }
        });

    }

    private void initAdapter2() {
        mAdapter2 = new FiltrateItemAdapter(mActivity, mDatas2);
        mRecyclerView2.setAdapter(mAdapter2);

        mAdapter2.setOnItemClickListener(new FiltrateItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                FiltrateItemBean.DataEntity bean = mDatas2.get(position);
                mMarketActivity.mParentCID = bean.ParentID;
                mMarketActivity.resetPriceFragmentData();
            }
        });
    }


    @Override
    public void loadData() {
        mMarketActivity = ((MarketActivity) getActivity());
        mMarketKey = mMarketActivity.getMarketKey();

        getTotalTypeNet();

        getTotalTypeItemNet("all");

    }


    //获取商品分类属性
    private void getTotalTypeNet() {
        RetrofitHelper.getBaseApi().getTotalType(mMarketKey, "other")
                .compose(this.<FiltrateTotalBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FiltrateTotalBean>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(FiltrateTotalBean bean) {
                        if (Constants.OK.equals(bean.result)) {
                            finishTask(bean);
                        } else {
                            ToastUtil.showShort(mActivity, TextUtils.isEmpty(bean.message) ? "" : bean.message);
                        }

                    }
                });
    }

    //获取商品分类属性
    private void getTotalTypeItemNet(String CID) {
        RetrofitHelper.getBaseApi().getItemType(mMarketKey, CID)
                .compose(this.<FiltrateItemBean>bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<FiltrateItemBean>() {
                    @Override
                    public void onCompleted() {
                        hideProgressDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showNetError();
                    }

                    @Override
                    public void onNext(FiltrateItemBean bean) {
                        if (Constants.OK.equals(bean.result)) {
                            finishTask(bean);
                        } else {
                            ToastUtil.showShort(mActivity, TextUtils.isEmpty(bean.message) ? "" : bean.message);
                        }

                    }
                });
    }


    @Override
    protected void finishTask(BaseBean bean) {
        super.finishTask(bean);
        if (bean instanceof FiltrateTotalBean) {
            setFiltrateTotalData((FiltrateTotalBean) bean);
        } else if (bean instanceof FiltrateItemBean) {
            setFiltrateItemData((FiltrateItemBean) bean);
        }
    }


    private void setFiltrateTotalData(FiltrateTotalBean bean) {
        if (bean.data == null) {
            //            ToastUtil.showShort(mActivity,Constants.ERROR);
            //            return;
            bean.data = new ArrayList<>();
        }
        mDatas1.clear();
        mDatas1.addAll(bean.data);
        mAdapter1.notifyDataSetChanged();
    }

    private void setFiltrateItemData(FiltrateItemBean bean) {
        if (bean.data == null) {
            //            ToastUtil.showShort(mActivity,Constants.ERROR);
            //            return;
            bean.data = new ArrayList<>();
        }
        mDatas2.clear();
        mDatas2.addAll(bean.data);
        mAdapter2.notifyDataSetChanged();
    }

    @OnClick(R.id.filtrate_total_tv)
    public void clickTotal(View view) {
        if (mTotalIndex == 0) {
            mMarketActivity.mParentCID = "";
        } else {
            if (mDatas1 != null && mDatas1.size() > mTotalIndex - 1) {
                FiltrateTotalBean.DataEntity bean = mDatas1.get(mTotalIndex - 1);
                mMarketActivity.mParentCID = bean.ParentID;
            }
        }
        mMarketActivity.resetPriceFragmentData();
    }

}
