package com.ohdroid.zbmaster.login.presenter.imp

import android.app.Activity
import android.content.Context
import android.content.Intent
import cn.bmob.v3.BmobUser
import cn.bmob.v3.listener.OtherLoginListener
import cn.bmob.v3.listener.SaveListener
import cn.bmob.v3.listener.UpdateListener
import com.ohdroid.zbmaster.BuildConfig
import com.ohdroid.zbmaster.application.data.DataManager
import com.ohdroid.zbmaster.application.rxbus.RxBus
import com.ohdroid.zbmaster.base.view.BaseActivity
import com.ohdroid.zbmaster.login.data.LoginManager
import com.ohdroid.zbmaster.login.event.UserInfoUpdateEvent
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
class LoginPresenterImp constructor(var activity: Activity, val dataManager: DataManager, val rxBus: RxBus) : LoginPresenter {


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
                rxBus.send(UserInfoUpdateEvent())
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

            //2.QQ授权成功后，在BMOB服务器通过access_token验证
            val authInfo: BmobUser.BmobThirdUserAuth = BmobUser.BmobThirdUserAuth(
                    "qq",
                    jsonResult.getString("access_token"),
                    jsonResult.getString("expires_in"),
                    jsonResult.getString("openid"))

            BmobUser.loginWithAuthData(activity, authInfo, object : OtherLoginListener() {
                override fun onSuccess(p0: JSONObject?) {
                    println("bmob login success------->>$p0")
                    //3.QQ登录成功后获取用户的信息
                    val userInfoListener: IUiListener = object : IUiListener {
                        override fun onComplete(p0: Any?) {
                            println("user info--->>$p0")
                            //4.用户Bmob登录成功后根据QQ返回的用户信息更新Bmob的用户信息
                            updateBmobUserInfo(p0 as JSONObject)
                        }

                        override fun onCancel() {
                        }

                        override fun onError(p0: UiError?) {
                        }

                    }

                    //获取详细的用户信息
                    getQQUserInfo(jsonResult, userInfoListener)


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

    fun updateBmobUserInfo(userInfo: JSONObject) {
        val newAccountInfo: AccountInfo = AccountInfo()
        val accountInfo = BmobUser.getCurrentUser(activity, AccountInfo::class.java)
        newAccountInfo.nickName = userInfo.getString("nickname")
        newAccountInfo.photoUrl = userInfo.getString("figureurl_qq_2")
        newAccountInfo.update(activity, accountInfo.objectId, object : UpdateListener() {
            override fun onSuccess() {
//                println("bmob 用户数据更新成功")
                //发送登录成功事件，更新各个页面
                rxBus.send(UserInfoUpdateEvent())
                //退出登陆界面
                loginView?.loginSuccess()
            }

            override fun onFailure(p0: Int, p1: String?) {
                loginView?.loginFailed("$p0:$p1")
            }
        })
    }

    /**
     * qq第三方登录
     * 这里逻辑稍微有点绕，理一下：
     * 1.QQ登陆授权
     * 2.使用QQ授权返回的信息在Bmob平台上登录
     * 3.通过QQ授权反馈的信息，再向QQ请求详细的用户信息
     * 4.通过获取的客户信息更新Bmob平台的用户信息
     * 其中的2.3步可以同时请求，但是有个数据回调先后问题，这里先按层次回调，后面使用Rx重构下
     */
    override fun qqLogin() {
        dataManager.tencentManager.login(loginView?.getCurrentFragment(), "all", listener);
    }

    override fun handleQQLoginResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, listener);
        }
    }

    override fun qqQuit() {
    }

}