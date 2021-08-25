package com.custom.base

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/7/23 2:19 下午
 */
class CustomApp : Application(), ViewModelStoreOwner{

    private lateinit var viewModelStore: ViewModelStore
    private var vmFactory: Factory? = null

    override fun onCreate() {
        super.onCreate()
        viewModelStore = ViewModelStore()
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

}