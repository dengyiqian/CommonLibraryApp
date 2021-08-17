package com.custom.base.utils

import com.tencent.mmkv.MMKV

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/7/23 11:51 上午
 */
object SPUtil {

    private val mmkv = MMKV.defaultMMKV()

    fun put(key:String, value: Any){
        when(value){
            is Int-> mmkv.putInt(key, value)
            is Long-> mmkv.putLong(key, value)
            is Float-> mmkv.putFloat(key, value)
            is String-> mmkv.putString(key, value)
            is Boolean-> mmkv.putBoolean(key, value)
        }
    }

    fun getInt(key:String) = mmkv.decodeInt(key)

    fun getLong(key:String) = mmkv.decodeLong(key)

    fun getFloat(key:String) = mmkv.decodeFloat(key)

    fun getString(key:String) = mmkv.decodeString(key)

    fun getBoolean(key:String) = mmkv.decodeBool(key)

    fun remove(key:String) =  mmkv.removeValueForKey(key)

}