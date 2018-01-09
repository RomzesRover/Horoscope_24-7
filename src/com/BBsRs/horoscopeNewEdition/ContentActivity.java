package com.BBsRs.horoscopeNewEdition;

import android.os.Bundle;

import com.BBsRs.horoscopeNewEdition.Base.BaseActivity;



public class ContentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        
        getSupportActionBar().setIcon(R.drawable.ic_menu);
    }
    
    @Override
    public void onResume() {
        super.onResume();
        setTitle("Hello");
    }
}
