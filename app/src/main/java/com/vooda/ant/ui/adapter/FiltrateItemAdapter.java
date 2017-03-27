package com.vooda.ant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.bean.FiltrateItemBean;
import com.vooda.ant.utils.ImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 11:27
 * Describe
 */
public class FiltrateItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context                           mContext;
    private List<FiltrateItemBean.DataEntity> mDatas;


    public FiltrateItemAdapter(Context context, List<FiltrateItemBean.DataEntity> datas) {
        mContext = context;
        mDatas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_filtrate_item, parent, false);

        return new ViewHolderType1(view);

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderType1) {
            setDataType1((ViewHolderType1) holder, position);
        }
    }

    private void setDataType1(ViewHolderType1 holder, final int position) {
        FiltrateItemBean.DataEntity bean = mDatas.get(position);
        ImageUtil.getInstance().displayImage(mContext, Constants.BASE_URL + bean.ImageUrl, holder.mItemIv);
        holder.mItemTv.setText(TextUtils.isEmpty(bean.ClassName)?"":bean.ClassName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener !=null ) {
                    mListener.onItemClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }


    public  OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemClick(int position);

    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }


    static class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_iv)
        ImageView mItemIv;
        @BindView(R.id.item_tv)
        TextView  mItemTv;

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
