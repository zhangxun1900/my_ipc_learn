package cn.demo.zx_ipc_learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.demo.zx_ipc_learn.domain.Student;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);

        Student extra_stu = getIntent().getParcelableExtra("extra_stu");
        Toast.makeText(this, extra_stu.toString(), Toast.LENGTH_SHORT).show();

    }



    public void onClick2(View view){
        Intent intent = new Intent(this,ThirdActivity.class);
        startActivity(intent);
    }

    public void changeMath2(View view){
        Toast.makeText(this, "MainActivity显示的数据："+Utils.sId, Toast.LENGTH_SHORT).show();
    }
}
