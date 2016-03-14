package com.ohdroid.zbmaster.login.data.bmob

import android.content.Context
import cn.bmob.v3.Bmob
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.SaveListener
import com.ohdroid.zbmaster.di.exannotation.ForApplication
import com.ohdroid.zbmaster.login.data.AccountManager
import com.ohdroid.zbmaster.login.data.LoginManager
import com.ohdroid.zbmaster.login.model.AccountInfo
import javax.inject.Inject


/**
 * Created by ohdroid on 2016/3/14.
 */
class BmobLoginManager constructor( val context: Context) : AccountManager {

    var accountInfo: AccountInfo? = null

    override fun regist(accountInfo: AccountInfo, registerListener: LoginManager.LoginListener) {
        val user: BmobUser = BmobUser()
        user.username = accountInfo.uesrName
        user.setPassword(accountInfo.password)
        user.signUp(context, object : SaveListener() {
            override fun onFailure(p0: Int, p1: String?) {
                registerListener.onFailed(p0.toString() + ":" + p1)
            }

            override fun onSuccess() {
                registerListener.onSuccess()
            }

        })
    }

    override fun login(userName: String, userPassword: String, loginListener: LoginManager.LoginListener) {
        BmobUser.loginByAccount(context, userName, userPassword, object : LogInListener<BmobUser>() {
            override fun done(p0: BmobUser?, p1: BmobException?) {
                if (p0 != null) {
                    //TODO 封装成可用账户
                    accountInfo = AccountInfo(p0.username,"");
                    loginListener.onSuccess()
                }
            }

        })
    }



    override fun getUserAccount(): AccountInfo? {
        return accountInfo
    }


}