package com.ohdroid.zbmaster.application.view.recycleview

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ohdroid.zbmaster.R
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

/**
 * Created by ohdroid on 2016/4/22.
 * 为带有head 和foot的RecycleView添加view
 */
class RecyclerViewAddViewHelper {

    companion object {

        /**
         * 添加单个text文本提示view并返回textView对象
         */
        fun <T : RecyclerView.ViewHolder> addSingleTextFootHintView(context: Context, listAdapterWrap: RecycleViewHeaderFooterAdapter<T>): TextView {
            val footTextView = TextView(context)
            footTextView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            footTextView.gravity = Gravity.CENTER
            val padding = context.resources.getDimensionPixelSize(R.dimen.padding_8dp)
            footTextView.setPadding(0, 0, 0, padding)
            footTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
            listAdapterWrap.addFootView(footTextView);
            return footTextView
        }

        fun <T : RecyclerView.ViewHolder> addNoNetFootView(context: Context, listAdapterWrap: RecycleViewHeaderFooterAdapter<T>, retryListener: View.OnClickListener): View {
            val noNetLayout = LayoutInflater.from(context).inflate(R.layout.no_net_work_layout, null, false)
            noNetLayout.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            noNetLayout.find<Button>(R.id.btn_retry).setOnClickListener(retryListener)
            listAdapterWrap.addFootView(noNetLayout)
            return noNetLayout
        }

        fun <T : RecyclerView.ViewHolder> addNoDataFootView(hintStr: String?, context: Context, listAdapterWrap: RecycleViewHeaderFooterAdapter<T>) {
            val footTextView = TextView(context)
            footTextView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            footTextView.gravity = Gravity.CENTER
            footTextView.textColor = context.resources.getColor(R.color.hint_text_color)
            footTextView.text = hintStr ?: context.resources.getString(R.string.hint_no_data)
            footTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f);
            listAdapterWrap.addFootView(footTextView);
        }
    }
}