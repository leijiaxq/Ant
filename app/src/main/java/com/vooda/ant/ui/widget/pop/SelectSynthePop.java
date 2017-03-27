package com.vooda.ant.ui.widget.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.vooda.ant.R;
import com.vooda.ant.bean.MarketSyntheBean;
import com.vooda.ant.ui.adapter.MarketSynthePopAdapter;

import java.util.List;


/**
 * Created by leijiaxq
 * Data       2016/12/29 17:58
 * Describe
 */

public class SelectSynthePop extends PopupWindow {
    private Activity               mContext;
    private List<MarketSyntheBean> mDatas;
    private RecyclerView           mRecyclerView;
    private MarketSynthePopAdapter mAdapter;

    public SelectSynthePop(Context context,List<MarketSyntheBean> datas) {
        super(context);
        this.mContext = (Activity) context;
        this.mDatas = datas;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_select_synthe, null);
        this.setContentView(view);
        this.setFocusable(true);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //        ColorDrawable colorDrawable = new ColorDrawable(0x00000000);
        //        this.setBackgroundDrawable(colorDrawable);

        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_bottom_anim);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);

        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
        params.alpha = 0.9f;
//        params.alpha = 0.2f;
        mContext.getWindow().setAttributes(params);
        this.setOnDismissListener(new poponDismissListener());


        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));

        mAdapter = new MarketSynthePopAdapter(mDatas);

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new MarketSynthePopAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                for (int i= 0;i<mDatas.size();i++) {
                    MarketSyntheBean bean = mDatas.get(i);
                    if (i == position) {
                        bean.flag = true;
                    } else {
                        bean.flag = false;
                    }
                }
                mAdapter.notifyDataSetChanged();
                dismiss();

                if (mShareListener !=null) {
                    mShareListener.onItem(position);
                }
            }
        });

            /*
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mShareListener != null) {
                    mShareListener.onItem(1);
                }
            }
        });*/


       /* mView.setOnTouchListener(new View.OnTouchListener() {
            @SuppressLint("ClickableViewAccessibility")
            public boolean onTouch(View v, MotionEvent event) {
                int height = mView.findViewById(R.id.pop_area_tv).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y > height) {
                        dismiss();
                    }
                }
                return true;
            }
        });*/


    }

    class poponDismissListener implements OnDismissListener {

        @Override
        public void onDismiss() {
            WindowManager.LayoutParams params = mContext.getWindow().getAttributes();
            params.alpha = 1f;
            mContext.getWindow().setAttributes(params);
        }

    }


    ShareListener mShareListener;

    public void setShareListener(ShareListener pShareListener) {
        mShareListener = pShareListener;
    }

    public interface ShareListener {
        void onItem(int position);
    }
}
