package com.ohdroid.zbmaster.application.data;

import com.ohdroid.zbmaster.login.data.LoginManager;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by ohdroid on 2016/3/14.
 *
 * 所有数据总管家
 */
public class DataManager {
    private LoginManager loginManager;
    @Inject
    public DataManager(LoginManager loginManager){
        this.loginManager = loginManager;
    }

    public LoginManager getLoginManger(){
        return this.loginManager;
    }
}
