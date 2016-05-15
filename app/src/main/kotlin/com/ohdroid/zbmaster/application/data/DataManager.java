package com.ohdroid.zbmaster.application.data;

import com.ohdroid.zbmaster.facesync.data.FaceSyncManager;
import com.ohdroid.zbmaster.login.data.LoginManager;
import com.tencent.tauth.Tencent;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by ohdroid on 2016/3/14.
 * <p/>
 */
@Singleton
public class DataManager {
    private LoginManager loginManager;
    private FaceSyncManager faceSyncManager;
    private Tencent tencentManager;

    @Inject
    public DataManager(LoginManager loginManager, FaceSyncManager faceSyncManager, Tencent tencentManager) {
        this.loginManager = loginManager;
        this.faceSyncManager = faceSyncManager;
        this.tencentManager = tencentManager;
        System.out.println("===========data manager init==================");
    }

    public LoginManager getLoginManger() {
        return this.loginManager;
    }

    public FaceSyncManager getFaceSyncManager() {
        return this.faceSyncManager;
    }

    public Tencent getTencentManager() {
        return tencentManager;
    }
}
