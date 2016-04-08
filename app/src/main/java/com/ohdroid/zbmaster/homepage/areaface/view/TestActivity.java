package com.ohdroid.zbmaster.homepage.areaface.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ohdroid.zbmaster.R;
import com.ohdroid.zbmaster.application.view.ExRecycleView;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv;
    ExRecycleView exRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        exRecycleView = (ExRecycleView) findViewById(R.id.exRecycleView);
        Button btn = (Button) findViewById(R.id.btn_hide);
        btn.setOnClickListener(this);
        if (null == exRecycleView) {
            return;
        }
        exRecycleView.setLayoutManager(new LinearLayoutManager(this));

        List<String> list = new ArrayList<>();
        list.add("test");
        list.add("test");
        list.add("test");
        list.add("test");
        list.add("test");

        tv = new TextView(this);
        tv.setText("TEST");
        tv.setWidth(320);
        tv.setHeight(320);
        exRecycleView.addFootView(tv);
        exRecycleView.setAdapter(new MyAdapter(list));

    }

    @Override
    public void onClick(View v) {
//        tv.setVisibility();
        exRecycleView.removeFootView();
//        exRecycleView.getAdapter().notifyDataSetChanged();
    }

    class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<String> list = new ArrayList<>();

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_area_face, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
