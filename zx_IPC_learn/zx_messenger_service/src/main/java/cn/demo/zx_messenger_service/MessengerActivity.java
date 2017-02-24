
package cn.demo.zx_messenger_service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;

public class MessengerActivity extends AppCompatActivity {

    /**
     * 创建与Handler相关联的Messenger对象，主要用于接收消息。
     */
    private Messenger mG = new Messenger(new MessengerHandler());

    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case GlobalUtils.MSG_FROM_CLIENT:
                    System.out.println("客户端收到的数据：："+msg.getData().getString("reply"));
                    break;

                default:break;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Intent intent = new Intent(this,MessengerService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);

    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Messenger messenger = new Messenger(service);


            Message msg = Message.obtain(null,GlobalUtils.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg","这里是客户端。。。。。。。。。。");
            msg.setData(data);

            //注意此处，
            msg.replyTo = mG;
            try {
                messenger.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
