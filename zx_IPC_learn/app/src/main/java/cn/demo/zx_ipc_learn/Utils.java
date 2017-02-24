package cn.demo.zx_ipc_learn;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;

import java.util.List;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 17:16
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/2/20.
 */

public class Utils {
    public static int sId = 1;

    public static final String TOTAL_PATH = Environment.getExternalStorageDirectory().getPath()+"/ipc/";
    public static final String PATH = TOTAL_PATH+"user.txt";

    public static String getProcessName(Context cxt,int pid){
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = am.getRunningAppProcesses();
        if(list==null){
            return null;
        }else{
            for(ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list){
                if(runningAppProcessInfo.pid==pid){
                    return runningAppProcessInfo.processName;
                }
            }
        }

        return  null;
    }
}
