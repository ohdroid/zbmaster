package com.ohdroid.zbmaster.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.base.view.BaseActivity

/**
 * Created by ohdroid on 2016/2/25.
 */
class LoginActivity : BaseActivity() {


    companion object {
        @JvmStatic fun launch(context: Context) {
            val intent: Intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        LoginFragment.launch(supportFragmentManager, R.id.fragment_container)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
    }
}
