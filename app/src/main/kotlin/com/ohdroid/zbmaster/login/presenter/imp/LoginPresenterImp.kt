package com.ohdroid.zbmaster.login.presenter.imp

import android.app.Activity
import android.content.Context
import android.content.Intent
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.OtherLoginListener
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.BuildConfig
import com.ohdroid.zbmaster.application.data.DataManager
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.login.data.LoginManager
import com.ohdroid.zbmaster.login.model.AccountInfo
import com.ohdroid.zbmaster.login.presenter.LoginPresenter
import com.ohdroid.zbmaster.login.view.LoginView
import com.ohdroid.zbmaster.login.view.RegisterView
import com.tencent.connect.UserInfo
import com.tencent.connect.auth.QQToken
import com.tencent.connect.common.Constants
import com.tencent.tauth.IUiListener
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import org.json.JSONObject

/**
 * Created by ohdroid on 2016/2/25.
 */
class LoginPresenterImp constructor(var activity: Activity, val dataManager: DataManager) : LoginPresenter {


    var loginView: LoginView? = null
    var registerView: RegisterView? = null
    override fun detachView() {
        this.loginView = null;
    }

    override fun attachView(view: LoginView) {
        this.loginView = view
    }

    override fun attachRegisterView(registerView: RegisterView?) {
        this.registerView = registerView
    }


    override fun register(accountInfo: AccountInfo) {
        dataManager.loginManger.regist(accountInfo, object : LoginManager.LoginListener {
            override fun onSuccess() {
                registerView?.registerSuccess()
            }

            override fun onFailed(state: Int, msg: String) {
                registerView?.registerFailed(state, msg)
            }

        })
    }

    override fun login(accountInfo: AccountInfo) {
        //        println("==============loginmanager====>${dataManager}")

        dataManager.loginManger.login(accountInfo, object : SaveListener() {
            override fun onFailure(p0: Int, p1: String?) {
                loginView?.loginFailed("$p0-------login failed----------$p1")
            }

            override fun onSuccess() {
                loginView?.loginSuccess()
            }
        })
    }


    val listener: IUiListener = object : IUiListener {
        override fun onComplete(p0: Any?) {
            if (p0 !is JSONObject) {
                return
            }
            val jsonResult: JSONObject = p0

            println("QQ login result -->${jsonResult.toString()}")

            //QQ登录成功后获取用户的信息
            val userInfoListener: IUiListener = object : IUiListener {
                override fun onComplete(p0: Any?) {
                    println("user info--->>$p0")
                }

                override fun onCancel() {
                }

                override fun onError(p0: UiError?) {
                }

            }

            getQQUserInfo(jsonResult, userInfoListener)

            //QQ授权成功后，在BMOB服务器通过access_token验证
            val authInfo: BmobUser.BmobThirdUserAuth = BmobUser.BmobThirdUserAuth(
                    "qq",
                    jsonResult.getString("access_token"),
                    jsonResult.getString("expires_in"),
                    jsonResult.getString("openid"))

            BmobUser.loginWithAuthData(activity, authInfo, object : OtherLoginListener() {
                override fun onSuccess(p0: JSONObject?) {
                    println("bmob login success------->>$p0")
                    //用户Bmob登录成功后根据QQ返回的用户信息更新Bmob的用户信息
                }

                override fun onFailure(p0: Int, p1: String?) {
                    println("bmob login failed------->>$p0:$p1")
                }
            })

        }

        override fun onCancel() {
        }

        override fun onError(p0: UiError?) {
            println("qq login error ${p0?.errorMessage}")
        }
    }

    fun getQQUserInfo(jsonParams: JSONObject, listener: IUiListener) {
        val qqToken = QQToken(BuildConfig.QQ_APP_ID)
        qqToken.openId = jsonParams.getString("openid")
        qqToken.setAccessToken(jsonParams.getString("access_token"), jsonParams.getString("expires_in"))
        val info: UserInfo = UserInfo(activity, qqToken);
        info.getUserInfo(listener);
    }

    /**
     * qq第三方登录
     */
    override fun qqLogin() {

        //        println("=tencent login========>>${dataManager.tencentManager}")
        //        dataManager.tencentManager.login(loginView?.getCurrentActivity(), "get_user_info", listener);
        dataManager.tencentManager.login(loginView?.getCurrentFragment(), "all", listener);
    }

    override fun handleQQLoginResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        if (requestCode == Constants.REQUEST_API) {
            if (resultCode == Constants.REQUEST_LOGIN) {
                Tencent.handleResultData(data, listener);
            }
        }
    }

    override fun qqQuit() {
    }

}