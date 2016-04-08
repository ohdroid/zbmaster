package com.ohdroid.zbmaster.application.view;

import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.WrapperListAdapter;

import java.util.ArrayList;

/**
 * Created by ohdroid on 2016/4/8.
 */
public class RecycleViewWrapAdapter extends RecyclerView.Adapter {

    public static final int VIEW_TYPE_HEAD = 100;
    public static final int VIEW_TYPE_FOOT = 101;


    ArrayList<HeaderViewHolder> mHeaderViewHolders;
    ArrayList<FooterViewHolder> mFooterViewHolders;

    static final ArrayList<HeaderViewHolder> EMPTY_HEAD_LIST =
            new ArrayList<>();

    static final ArrayList<FooterViewHolder> EMPTY_FOOT_LIST =
            new ArrayList<>();

    private RecyclerView.Adapter mAdapter;

    private int mCurrentPosition = 0;

    public RecycleViewWrapAdapter(ArrayList<HeaderViewHolder> mHeaderViewHolders,
                                  ArrayList<FooterViewHolder> mFooterViewHolders,
                                  RecyclerView.Adapter adapter) {
        mAdapter = adapter;

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

    public int getHeadersCount() {
        return mHeaderViewHolders.size();
    }

    public int getFootersCount() {
        return mFooterViewHolders.size();
    }

//    @Override
//    public boolean areAllItemsEnabled() {
//        return false;
//    }

//    @Override
//    public boolean isEnabled(int position) {
//        return false;
//    }
//
//    @Override
//    public void registerDataSetObserver(DataSetObserver observer) {
//        getWrappedAdapter().registerDataSetObserver(observer);
//    }
//
//    @Override
//    public void unregisterDataSetObserver(DataSetObserver observer) {
//        getWrappedAdapter().unregisterDataSetObserver(observer);
//    }
//
//    @Override
//    public int getCount() {
//        return 0;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return null;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return null;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 0;
//    }

//    @Override
//    public boolean isEmpty() {
//        return false;
//    }

//    @Override
//    public ListAdapter getWrappedAdapter() {
//        return null;
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println("====viewtype===============>" + viewType);

        if (viewType == VIEW_TYPE_HEAD) {
            return new HeaderViewHolder(mHeaderViewHolders.get(0).itemView);//如果要改成添加多种类型的headview这里需要在headviewhodler中添加type，这里先这样实现
        } else if (viewType == VIEW_TYPE_FOOT) {
            return new HeaderViewHolder(mFooterViewHolders.get(0).itemView);
        }
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

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


//    public RecyclerView.Adapter getAdapter() {
//        return mAdapter;
//    }

    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
