package cn.demo.zx_remote_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class RemoteService extends Service {
    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new RemoteBinder();
    }

    public void method(){
        System.out.println("服务端的方法。。。。。。。。。。。。");
//        Toast.makeText(this, "调用服务的数据", Toast.LENGTH_SHORT).show();
    }

    class RemoteBinder extends IService.Stub {

        @Override
        public void callMethod() throws RemoteException {
            method();
        }
    }
}
