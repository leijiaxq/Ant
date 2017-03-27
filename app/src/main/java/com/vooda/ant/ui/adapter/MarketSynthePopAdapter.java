package com.vooda.ant.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.bean.MarketSyntheBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by  leijiaxq
 * Date       2017/3/16 11:22
 * Describe
 */
public class MarketSynthePopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MarketSyntheBean> mDatas;

    public MarketSynthePopAdapter(List<MarketSyntheBean> datas) {
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_market_pop, parent, false);
        return new ViewHolderItem(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolderItem holderItem = (ViewHolderItem) holder;
        MarketSyntheBean bean = mDatas.get(position);

        holderItem.mItemTv.setText(TextUtils.isEmpty(bean.text)?"":bean.text);
        holderItem.mItemTv.setSelected(bean.flag);

        holderItem.mItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    public OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }


    static class ViewHolderItem extends RecyclerView.ViewHolder{
        @BindView(R.id.item_tv)
        TextView mItemTv;

        ViewHolderItem(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
