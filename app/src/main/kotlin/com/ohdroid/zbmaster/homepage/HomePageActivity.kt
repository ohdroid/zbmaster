package com.ohdroid.zbmaster.homepage

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.utils.QiniuUtils
import okhttp3.*
import org.jetbrains.anko.find
import java.io.IOException

/**
 * Created by ohdroid on 2016/4/4.
 */
class HomePageActivity : BaseActivity() {

    val tv: TextView by lazy { find<TextView>(R.id.tv) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        //        getFileList()

        //        uploadFile();
//        QiniuUtils.getInstance().uploadFile();

    }


    fun getFileList() {
        val key = "ohdroid:image/gif/zl_xz.jpg"
        val base64EncodeUri = QiniuUtils.getBaseStr(key)

        val encodedEntryURI: String = "/stat/{$base64EncodeUri}"
        val token: String = QiniuUtils.getManagerAuth(encodedEntryURI)

        tv.text = token
        Log.d("FILES", token)

        val okhttp: OkHttpClient = OkHttpClient()
        val requestBuilder: Request.Builder = Request.Builder()
        requestBuilder.url("http://rs.qiniu.com{$encodedEntryURI}")
        requestBuilder.addHeader("Content-type", "application/x-www-form-urlencoded")
        requestBuilder.addHeader("Authorization", "$token")
        //        requestBuilder.post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), "test"))
        okhttp.newCall(requestBuilder.build())
        okhttp.newCall(requestBuilder.build()).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println(e?.message + "<<error>>")
            }

            override fun onResponse(call: Call?, response: Response?) {
                println(response?.body().toString() + "<<body>>")
            }
        })

    }

}