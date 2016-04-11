package com.ohdroid.zbmaster.homepage.areaface.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import org.jetbrains.anko.find
import java.util.*

/**
 * Created by ohdroid on 2016/4/11.
 */
class Main3Activity : BaseActivity() {

    val rv: RecyclerView by lazy { find<RecyclerView>(R.id.rv) }
    val btn: Button by lazy { find<Button>(R.id.btn_add) }

    lateinit var datas: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)


        rv.layoutManager = LinearLayoutManager(this)

        datas = ArrayList()
        datas.add("test1")
        datas.add("test2")
        datas.add("test3")
        datas.add("test4")
        datas.add("test5")
        datas.add("test6")
        datas.add("test7")
        datas.add("test8")
        datas.add("test9")
        datas.add("test10")
        datas.add("test11")
        datas.add("test12")
        rv.adapter = MyAdapter(datas)
        rv.itemAnimator.addDuration = 1000;
        btn.setOnClickListener({
            println("${datas.size}:<<======before add")

            datas.add(1, "add${(Math.random() * 100).toInt()}")
            rv.adapter.notifyItemInserted(1)
        })
    }

    class MyAdapter(var data: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        fun add(str: String) {
            data.add(1, str)
            notifyItemInserted(1)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
            val view: View = LayoutInflater.from(parent?.context).inflate(R.layout.item_test, parent, false)
            return object : RecyclerView.ViewHolder(view) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int, payloads: MutableList<Any>?) {
            super.onBindViewHolder(holder, position, payloads)
            holder?.itemView?.find<TextView>(R.id.tv_name)?.text = data[position]
        }


    }
}