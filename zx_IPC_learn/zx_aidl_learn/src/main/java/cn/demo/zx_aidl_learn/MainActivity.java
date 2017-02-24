package cn.demo.zx_aidl_learn;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import cn.demo.zx_aidl_learn.domain.Book;

public class MainActivity extends AppCompatActivity {

    private IService mService = null;

    /**
     * 当新书到的时候，服务端会回调客户端的IOnNewBookArrivedListener对象的onNewBookArrived方法，
     * 但是这个方法是在客户端的Binder线程池中执行的，因此，为了便于进行UI操作，我们需要一个Handler可以将
     * 其切换到客户端的主线程中去执行。
     */

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case 0:
                    Toast.makeText(MainActivity.this, ""+msg.obj, Toast.LENGTH_SHORT).show();
                    break;

                default:
                super.handleMessage(msg);
            }
        }
    };

    private Intent intent = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = new Intent(this,RemoteService.class);
        startService(intent);

        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    /**
     * 查询图书
     * @param v
     */
    public void findBook(View v){
        try {
            List<Book> bookList = mService.getBookList();
            if(bookList!=null){
                for(Book b : bookList){
                    System.out.println("书籍::"+b.toString());
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }


    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IService.Stub.asInterface(service);
            try {
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                /**
                 * 注册对新书的监控
                 */
                mService.registerListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    private IOnNewBookArrivedListener listener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {
            System.out.println("新到的图书：："+newBook.toString());
            mHandler.obtainMessage(0,newBook).sendToTarget();
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            mService.asBinder().unlinkToDeath(mDeathRecipient,0);
            mService = null;
            //重新连接
            bindService(intent,conn,BIND_AUTO_CREATE);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mService!=null || mService.asBinder().isBinderAlive()){
            try {
                mService.unregisterListener(listener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        unbindService(conn);

    }
}
