package com.ohdroid.zbmaster.application.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by ohdroid on 2016/4/9.
 * <p>
 * 能够添加hear和foot的recycleViewAdapter
 */
public class RecycleViewHeaderFooterAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public static final int VIEW_TYPE_HEAD = 100;
    public static final int VIEW_TYPE_FOOT = 101;


    ArrayList<View> mHeaderViewHolders;
    ArrayList<View> mFooterViewHolders;

    static final ArrayList<View> EMPTY_HEAD_LIST =
            new ArrayList<>();
    static final ArrayList<View> EMPTY_FOOT_LIST =
            new ArrayList<>();

    private RecyclerView.Adapter<VH> mAdapter;

    private int mCurrentPosition = 0;

    public RecycleViewHeaderFooterAdapter(RecyclerView.Adapter<VH> adapter) {
        this(null, null, adapter);
    }

    public RecycleViewHeaderFooterAdapter(ArrayList<View> mHeaderViewHolders,
                                          ArrayList<View> mFooterViewHolders,
                                          RecyclerView.Adapter<VH> adapter) {
        mAdapter = adapter;
        mAdapter.registerAdapterDataObserver(mDataObserver);

        if (mHeaderViewHolders == null) {
            this.mHeaderViewHolders = EMPTY_HEAD_LIST;
        } else {
            this.mHeaderViewHolders = mHeaderViewHolders;
        }

        if (mFooterViewHolders == null) {
            this.mFooterViewHolders = EMPTY_FOOT_LIST;
        } else {
            this.mFooterViewHolders = mFooterViewHolders;
        }
    }

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = getHeadersCount();
            notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }
    };

    public int getHeadersCount() {
        return mHeaderViewHolders.size();
    }

    public int getFootersCount() {
        return mFooterViewHolders.size();
    }

    public void addHeaderView(View view) {
        mHeaderViewHolders.clear();
        mHeaderViewHolders.add(view);
        notifyDataSetChanged();
    }

    public void addFootView(View view) {
        mFooterViewHolders.clear();
        mFooterViewHolders.add(view);
        notifyDataSetChanged();
    }


    public void removeFootView() {
        mFooterViewHolders.clear();
        mAdapter.notifyDataSetChanged();
    }

    public void removeHeadView() {
        mHeaderViewHolders.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_HEAD) {
            return (VH) new RecyclerView.ViewHolder(mHeaderViewHolders.get(0)) {
            };//如果要改成添加多种类型的headview这里需要在headviewhodler中添加type，这里先这样实现
        } else if (viewType == VIEW_TYPE_FOOT) {
            return (VH) new RecyclerView.ViewHolder(mFooterViewHolders.get(0)) {
            };
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return;
        }
        int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                mAdapter.onBindViewHolder(holder, adjPosition);
                return;
            }
        }
    }

    @Override
    public int getItemCount() {

        if (mAdapter != null) {
            return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
        } else {
            return getHeadersCount() + getFootersCount();
        }
    }

    @Override
    public int getItemViewType(int position) {

        mCurrentPosition = position;
        int numHeaders = getHeadersCount();
        if (position < numHeaders) {
            return VIEW_TYPE_HEAD;//头部类型
        }
        int adjPosition = position - numHeaders;
        int adapterCount = 0;
        if (mAdapter != null) {
            adapterCount = mAdapter.getItemCount();
            if (adjPosition < adapterCount) {
                return mAdapter.getItemViewType(adjPosition);
            }
        }
        return VIEW_TYPE_FOOT;//尾部类型
    }


    public RecyclerView.Adapter getWrapedAdapter() {
        return mAdapter;
    }
}
