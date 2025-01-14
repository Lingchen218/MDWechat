package com.blanke.mdwechat

import android.content.Context
import com.blanke.mdwechat.config.WxVersionConfig
import com.blanke.mdwechat.util.LogUtil
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage

object WechatGlobal {
    //play版等部分微信需要提前识别classLoader
    var preloaded = false
//    var hookersLoaded=false

    @Volatile
    var wxVersion: Version? = null

    @Volatile
    var wxPackageName: String = ""

    @Volatile
    lateinit var wxLoader: ClassLoader
    lateinit var wxClasses: List<String>
    lateinit var wxVersionConfig: WxVersionConfig

    @JvmStatic
    fun init(lpparam: XC_LoadPackage.LoadPackageParam) {
        LogUtil.log("init ddddd")
        wxPackageName = lpparam.packageName
        LogUtil.log("init wxPackageName "+ wxPackageName)
        val context = XposedHelpers.callMethod(
                XposedHelpers.callStaticMethod(XposedHelpers.findClass("android.app.ActivityThread", null),
                        "currentActivityThread"), "getSystemContext") as Context
        LogUtil.log("XposedHelpers.callMethod "+ context.toString())
        wxVersion = Version(context.packageManager.getPackageInfo(wxPackageName, 0)?.versionName
                ?: "")
        LogUtil.log("Version(context.pa "+ wxVersion)
        wxLoader = lpparam.classLoader
    }

    fun <T> wxLazy(initializer: () -> T?): Lazy<T> {
        return wxLazy("", initializer)
    }

    fun <T> wxLazy(name: String, initializer: () -> T?): Lazy<T> {
        return lazy(LazyThreadSafetyMode.PUBLICATION) {
            val res = initializer()
            if (res == null) {
                LogUtil.log("$name == null ")
            }
            res!!
        }
    }
}