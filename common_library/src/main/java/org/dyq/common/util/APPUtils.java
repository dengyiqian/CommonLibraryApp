package org.dyq.common.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @Author： 邓益千
 * @Create by：   2020/6/6 10:25
 * @Description：
 */
public class APPUtils {

    /**
     * 获取版本名称
     * @return 版本名称
     */
    public static String getVersionName() {
        //获取包管理器
        PackageManager pm = Utils.mContext.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(Utils.mContext.getPackageName(), 0);
            //返回版本号
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
