package cn.demo.zx_messenger_service;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this,MessengerService.class);
        startService(intent);
    }

    public void onClick(View view){
        Intent intent  = new Intent(this,MessengerActivity.class);
        startActivity(intent);
    }
}
