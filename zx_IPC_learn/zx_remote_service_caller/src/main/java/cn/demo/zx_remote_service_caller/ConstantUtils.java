package cn.demo.zx_remote_service_caller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 16:57
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/2/21.
 */

public class ConstantUtils {
    public static Intent getMyIntent(Context cxt,Intent intent){

        /**
         * 获取包管理器，通过包管理器获取所有满足intent意图的服务入口信息集合
         */
        PackageManager pm = cxt.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentServices(intent, 0);

        if(resolveInfos==null || resolveInfos.size()!=1){
            return null;
        }

        ResolveInfo resolveInfo = resolveInfos.get(0);
        String packageName = resolveInfo.serviceInfo.packageName;
        String name = resolveInfo.serviceInfo.name;


        Intent intent1 = new Intent(intent);

        ComponentName component = new ComponentName(packageName,name);
        intent1.setComponent(component);

        return intent1;
    }
}
