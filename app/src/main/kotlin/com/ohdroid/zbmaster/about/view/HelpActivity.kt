package com.ohdroid.zbmaster.about.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity

/**
 * Created by ohdroid on 2016/5/24.
 */
class HelpActivity : BaseActivity() {
    companion object {
        @JvmStatic fun launch(context: Context) {
            val intent: Intent = Intent(context, HelpActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }
}