package com.custom.base.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel


/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/7/23 7:30 下午
 */
open class BaseViewMolde : ViewModel(), LifecycleOwner {

//    open fun <T>postRequest(url: String, params: Map<String,Any>, javaBean:Class<T>, success:(T)-> Unit){
//        lifecycleScope.launch {
//            RxHttp.postCustomJson(url)
//                .addAll(params)
//                .toEntity<javaBean::class>()
//                .awaitResult {
//                    success.invoke(it)
//                }.onFailure {
//
//                }
//        }
//    }

    override fun getLifecycle(): Lifecycle {
        return LifecycleRegistry(this)
    }

}