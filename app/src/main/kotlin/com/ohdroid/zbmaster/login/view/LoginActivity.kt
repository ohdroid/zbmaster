package com.ohdroid.zbmaster.login.view

import android.os.Bundle
import com.ohdroid.zbmaster.R

/**
 * Created by ohdroid on 2016/2/25.
 */
class LoginActivity : JLoginActivity() {
    //
    //    val btnQQLogin by lazy { findViewById(R.id.btn_qq_login) }
    //    val btnWeChatLogin by lazy { findViewById(R.id.btn_wechat_login) }

    //    @Inject
    //    @PerActivity
    //    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        //        activityComponent.inject(this)//dagger2 注入

        //        presenter = activityComponent.loginPresenter()

        //        presenter = mPresenter
        //                DaggerLoginActivityComponent.builder()
        //                        .appModule(AppModule(applicationContext))
        //                        .perActivityModule(PerActivityModule(this@LoginActivity))
        //                        .loginActivityModule(LoginActivityModule())

        //        initViews()
        //
        //        initPresenter();
    }

    //    fun initViews() {
    //        btnQQLogin.setOnClickListener(mBtnOnClickListener)
    //        btnWeChatLogin.setOnClickListener(mBtnOnClickListener)
    //    }

    //    fun initPresenter() {
    //        //TODO inject by Dagger2
    //        // presenter = LoginPresenterImp()
    //        //        presenter.addContext(this@LoginActivity)
    //    }
    //
    //    //this view btn onClickListener
    //    var mBtnOnClickListener: View.OnClickListener = View.OnClickListener { view ->
    //
    //        when (view.id) {
    ////            R.id.btn_qq_login -> mPresenter.qqLogin()
    ////            R.id.btn_wechat_login -> mPresenter.weChatLogin()
    //        }
    //
    //    }


}
