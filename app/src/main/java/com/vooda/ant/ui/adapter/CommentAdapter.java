package com.vooda.ant.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vooda.ant.R;
import com.vooda.ant.api.Constants;
import com.vooda.ant.bean.CommentBean;
import com.vooda.ant.bean.ProductBean;
import com.vooda.ant.utils.DataFormatUtil;
import com.vooda.ant.utils.ImageUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by  leijiaxq
 * Date       2017/3/15 11:27
 * Describe
 */
public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context                      mContext;
    private List<CommentBean.DataEntity> mDatas;
    public boolean isAllLoad = false;
    private ProductBean.DataEntity mBean;

    public CommentAdapter(Context context, List<CommentBean.DataEntity> datas, ProductBean.DataEntity bean) {
        mContext = context;
        mDatas = datas;
        mBean = bean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == Constants.TYPE_TWO) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
            return new ViewHolderType2(view);
        } else if (viewType == Constants.TYPE_FOOT) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_foot_layout, parent, false);
            return new ViewHolderFoot(view);
        } else if (viewType == Constants.TYPE_ONE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_header, parent, false);
            return new ViewHolderType1(view);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderType1) {
            setDataType1((ViewHolderType1) holder, position);
        } else if (holder instanceof ViewHolderFoot) {
            setDataFoot((ViewHolderFoot) holder);
        } else if (holder instanceof ViewHolderType2) {
            setDataType2((ViewHolderType2) holder, position);
        }
    }


    private void setDataType1(ViewHolderType1 holder, final int position) {
        if (mBean != null) {
            holder.mItemNameTv.setText(TextUtils.isEmpty(mBean.ProductName) ? "" : mBean.ProductName);
            String formatPrice = DataFormatUtil.getFormatInstance().format(mBean.Price);

            holder.mItemPriceTv.setText(formatPrice + "/" + mBean.UnitName);
            holder.mItemNumberTv.setText("销售" + mBean.CommentCount + "份");
            holder.mItemIntroductionTv.setText(TextUtils.isEmpty(mBean.Description) ? "" : mBean.Description);

            holder.mItemGoogTv.setText("好评(" + mBean.mGood + ")份");
            holder.mItemMidleTv.setText("中评(" + mBean.mMiddle + ")份");
            holder.mItemBadTv.setText("差评(" + mBean.mBad + ")份");

            int totalComment = mBean.mBad + mBean.mMiddle + mBean.mGood;
            holder.mItemCommetNumberTv.setText("商品评价(" + totalComment + ")");

            holder.mItemAddIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onItemAddClick(v, position);
                    }
                }
            });

            holder.mItemGoogTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onGoodClick(position);
                    }
                }
            });

            holder.mItemMidleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onMiddleClick(position);
                    }
                }
            });

            holder.mItemBadTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onBadClick(position);
                    }
                }
            });
        }
    }

    private void setDataType2(ViewHolderType2 holder, int position) {

        CommentBean.DataEntity bean = mDatas.get(position);
//        CommentBean.DataEntity bean = mDatas.get(position - 1);

        String headUrl = "";
        if (!TextUtils.isEmpty(bean.HeadUrl)) {
            if (bean.HeadUrl.startsWith("http")) {
                headUrl = bean.HeadUrl;
            } else {
                headUrl = Constants.BASE_URL + bean.HeadUrl;
            }
        }
        ImageUtil.getInstance().displayCricleImage(mContext, headUrl, holder.mItemIconIv);

        if ("N".equals(bean.IsIncognito)) {
            holder.mItemNameTv.setText("*****");
        } else {
            holder.mItemNameTv.setText(TextUtils.isEmpty(bean.UserName) ? "" : bean.UserName);
        }

        holder.mItemTimeTv.setText(TextUtils.isEmpty(bean.AddTime) ? "" : bean.AddTime);
        holder.mItemContentTv.setText(TextUtils.isEmpty(bean.Contents) ? "" : bean.Contents);

        setXingXing(bean.Overview, holder.mXing1, holder.mXing2, holder.mXing3, holder.mXing4, holder.mXing5);


        if (TextUtils.isEmpty(bean.ImageUrl)) {
            holder.mItemIvLayout.setVisibility(View.GONE);
        } else {
            holder.mItemIvLayout.setVisibility(View.VISIBLE);
            setImageNumber(bean.ImageUrl, holder.mItemIv1, holder.mItemIv2, holder.mItemIv3, holder.mItemIv4, holder.mItemIv5, holder.mItemIv6, position);
        }


    }

    private void setImageNumber(String imageUrl, ImageView itemIv1, ImageView itemIv2, ImageView itemIv3, ImageView itemIv4, ImageView itemIv5, ImageView itemIv6, final int position) {
        String[] split = imageUrl.split(",");
        List<ImageView> list = new ArrayList<>();
        list.add(itemIv1);
        list.add(itemIv2);
        list.add(itemIv3);
        list.add(itemIv4);
        list.add(itemIv5);
        list.add(itemIv6);
        for (int i = 0; i < list.size(); i++) {
            ImageView iv = list.get(i);
            if (i < split.length) {
                iv.setVisibility(View.VISIBLE);
                String url = "";
                if (split[i].startsWith("http")) {
                    url = split[i];
                } else {
                    url = Constants.BASE_URL + split[i];
                }
                ImageUtil.getInstance().displayImage(mContext, url, iv);

                final int position2 = i;
                iv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onItemImageViewClick(v, position, position2);
                        }
                    }
                });

            } else {
                iv.setVisibility(View.GONE);
            }
        }


    }

    private void setXingXing(double overview, ImageView xing1, ImageView xing2, ImageView xing3, ImageView xing4, ImageView xing5) {
        double n = overview / 3;
        xing1.setImageResource(R.drawable.xing);
        xing2.setImageResource(R.drawable.xing);
        xing3.setImageResource(R.drawable.xing);
        xing4.setImageResource(R.drawable.xing);
        xing5.setImageResource(R.drawable.xing);
        if (n >= 5) {
        } else if (n > 4 && n < 5) {
            xing5.setImageResource(R.drawable.xing1);
        } else if (n == 4) {
            xing5.setImageResource(R.drawable.xing2);
        } else if (n > 3 && n < 4) {
            xing5.setImageResource(R.drawable.xing2);
            xing4.setImageResource(R.drawable.xing1);
        } else if (n == 3) {
            xing5.setImageResource(R.drawable.xing2);
            xing4.setImageResource(R.drawable.xing2);
        } else if (n > 2 && n < 3) {
            xing5.setImageResource(R.drawable.xing2);
            xing4.setImageResource(R.drawable.xing2);
            xing3.setImageResource(R.drawable.xing1);
        } else if (n == 2) {
            xing5.setImageResource(R.drawable.xing2);
            xing4.setImageResource(R.drawable.xing2);
            xing3.setImageResource(R.drawable.xing2);
        } else if (n > 1 && n < 2) {
            xing5.setImageResource(R.drawable.xing2);
            xing4.setImageResource(R.drawable.xing2);
            xing3.setImageResource(R.drawable.xing2);
            xing2.setImageResource(R.drawable.xing1);
        } else if (n == 1) {
            xing5.setImageResource(R.drawable.xing2);
            xing4.setImageResource(R.drawable.xing2);
            xing3.setImageResource(R.drawable.xing2);
            xing2.setImageResource(R.drawable.xing2);
        } else if (n > 0 && n < 1) {
            xing1.setImageResource(R.drawable.xing1);
            xing2.setImageResource(R.drawable.xing2);
            xing3.setImageResource(R.drawable.xing2);
            xing4.setImageResource(R.drawable.xing2);
            xing5.setImageResource(R.drawable.xing2);
        } else {
            xing1.setImageResource(R.drawable.xing2);
            xing2.setImageResource(R.drawable.xing2);
            xing3.setImageResource(R.drawable.xing2);
            xing4.setImageResource(R.drawable.xing2);
            xing5.setImageResource(R.drawable.xing2);
        }


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
        //                return mDatas == null ? 1 : mDatas.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
       /* if (position + 1 == getItemCount()) {
            return Constants.TYPE_FOOT;
        }
        return Constants.TYPE_TWO;*/


        if (position + 1 == getItemCount()) {
            return Constants.TYPE_FOOT;
        }/* else if (position == 0) {
            return Constants.TYPE_ONE;
        }*/
        return Constants.TYPE_TWO;
    }

    public OnItemClickListener mListener;

    public interface OnItemClickListener {

        void onItemAddClick(View view, int position);

        void onGoodClick(int position);

        void onMiddleClick(int position);

        void onBadClick(int position);


        void onItemImageViewClick(View view, int position, int position2);

    }

    public void setOnItemClickListener(OnItemClickListener l) {
        mListener = l;
    }


    static class ViewHolderType1 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_introduction_tv)
        TextView  mItemIntroductionTv;
        @BindView(R.id.item_name_tv)
        TextView  mItemNameTv;
        @BindView(R.id.item_price_tv)
        TextView  mItemPriceTv;
        @BindView(R.id.item_number_tv)
        TextView  mItemNumberTv;
        @BindView(R.id.item_add_iv)
        ImageView mItemAddIv;
        @BindView(R.id.item_commet_number_tv)
        TextView  mItemCommetNumberTv;
        @BindView(R.id.item_goog_tv)
        TextView  mItemGoogTv;
        @BindView(R.id.item_midle_tv)
        TextView  mItemMidleTv;
        @BindView(R.id.item_bad_tv)
        TextView  mItemBadTv;

        ViewHolderType1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderType2 extends RecyclerView.ViewHolder {
        @BindView(R.id.item_icon_iv)
        ImageView    mItemIconIv;
        @BindView(R.id.item_name_tv)
        TextView     mItemNameTv;
        @BindView(R.id.item_time_tv)
        TextView     mItemTimeTv;
        @BindView(R.id.xing1)
        ImageView    mXing1;
        @BindView(R.id.xing2)
        ImageView    mXing2;
        @BindView(R.id.xing3)
        ImageView    mXing3;
        @BindView(R.id.xing4)
        ImageView    mXing4;
        @BindView(R.id.xing5)
        ImageView    mXing5;
        @BindView(R.id.item_content_tv)
        TextView     mItemContentTv;
        @BindView(R.id.item_iv1)
        ImageView    mItemIv1;
        @BindView(R.id.item_iv2)
        ImageView    mItemIv2;
        @BindView(R.id.item_iv3)
        ImageView    mItemIv3;
        @BindView(R.id.item_iv4)
        ImageView    mItemIv4;
        @BindView(R.id.item_iv5)
        ImageView    mItemIv5;
        @BindView(R.id.item_iv6)
        ImageView    mItemIv6;
        @BindView(R.id.item_iv_layout)
        LinearLayout mItemIvLayout;

        ViewHolderType2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderFoot extends RecyclerView.ViewHolder {
        @BindView(R.id.item_foot_pb)
        ProgressBar mItemFootPb;
        @BindView(R.id.item_foot_tv)
        TextView    mItemFootTv;

        ViewHolderFoot(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
