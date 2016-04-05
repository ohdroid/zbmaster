package com.ohdroid.zbmaster.facesync;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ohdroid.zbmaster.application.di.ActivityComponent;
import com.ohdroid.zbmaster.application.di.ActivityModule;
import com.ohdroid.zbmaster.application.di.ApplicationModule;
import com.ohdroid.zbmaster.application.di.DaggerActivityComponent;
import com.ohdroid.zbmaster.base.view.BaseFragment;

/**
 * Created by ohdroid on 2016/3/22.
 */
public class JFaceSyncFragment extends BaseFragment {

    private ActivityComponent activityComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .applicationModule(new ApplicationModule(getActivity().getApplication()))
                .activityModule(new ActivityModule(getActivity()))
                .build();

    }
}
