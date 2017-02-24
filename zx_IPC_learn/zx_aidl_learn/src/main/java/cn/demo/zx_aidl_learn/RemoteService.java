package cn.demo.zx_aidl_learn;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import cn.demo.zx_aidl_learn.domain.Book;

public class RemoteService extends Service {

    /**
     * 使用CopyOnWriteArrayList的原因：
     * 因为CopyOnWriteArrayList支持并发读/写，AIDL方法是在服务端的Binder的线程池中执行的，
     * 因此当多个客户端同时连接的时候，会存在多个线程同时访问的情形，所以我们要在AIDL方法中
     * 处理线程同步，而我们这里直接使用CopyOnWriteArrayList来进行自动的线程同步。
     *
     * AIDL中能够使用的List只有ArrayList，但是这里为什么还使用它呢？
     * 因为AIDL中所支持的是抽象的List，而List只是一个接口，因此虽然服务端返回的是CopyOnWriteArrayList，
     * 但是在Binder中会按照List的规范去访问数据并最终返回一个新的ArrayList传递给客户端。还有就是类似的是
     * ConcurrentHashMap.
     */
    CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

//    CopyOnWriteArrayList<IOnNewBookArrivedListener>mListenerList = new CopyOnWriteArrayList<>();
    RemoteCallbackList<IOnNewBookArrivedListener>mListenerList = new RemoteCallbackList<>();

    AtomicBoolean mAtomicBoolean = new AtomicBoolean(true);

    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        int check = checkCallingOrSelfPermission("cn.demo.zx_aidl_learn.YANZHENG");
        if(check== PackageManager.PERMISSION_DENIED){
            return null;
        }

        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mBookList.add(new Book(1,"android"));
        mBookList.add(new Book(2,"ios学习"));

        new Thread(new ServiceWork()).start();
    }

    /**
     * 每隔5秒添加一本图书
     */
    private class ServiceWork implements Runnable{

        @Override
        public void run() {
            while (mAtomicBoolean.get()){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int bookId = mBookList.size()+1;
                Book newBook = new Book(bookId,"新Android##"+bookId);
                sendNewBookArrivedToClient(newBook);
            }
        }
    }

    /**
     * 发送新书已到的消息到客户端
     * 怎样通知呢？
     * 通过客户端在主持监听的时候传入的IOnNewBookArrivedListener对象来通知客户端
     * @param newBook
     */
    private void sendNewBookArrivedToClient(Book newBook){
        mBookList.add(newBook);
//        for(int i=0;i<mListenerList.size();i++){
        //            IOnNewBookArrivedListener listener = mListenerList.get(i);
        //            try {
        //                listener.onNewBookArrived(newBook);
        //            } catch (RemoteException e) {
        //                e.printStackTrace();
        //            }
        //        }

        int N = mListenerList.beginBroadcast();
        for(int i=0;i<N;i++){
            IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
            try {
                listener.onNewBookArrived(newBook);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mListenerList.finishBroadcast();
    }

    private Binder mBinder = new IService.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book b) throws RemoteException {
            mBookList.add(b);
        }

        /**
         * 客户端通过传入IOnNewBookArrivedListener对象来进行注册，然后此处将传入的对象保存起来
         * 以便以后想要使用它的对象的时候可以随时使用。
         * @param listener
         * @throws RemoteException
         */
        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if(!mListenerList.contains(listener)){
//                mListenerList.add(listener);
//            }
            mListenerList.register(listener);
        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {
//            if(mListenerList.contains(listener)){
//                mListenerList.remove(listener);
//            }
            mListenerList.unregister(listener);
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();


        mAtomicBoolean.set(false);
    }
}
