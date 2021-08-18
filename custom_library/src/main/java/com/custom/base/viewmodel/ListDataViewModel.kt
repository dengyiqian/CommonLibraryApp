package com.custom.base.viewmodel

import androidx.lifecycle.MutableLiveData

/**
 *  作者 : dao sanqing
 *  描述 : 通用数据列表
 *  日期 : 208/9 10:58 上午
 */
open abstract class ListDataViewModel<T> : BaseViewMolde(){

    val dataList = MutableLiveData<MutableList<T>>()

    abstract fun requestDataList(page: Int, pageSize: Int, params: Map<String,Any>)

}