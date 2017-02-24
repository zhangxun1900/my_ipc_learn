package cn.demo.zx_remote_service_caller;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cn.demo.zx_remote_service.IService;

public class MainActivity extends AppCompatActivity {

    IService mIService = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction("cn.demo.zx_remote_service.YCFW");
        Intent intent1 = new Intent(ConstantUtils.getMyIntent(this,intent));
        bindService(intent1,conn,BIND_AUTO_CREATE);
    }

    public void onClick(View view){

        try {
            mIService.callMethod();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            System.out.println("服务端异常结束。。。。。。。。");
            mIService.asBinder().unlinkToDeath(mDeathRecipient,0);
            mIService = null;
            //TODO 重新绑定服务
        }
    };

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIService = IService.Stub.asInterface(service);
            /**
             * 设置死亡代理
             */
            try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
