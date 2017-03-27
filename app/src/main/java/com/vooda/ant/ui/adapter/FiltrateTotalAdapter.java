package com.vooda.ant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.bean.FiltrateTotalBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 10:12
 * Describe
 */

public class FiltrateTotalAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context                 mContext;
    private List<FiltrateTotalBean.DataEntity> mDatas;

    //“全部”是否被点击，用于显示背景及文字颜色
    private boolean mFlag = true;

    public FiltrateTotalAdapter(Context context, List<FiltrateTotalBean.DataEntity> datas) {
        mDatas = datas;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case Constants.TYPE_ONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filtrate_total, parent, false);
                return new ViewHolderType1(view);
            case Constants.TYPE_TWO:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filtrate_total, parent, false);
                return new ViewHolderType2(view);
            default:
                throw new RuntimeException("there is no type that matches the type " +
                        viewType + " + make sure your using types correctly");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderType1) {
            setDataType1((ViewHolderType1) holder, position);
        } else if (holder instanceof ViewHolderType2) {
            setDataType2((ViewHolderType2) holder, position);
        }
    }

    private void setDataType1(ViewHolderType1 holder, final int position) {
        holder.mItemTv.setText("全部");
        holder.mItemTv.setSelected(mFlag);

        holder.mItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener !=null) {
                    mListener.onHeadItemClick(position);
                }
            }
        });

    }

    private void setDataType2(ViewHolderType2 holder, final int position) {
        FiltrateTotalBean.DataEntity bean = mDatas.get(position - 1);

        holder.mItemTv.setText(TextUtils.isEmpty(bean.ClassName)?"":bean.ClassName);
        holder.mItemTv.setSelected(bean.mFlag);

        holder.mItemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener !=null) {
                    mListener.onItemClick(position);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mDatas == null ? 1 : mDatas.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return Constants.TYPE_ONE;
        }
        return Constants.TYPE_TWO;
    }

    public  OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onHeadItemClick(int position);
        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }



    static class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv)
        TextView mItemTv;

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderType2 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_tv)
        TextView mItemTv;

        ViewHolderType2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setTotalState(boolean flag) {
     this.mFlag = flag;
    }

}
