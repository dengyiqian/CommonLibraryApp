package com.custom.base.utils

import com.tencent.mmkv.MMKV

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/7/23 11:51 上午
 */
object SPUtil {

    private val mmkv = MMKV.defaultMMKV()

    @JvmStatic
    fun put(key:String, value: Any?){
        value?.let {
            when(it){
                is Int-> mmkv.putInt(key, it)
                is Long-> mmkv.putLong(key, it)
                is Float-> mmkv.putFloat(key, it)
                is String-> mmkv.putString(key, it)
                is Boolean-> mmkv.putBoolean(key, it)
                else -> {}
            }
        }
    }

    @JvmStatic
    fun getInt(key:String) = mmkv.decodeInt(key)

    @JvmStatic
    fun getLong(key:String) = mmkv.decodeLong(key)

    @JvmStatic
    fun getFloat(key:String) = mmkv.decodeFloat(key)

    @JvmStatic
    fun getString(key:String?) = mmkv.decodeString(key)

    @JvmStatic
    fun getBoolean(key:String) = mmkv.decodeBool(key)

    @JvmStatic
    fun remove(key:String) =  mmkv.removeValueForKey(key)

}