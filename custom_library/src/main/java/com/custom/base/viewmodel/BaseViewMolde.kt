package com.custom.base.viewmodel

import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import rxhttp.awaitResult
import rxhttp.toClass
import rxhttp.toOkResponse
import rxhttp.wrapper.param.RxHttp

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/7/23 7:30 下午
 */
open class BaseViewMolde : ViewModel() {

    open suspend fun request(url: String, params: ArrayMap<String,Any>, callback:(String)-> Unit){
        RxHttp.postForm(url,params)
            .toClass<ViewModel>()
            .awaitResult {
                callback.invoke("")
            }.onFailure {

            }
    }

}