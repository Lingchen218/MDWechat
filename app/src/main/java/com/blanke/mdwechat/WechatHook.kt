package com.blanke.mdwechat

import android.R.attr.classLoader
import com.blanke.mdwechat.Common.isVXPEnv
import com.blanke.mdwechat.config.HookConfig
import com.blanke.mdwechat.config.ViewTreeConfig
import com.blanke.mdwechat.config.WxVersionConfig
import com.blanke.mdwechat.hookers.*
import com.blanke.mdwechat.hookers.base.Hooker
import com.blanke.mdwechat.hookers.base.HookerProvider
import com.blanke.mdwechat.util.LogUtil
import com.blanke.mdwechat.util.LogUtil.log
import com.blanke.mdwechat.util.waitInvoke
import com.joshcai.mdwechat.BuildConfig
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import java.io.File


class WechatHook : IXposedHookLoadPackage {

    @Throws(Throwable::class)
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        try {
            log(lpparam.packageName)
            if(lpparam.packageName.equals("com.finaccel.android")){


                var appsealingcall = XposedHelpers.findClass("com.inka.appsealing.AppSealingReport", lpparam.classLoader)




                // java.io.FileSystem
                // createFileExclusively
                XposedBridge.hookAllMethods(appsealingcall,"sendReportFile",object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val msg = param.args[1] as File
                        LogUtil.log("xxxxd beforeHookedMethod showAlertDialog " + msg.absolutePath    )
                        if(msg.exists() && msg.canRead()){
                            LogUtil.log("before msg.readText " + msg.readText())
                        }
                    }

                    override fun afterHookedMethod(param: MethodHookParam) {
                        val msg = param.args[1] as File

                        // val msg1 = param.args[1] as String
                        // val actionBar = param.thisObject as View

                        // var res = param.result as String
                        //LogUtil.log("msg.exists " + msg.exists())
                        //LogUtil.log("msg.canRead " + msg.canRead())

                        //LogUtil.log("xxxxd afterHookedMethod showAlertDialog " + msg.absolutePath    )
                    }

                })

                XposedBridge.hookAllMethods(appsealingcall,"sendReport",object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val msg = param.args[1] as String
                        val file = File(msg)
                        if(file.exists()){
                            if(! file.isDirectory()){

                                LogUtil.log("xxxxd beforeHookedMethod sendReport " + file.readText()    )
                                // printstackTrace()
                            }
                        }

//                        if(msg.exists() && msg.canRead()){
//                            LogUtil.log("before msg.readText " + msg.readText())
//                        }
                    }

                    override fun afterHookedMethod(param: MethodHookParam) {
                        val msg = param.args[1] as String

                        // val msg1 = param.args[1] as String
                        // val actionBar = param.thisObject as View

                        // var res = param.result as String
                        //LogUtil.log("msg.exists " + msg.exists())
                        //LogUtil.log("msg.canRead " + msg.canRead())

                        //LogUtil.log("xxxxd afterHookedMethod showAlertDialog " + msg.absolutePath    )
                    }

                })

                XposedBridge.hookAllMethods(appsealingcall,"requestSendReportFolder",object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        val file = param.args[0] as File
                        //val file = File(msg)
                        if(file.exists()){
                            if(! file.isDirectory()){

                                LogUtil.log("xxxxd beforeHookedMethod requestSendReportFolder " + file.readText()    )
                                // printstackTrace()
                            }
                        }

//                        if(msg.exists() && msg.canRead()){
//                            LogUtil.log("before msg.readText " + msg.readText())
//                        }
                    }

                    override fun afterHookedMethod(param: MethodHookParam) {
                        val msg = param.args[0] as File

                        // val msg1 = param.args[1] as String
                        // val actionBar = param.thisObject as View

                        // var res = param.result as String
                        //LogUtil.log("msg.exists " + msg.exists())
                        //LogUtil.log("msg.canRead " + msg.canRead())

                        //LogUtil.log("xxxxd afterHookedMethod showAlertDialog " + msg.absolutePath    )
                    }

                })


                XposedBridge.hookAllMethods(appsealingcall,"prepareReportFolders",object : XC_MethodHook() {
                    override fun beforeHookedMethod(param: MethodHookParam) {
                        // val file = param.args[0] as File
                        val file = param.thisObject
                        val currentProcessName = XposedHelpers.getObjectField(file,"currentProcessName") as String
                        val patt =  "/data/user/0/com.finaccel.android/.sealing_reports/com_finaccel_android"
                        val files = String.format("%s/%s",patt,currentProcessName)
                        LogUtil.log("ddfdfd beforeHookedMethod prepareReportFolders " + files)

                    }

                    override fun afterHookedMethod(param: MethodHookParam) {
                        // val msg = param.args[0] as File

                        // val msg1 = param.args[1] as String
                        // val actionBar = param.thisObject as View

                        // var res = param.result as String
                        //LogUtil.log("msg.exists " + msg.exists())
                        //LogUtil.log("msg.canRead " + msg.canRead())

                        //LogUtil.log("xxxxd afterHookedMethod showAlertDialog " + msg.absolutePath    )
                    }

                })

//                var appsealingcall2 = XposedHelpers.findClass("com.inka.appsealing.AppSealingReport$ReportObserver", lpparam.classLoader)
//                XposedHelpers.findAndHookConstructor(appsealingcall2,String::class.java,object : XC_MethodHook() {
//                    override fun beforeHookedMethod(param: MethodHookParam) {
//                         val file = param.args[0] as String
//                        LogUtil.log("file findAndHookConstructor" + file)
////                       val file = param.thisObject
//
//                    }
//
//                    override fun afterHookedMethod(param: MethodHookParam) {
//                        // val msg = param.args[0] as File
//                    }
//
//                })
//
            }



            if (!(lpparam.packageName.contains("com.tencent") && lpparam.packageName.contains("mm")))
                return
            // 暂时不 hook 小程序
            if (lpparam.processName.contains(":")) {
                return
            }
            WeChatHelper.initPrefs()
            if (!HookConfig.is_hook_switch) {
                log("模块总开关已关闭")
                return
            }
            log("模块加载中...")
            val preloadHooker = LauncherUIHooker.launcherLifeHooker
            val hookers = mutableListOf(
                    StatusBarHooker,
                    ActionBarHooker,
                    LauncherUIHooker,
                    AvatarHooker,
                    ListViewHooker,
                    ConversationHooker,
                    ContactHooker,
                    DiscoverHooker,
                    SettingsHooker,
                    SchemeHooker,
                    LogHooker,
                    NightModeHooker
            )
//            region test
//            log("Hookers 总数: ${hookers.count()}")
//            val asd = HookConfig.debug_config_text.split(" ")
//            for (i in asd[3].toInt() downTo asd[2].toInt()) {
//                hookers.removeAt(i)
//            }
//            for (i in asd[1].toInt() downTo asd[0].toInt()) {
//                hookers.removeAt(i)
//            }
//            log("激活的 Hookers 数量: ${hookers.count()}，分别为：")
//            hookers.forEach {
//                log(it::class.java.name)
//            }
            // LogUtil.logStackTraces()
//            //endregion

            if ((!isVXPEnv) && (HookConfig.is_hook_debug || HookConfig.is_hook_debug2)) {
                hookers.add(0, DebugHooker)
            }
            hookMain(lpparam, preloadHooker, hookers)
        } catch (e: Throwable) {
            log(e)
        }
    }

    private fun hookMain(lpparam: XC_LoadPackage.LoadPackageParam, preloadHooker: Hooker, plugins: List<HookerProvider>) {
        enableHookers(listOf(ContextHooker))
        WechatGlobal.init(lpparam)

        try {
            WechatGlobal.wxVersionConfig = WxVersionConfig.loadConfig(WechatGlobal.wxVersion!!.toString())
            LogUtil.log("config load success")
            preloadHooker.hook()
            LogUtil.log(" hook success")
            ViewTreeConfig.set(WechatGlobal.wxVersion!!)
        } catch (e: Exception) {
            waitInvoke(100, true,
                    { Objects.Main.context != null },
                    {
//                        LogUtil.toast("无法读取配置文件，请开启微信的存储权限后，打开 mdwechat 生成本机微信配置文件。", true)
                        LogUtil.toast("无法读取配置文件，请开启微信的存储权限后，打开 mdwechat 生成本机微信配置文件。", true)
                    })
            log("${WechatGlobal.wxVersion} 配置文件不存在或解析失败")
            return
        }
        log("wechat version=" + WechatGlobal.wxVersion
                + ",processName=" + lpparam.processName
                + ",isVXPEnv = " + isVXPEnv
                + ",MDWechat version=" + BuildConfig.VERSION_NAME)

        if (HookConfig.is_fix_play) {
            //todo 等待其他hookers加载
            waitInvoke(1, true, { WechatGlobal.preloaded }, { enableHookers(plugins) })
        } else {
            enableHookers(plugins)
        }
    }
    fun printstackTrace(){
        val stackTrace = Thread.currentThread().stackTrace
        for (element in stackTrace) {
            //println(element)
            LogUtil.log("beforeHookedMethod stackTrace" + element.toString())
        }
    }
    fun enableHookers(plugins: List<HookerProvider>) {
        plugins.forEach { provider ->
            provider.provideStaticHookers()?.forEach { hooker ->
                if (!hooker.hasHooked) {
                    hooker.hook()
                    hooker.hasHooked = true
                }
            }
        }
        log("模块加载成功")
    }
}

