package cn.demo.zx_ipc_learn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cn.demo.zx_ipc_learn.domain.Book;
import cn.demo.zx_ipc_learn.domain.Student;
import cn.demo.zx_ipc_learn.domain.User;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view){
        Intent intent = new Intent(this,SecondActivity.class);
        Book book = new Book(0,"java");
        Student s = new Student("zhangx",true,book);
        intent.putExtra("extra_stu",s);
        startActivity(intent);
    }

    public void changeMath(View view){
        Utils.sId = 2;

    }



    public void showMath(View view){
        Toast.makeText(this, "MainActivity显示的数据："+Utils.sId, Toast.LENGTH_SHORT).show();
    }

    /**
     * 序列号
     * @param view
     */
    public void serialize(View view){

        new Thread(){
            @Override
            public void run() {
                super.run();
                User user = new User(0,"zhangx",false);
                File file = new File((Utils.TOTAL_PATH));
                if(!file.exists()){
                    file.mkdirs();
                }
                File cacheFile = new File(Utils.PATH);
                ObjectOutputStream out = null;
                try {
                    out = new ObjectOutputStream(new FileOutputStream(cacheFile));
                    out.writeObject(user);
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();


    }

    /**
     * 反序列化
     * @param view
     */
    public void unSerialize(View view){


        final File file = new File(Utils.PATH);

        new Thread(){
            @Override
            public void run() {
                super.run();
                User user = null;
                if(file.exists()){
                    ObjectInputStream in = null;
                    try {
                        in = new ObjectInputStream(new FileInputStream(file));
                        user = (User) in.readObject();
                        System.out.println(user.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }.start();

    }
}
