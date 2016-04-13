package com.ohdroid.zbmaster.login.data.bmob

import android.content.Context
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.application.di.exannotation.ForApplication
import com.ohdroid.zbmaster.login.data.AccountManager
import com.ohdroid.zbmaster.login.data.LoginManager
import com.ohdroid.zbmaster.login.model.AccountInfo
import javax.inject.Inject


/**
 * Created by ohdroid on 2016/3/14.
 */
class BmobLoginManager constructor(val context: Context) : AccountManager {

    var userInfo: AccountInfo? = null

    override fun regist(accountInfo: AccountInfo, registerListener: LoginManager.LoginListener) {
        accountInfo.signUp(context, object : SaveListener() {
            override fun onFailure(p0: Int, p1: String?) {
                registerListener.onFailed(p0.toString() + ":" + p1)
            }

            override fun onSuccess() {
                registerListener.onSuccess()
            }

        })
    }

    override fun login(accountInfo: AccountInfo, saveListener: SaveListener) {
        accountInfo.login(context, object : SaveListener() {
            override fun onSuccess() {
                userInfo = accountInfo
                println("user info=============>>${userInfo?.objectId}")
                saveListener.onSuccess()
            }

            override fun onFailure(p0: Int, p1: String?) {
                userInfo = null
                saveListener.onFailure(p0, p1)
            }

        })
        //        BmobUser.loginByAccount(context, userName, userPassword, object : LogInListener<AccountInfo>() {
        //            override fun done(p0: AccountInfo?, p1: BmobException?) {
        //                if (p0 != null) {
        //                    //TODO 封装成可用账户
        //                    accountInfo = p0
        //                    loginListener.onSuccess()
        //                }
        //            }
        //
        //        })
    }


    override fun getUserAccount(): AccountInfo? {
        println("get   user info=============>>${userInfo?.objectId}")
        return userInfo
    }


}