package com.custom.base.utils

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/7/23 2:10 下午
 */
@SuppressLint("StaticFieldLeak")
object AppUtils {

    lateinit var mContext: Context

    /**
     * 获取版本名称
     * @return 版本名称
     */
    @JvmStatic
    val versionName: String?
        get() {
            //获取包管理器
            val pm = mContext.packageManager
            //获取包信息
            try {
                val packageInfo = pm.getPackageInfo(mContext.packageName, 0)
                //返回版本号
                return packageInfo.versionName
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }
            return null
        }

    /**启动第三方应用 */
    fun getAppOpenIntentByPackageName(context: Context, packageName: String) {
        var mainAct: String? = null
        val pkgMag = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        intent.flags = Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_NEW_TASK
        val list = pkgMag.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES)
        for (i in list.indices) {
            val info = list[i]
            if (info.activityInfo.packageName == packageName) {
                mainAct = info.activityInfo.name
                break
            }
        }
        intent.component = ComponentName(packageName, mainAct!!)
        context.startActivity(intent)
    }

}