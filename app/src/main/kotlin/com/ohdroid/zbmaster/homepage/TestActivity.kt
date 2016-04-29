package com.ohdroid.zbmaster.homepage

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.view.recycleview.RecycleViewHeaderFooterAdapter
import com.ohdroid.zbmaster.application.view.recycleview.RecyclerViewAddViewHelper
import com.ohdroid.zbmaster.base.view.BaseActivity
import org.jetbrains.anko.find
import java.util.*

/**
 * Created by ohdroid on 2016/4/29.
 */
class TestActivity : BaseActivity() {
    val rvTest by lazy { find<RecyclerView>(R.id.rv_test) }
    val btnOne by lazy { find<Button>(R.id.btn_one) }
    val btnTwo by lazy { find<Button>(R.id.btn_two) }
    val btnThree by lazy { find<Button>(R.id.btn_three) }

    var myAdapter: MyAdapter? = null
    var wrapAdapter: RecycleViewHeaderFooterAdapter<MyViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test2)

        btnOne.setOnClickListener(btnOnClickLitener)
        btnTwo.setOnClickListener(btnOnClickLitener)
        btnThree.setOnClickListener(btnOnClickLitener)

        rvTest.layoutManager = LinearLayoutManager(this)
        val list = ArrayList<String>()
        list.add("test1")
        list.add("test2")

        myAdapter = MyAdapter(list)

        wrapAdapter = RecycleViewHeaderFooterAdapter<MyViewHolder>(myAdapter)

        rvTest.adapter = wrapAdapter
    }

    val btnOnClickLitener: View.OnClickListener = View.OnClickListener { v ->
        when (v?.id) {
            R.id.btn_one -> {
                addFootView()
            }
            R.id.btn_two -> {
                test()
            }
            R.id.btn_three -> test3()
        }
    }

    fun test3() {
        //        wrapAdapter?.setDataState(RecycleViewHeaderFooterAdapter.STATE_NO_MORE_DATA, this@TestActivity)

        println("view count:${wrapAdapter?.itemCount}:${wrapAdapter?.footersCount}")
        //                RecyclerViewAddViewHelper.addNoDataFootView("test+${Random().nextInt()}", this@TestActivity, wrapAdapter!!)
        //        //        myAdapter?.notifyDataSetChanged()
        //        wrapAdapter?.removeAllFootView()
        //        println("view count2:${wrapAdapter?.itemCount}:${wrapAdapter?.footersCount}")
        //
        //        rvTest.post({
        //            println("delay post")
        //            //            RecyclerViewAddViewHelper.addNoDataFootView("test+${Random().nextInt()}", this@TestActivity, wrapAdapter!!)
        //            wrapAdapter?.setDataState(RecycleViewHeaderFooterAdapter.STATE_NO_MORE_DATA, this@TestActivity)
        //            println("view count2:${wrapAdapter?.itemCount}:${wrapAdapter?.footersCount}")
        //        })
    }

    fun test() {
        //        myAdapter!!.data.removeAt(0)
        //        wrapAdapter?.removeAllFootView()
        //        myAdapter?.notifyDataSetChanged()

        rvTest.post({
            println("delay post")
            //            RecyclerViewAddViewHelper.addNoDataFootView("test+${Random().nextInt()}", this@TestActivity, wrapAdapter!!)
            //            wrapAdapter?.setDataState(RecycleViewHeaderFooterAdapter.STATE_NO_MORE_DATA, this@TestActivity)
            println("view count2:${wrapAdapter?.itemCount}:${wrapAdapter?.footersCount}")
        })
    }

    fun addFootView() {
        println("view count:${wrapAdapter?.itemCount}:${wrapAdapter?.footersCount}")
        RecyclerViewAddViewHelper.addNoDataFootView("test+${Random().nextInt()}", this@TestActivity, wrapAdapter!!)
        //        myAdapter?.notifyDataSetChanged()
        wrapAdapter?.removeAllFootView()
        println("view count2:${wrapAdapter?.itemCount}:${wrapAdapter?.footersCount}")

        //        wrapAdapter?.setDataState(RecycleViewHeaderFooterAdapter.STATE_NO_MORE_DATA, this@TestActivity)
        println("view count2:${wrapAdapter?.itemCount}:${wrapAdapter?.footersCount}")

    }

    class MyAdapter(var data: MutableList<String>) : RecyclerView.Adapter<MyViewHolder>() {
        override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {
            println("on bind view ")
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder? {
            println("~~~~~~~~create inside view holder~~~~~~~" + viewType)

            val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_area_face, parent, false)
            return MyViewHolder(view)
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }


    }

    class MyViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

}