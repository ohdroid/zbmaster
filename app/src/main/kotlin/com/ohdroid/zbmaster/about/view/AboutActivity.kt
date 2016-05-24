package com.ohdroid.zbmaster.about.view

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import org.jetbrains.anko.find
import java.util.*

class AboutActivity : BaseActivity() {

    val mAboutViewPager: ViewPager by lazy { find<ViewPager>(R.id.about_content) }


    companion object {
        @JvmStatic fun launch(context: Context) {
            val intent: Intent = Intent(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
    }

    val mViews: MutableList<View> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

//        mAboutViewPager.adapter = ViewPager.

        //初始化views
        val oneList: Array<String> = resources.getStringArray(R.array.coder_one)
        val twoList: Array<String> = resources.getStringArray(R.array.coder_two)
        val threeList: Array<String> = resources.getStringArray(R.array.designer)
        addViewPagerItemMain(oneList, R.mipmap.cat_one)
        addViewPagerItemMain(twoList, R.mipmap.cat_two)
        addViewPagerItemMain(threeList, R.mipmap.cat_third)

        //显示view
        mAboutViewPager.adapter = AboutViewPagerAdapter(mViews)
    }

    fun addViewPagerItemMain(itemContent: Array<String>, imageId: Int) {
        val view = layoutInflater.inflate(R.layout.about_view_item_main, null)
        view.find<ImageView>(R.id.about_photo).setImageResource(imageId)
        view.find<TextView>(R.id.about_name).text = itemContent[0]
        view.find<TextView>(R.id.about_nick_name).text = itemContent[1]
        view.find<TextView>(R.id.about_position).text = itemContent[2]
        view.find<TextView>(R.id.about_qq).text = itemContent[3]
        view.find<TextView>(R.id.about_description).text = itemContent[4]

        mViews.add(view)
    }

//    data class AboutContent(val type: Int, val name: String, val position: String, val description: String, val contact: String)

    inner class AboutViewPagerAdapter(val viewList: MutableList<View>) : PagerAdapter() {

        override fun isViewFromObject(view: View?, `object`: Any?): Boolean {
            return view == `object`
        }

        override fun getCount(): Int {
            return viewList.size
        }

        override fun instantiateItem(container: ViewGroup?, position: Int): Any? {
            container?.addView(viewList[position])
            return viewList[position]
        }

        override fun getItemPosition(`object`: Any?): Int {
            return super.getItemPosition(`object`)
        }

        override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
            container?.removeView(viewList[position])
        }
    }
}
