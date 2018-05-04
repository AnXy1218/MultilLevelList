package com.example.multilevellist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tvRecyclerView,R.id.tvTreeAdapter})
    public void onClick(View view){
        Toast.makeText(MainActivity.this,"点击",Toast.LENGTH_SHORT).show();
        switch (view.getId()){
            case R.id.tvRecyclerView:
                Intent reIntent = new Intent(this,MultiLevelActivity.class);
                reIntent.putExtra("type",1);
                startActivity(reIntent);
                break;
            case R.id.tvTreeAdapter:
                Intent treeIntent = new Intent(this,MultiLevelActivity.class);
                treeIntent.putExtra("type",2);
                startActivity(treeIntent);
                break;
        }
    }
}
