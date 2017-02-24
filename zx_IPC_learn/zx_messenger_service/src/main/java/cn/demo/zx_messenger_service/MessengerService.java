package cn.demo.zx_messenger_service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public class MessengerService extends Service {


    private class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case GlobalUtils.MSG_FROM_CLIENT:

                    /**
                     * 接收客户端发送过来的消息
                     */
                    String msg1 = msg.getData().getString("msg");
                    System.out.println("获取的数据::::::"+msg1);

                    /**
                     * 向服务的发送消息
                     */
                    Messenger client = msg.replyTo;
                    Message replyMessage = Message.obtain(null,GlobalUtils.MSG_FROM_CLIENT);
                    Bundle data = new Bundle();
                    data.putString("reply","你的消息我已收到，稍后会回复你。");
                    replyMessage.setData(data);
                    try {
                        client.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:break;
            }
        }
    }

    public MessengerService() {
    }


    /**
     * 创建与Handler相关联的Messenger对象。
     */
    private Messenger mMessenger = new Messenger(new MessengerHandler());

    /**
     * 返回mMessenger对象中的Binder
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
