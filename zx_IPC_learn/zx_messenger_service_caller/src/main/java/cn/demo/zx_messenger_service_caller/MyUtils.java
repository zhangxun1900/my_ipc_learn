package cn.demo.zx_messenger_service_caller;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import java.util.List;

/**
 * @author Administrator
 * @version $Rev$
 * @time ${DATA} 17:21
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDate $Date$
 * @updateDes ${TODO}
 * Created by xun on 2017/2/21.
 */

public class MyUtils {
    public static Intent getMyIntent(Context context,Intent intent){
        /**
         * 获取包管理器,通过包管理器获取符合意图intent的服务入口集合
         */
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfos = pm.queryIntentServices(intent, 0);
        if(resolveInfos==null || resolveInfos.size()!=1){
            return null;
        }

        /**
         * 获取包名和服务名
         */
        ResolveInfo resolveInfo = resolveInfos.get(0);
        String packageName = resolveInfo.serviceInfo.packageName;
        String name = resolveInfo.serviceInfo.name;

        ComponentName componentName = new ComponentName(packageName,name);

        Intent intent1 = new Intent(intent);
        intent1.setComponent(componentName);

        return intent1;
    }
}
