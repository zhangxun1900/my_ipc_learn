package cn.demo.zx_ipc_learn;

import android.app.Application;
import android.os.Process;
import android.widget.Toast;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 17:23
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/2/20.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "MyApplication:::"+Utils.getProcessName(getApplicationContext(), Process.myPid()), Toast.LENGTH_SHORT).show();
    }
}
