package com.ohdroid.zbmaster.application.ex

import android.app.Fragment
import android.widget.Toast
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.login.view.LoginFragment

/**
 * Created by ohdroid on 2016/3/14.
 */
fun BaseFragment.showToast(msg: String) {
    Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
}
