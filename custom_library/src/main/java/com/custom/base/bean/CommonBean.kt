package com.custom.base.bean

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/8/27 3:53 下午
 */
data class CommonBean<T>(
    val code: Int,
    val data: T?,
    var msg: String,
    val encData: String?
)