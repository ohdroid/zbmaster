package com.ohdroid.zbmaster.application.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by ohdroid on 2016/4/8.
 * 带有head 和 foot view 的recycleview
 * <p>
 * 模仿listview的实现可以添加头部和尾部view
 * <p>
 * 实现原理.1.对原始adapter进行包装,返回的各个参数进行修改
 * 2.添加headview或footview时通知recycleview进行绘制
 */
public class ExRecycleView extends RecyclerView {

    ArrayList<RecycleViewWrapAdapter.HeaderViewHolder> mHeaderViewHolders = new ArrayList<>();
    ArrayList<RecycleViewWrapAdapter.FooterViewHolder> mFooterViewHolders = new ArrayList<>();

    public ExRecycleView(Context context) {
        super(context);
    }

    public ExRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ExRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private RecyclerView.Adapter mAdapter;

    public void addHeaderView(View view) {
        mHeaderViewHolders.clear();
        mHeaderViewHolders.add(new RecycleViewWrapAdapter.HeaderViewHolder(view));
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecycleViewWrapAdapter)) {
                mAdapter = new RecycleViewWrapAdapter(mHeaderViewHolders, mFooterViewHolders, mAdapter);
            }
        }
    }

    public void addFootView(View view) {
        mFooterViewHolders.clear();
        mFooterViewHolders.add(new RecycleViewWrapAdapter.FooterViewHolder(view));
        if (mAdapter != null) {
            if (!(mAdapter instanceof RecycleViewWrapAdapter)) {
                mAdapter = new RecycleViewWrapAdapter(mHeaderViewHolders, mFooterViewHolders, mAdapter);
            }
        }
    }

    public void removeFootView() {
        mFooterViewHolders.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if (mHeaderViewHolders.isEmpty() && mFooterViewHolders.isEmpty()) {
            super.setAdapter(adapter);
        } else {
            adapter = new RecycleViewWrapAdapter(mHeaderViewHolders, mFooterViewHolders, adapter);
            super.setAdapter(adapter);
        }
        mAdapter = adapter;
    }


}
