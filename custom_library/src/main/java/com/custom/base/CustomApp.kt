package com.custom.base

import android.graphics.Point
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.CoilUtils
import okhttp3.OkHttpClient
import kotlin.math.pow
import kotlin.math.sqrt

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/8/23 2:19 下午
 */
abstract class CustomApp : MultiDexApplication(), ViewModelStoreOwner, ImageLoaderFactory {

    private var vmFactory: Factory? = null
    private lateinit var viewModelStore: ViewModelStore

    protected abstract fun initApp()

    override fun onCreate() {
        super.onCreate()
        initApp()
        viewModelStore = ViewModelStore()
        if (BuildConfig.DEBUG) printInfo()
    }

    override fun getViewModelStore(): ViewModelStore {
        return viewModelStore
    }

    fun getViewModelProvider(): ViewModelProvider {
        return ViewModelProvider(this,getViewModelFactory())
    }

    fun getViewModelFactory(): Factory {
        if (vmFactory == null){
            vmFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(this)
        }
        return vmFactory as Factory
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .cache(CoilUtils.createDefaultCache(this))
                    .build()
            }.build()
    }

    private fun printInfo(){
        val point = Point()
        val metrics = DisplayMetrics()
        val wm = getSystemService(WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getMetrics(metrics);
        display.getRealSize(point);
        val x: Double = (point.x / metrics.xdpi).toDouble().pow(2.0)
        val y: Double = (point.y / metrics.ydpi).toDouble().pow(2.0)
        Log.i("tag", "宽：" + metrics.widthPixels
                + "  高：" + metrics.heightPixels
                + "  DPI: " + metrics.densityDpi
                + "  物理尺寸：" + sqrt(x + y)
        )
    }
}