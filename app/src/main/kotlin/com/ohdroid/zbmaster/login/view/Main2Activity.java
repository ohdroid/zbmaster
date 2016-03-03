package com.ohdroid.zbmaster.login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ohdroid.zbmaster.R;
import com.ohdroid.zbmaster.di.AppModule;
import com.ohdroid.zbmaster.di.PerActivityModule;
import com.ohdroid.zbmaster.login.di.DaggerLoginActivityComponent;
import com.ohdroid.zbmaster.login.di.LoginActivityModule;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

//        DaggerLoginActivityComponent.builder()
//                .appModule(new AppModule(getApplicationContext()))
//                .perActivityModule(new PerActivityModule(this))
//                .loginActivityModule(new LoginActivityModule());
    }
}
