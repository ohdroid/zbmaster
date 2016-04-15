package com.ohdroid.zbmaster.login.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.ohdroid.zbmaster.R
import com.ohdroid.zbmaster.application.ex.showToast
import com.ohdroid.zbmaster.base.view.BaseFragment
import com.ohdroid.zbmaster.login.model.AccountInfo
import com.ohdroid.zbmaster.login.presenter.LoginPresenter
import org.jetbrains.anko.support.v4.find

/**
 * Created by ohdroid on 2016/4/15.
 */
class RegisterFragment : BaseFragment(), RegisterView {


    val etName: EditText by lazy { find<EditText>(R.id.et_name) }
    val etPwd: EditText by lazy { find<EditText>(R.id.et_password) }
    val btnRegister: Button by lazy { find<Button>(R.id.btn_register) }

    lateinit var presenter: LoginPresenter

    companion object {
        @JvmField val TAG: String = "RegisterFragment"

        @JvmStatic fun launch(manager: FragmentManager, containerId: Int) {
            var fragment: Fragment? = null
            if (null == manager.findFragmentByTag(TAG)) {
                fragment = RegisterFragment()
                manager.beginTransaction()
                        .add(containerId, fragment)
                        .addToBackStack(null)
                        .commit()
            } else {
                fragment = manager.findFragmentByTag(TAG)
                manager.beginTransaction()
                        .show(fragment)
                        .commit()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View? = inflater?.inflate(R.layout.fragment_register_layout, container, false)
        presenter = component.loginPresenter()
        presenter.attachRegisterView(this)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnRegister.setOnClickListener(onClickListener)
    }

    val onClickListener: View.OnClickListener = object : View.OnClickListener {
        override fun onClick(v: View?) {
            if (TextUtils.isEmpty(etName.text.toString())) {
                etName.error = getString(R.string.register_name_empty)
                return
            }
            if (TextUtils.isEmpty(etPwd.text.toString())) {
                etPwd.error = getString(R.string.register_pwd_empty)
                return
            }

            //注册
            val accountInfo: AccountInfo = AccountInfo()
            accountInfo.username = etName.text.toString()
            accountInfo.setPassword(etPwd.text.toString())
            presenter.register(accountInfo)
        }


    }

    //==================================对presenter暴露的接口==================
    override fun registerSuccess() {
        showToast(getString(R.string.register_ok))
        activity.finish()
    }

    override fun registerFailed(state: Int, errorMessage: String) {
        showToast(getString(R.string.register_error, errorMessage))
    }
}