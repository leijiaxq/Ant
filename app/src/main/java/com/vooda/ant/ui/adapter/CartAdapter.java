package com.vooda.ant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.bean.CartBean;
import com.vooda.ant.utils.DataFormatUtil;
import com.vooda.ant.utils.ImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 11:27
 * Describe
 */
public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<CartBean.DataBean> mDatas;
    public boolean isAllLoad = false;


    public CartAdapter(Context context, List<CartBean.DataBean> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Constants.TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
            return new ViewHolderType1(view);
        } else if (viewType == Constants.TYPE_FOOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_layout, parent, false);
            return new ViewHolderFoot(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderType1) {
            setDataType1((ViewHolderType1) holder, position);
        } else if (holder instanceof ViewHolderFoot) {
            setDataFoot((ViewHolderFoot) holder);
        }
    }


    private void setDataType1(ViewHolderType1 holder, final int position) {
        CartBean.DataBean bean = mDatas.get(position);

        String imageUrl = "";
        if (!TextUtils.isEmpty(bean.ImageUrl)) {
            if (bean.ImageUrl.startsWith("http")) {
                imageUrl = bean.ImageUrl;
            } else {
                imageUrl = Constants.BASE_URL + bean.ImageUrl;
            }
        }
        ImageUtil.getInstance().displayImage(mContext, imageUrl, holder.mItemIconIv);

        holder.mItemNameTv.setText(TextUtils.isEmpty(bean.ProductName) ? "" : bean.ProductName);

        String formatPrice = DataFormatUtil.getFormatInstance().format(bean.Price);
        holder.mItemPriceTv.setText(formatPrice + "/" + bean.UnitName);


        holder.mItemSelectTv.setSelected(bean.mIsSelected);


    }

    //设置底部foot
    private void setDataFoot(ViewHolderFoot holder) {
        if (isAllLoad) {
            holder.mItemFootPb.setVisibility(View.GONE);
            holder.mItemFootTv.setText("所有数据已经加载完");
        } else {
            holder.mItemFootPb.setVisibility(View.VISIBLE);
            holder.mItemFootTv.setText("正在加载...");
        }
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return Constants.TYPE_FOOT;
        }
        return Constants.TYPE_ONE;
    }

    public OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemAddClick(View view, int position);

        void onItemClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }


    static class ViewHolderFoot extends RecyclerView.ViewHolder {
        @BindView(R.id.item_foot_pb)
        ProgressBar mItemFootPb;
        @BindView(R.id.item_foot_tv)
        TextView mItemFootTv;

        ViewHolderFoot(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_select_tv)
        TextView mItemSelectTv;
        @BindView(R.id.item_icon_iv)
        ImageView mItemIconIv;
        @BindView(R.id.item_name_tv)
        TextView mItemNameTv;
        @BindView(R.id.item_price_tv)
        TextView mItemPriceTv;
        @BindView(R.id.item_reduce_iv)
        ImageView mItemReduceIv;
        @BindView(R.id.item_number_et)
        EditText mItemNumberEt;
        @BindView(R.id.item_add_iv)
        ImageView mItemAddIv;

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
