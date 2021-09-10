package com.custom.base.utils

import java.io.File

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/8/30 5:03 下午
 */
object FileUtils {

    @JvmStatic
    fun createFile(path: String): Boolean{
        val file = File(path)
        if (!file.exists()) {
            return file.createNewFile()
        }
        return false
    }

    @JvmStatic
    fun writeFileFromString(file: File?,text: String?): Boolean{
        if (file != null && file.exists() && !text.isNullOrEmpty()){
            file.writeText(text)
            return true
        }
        return false
    }

    @JvmStatic
    fun readFileFromString(file: File?): String? {
        if (file != null && file.exists()){
            return file.readText()
        }
        return null
    }

    @JvmStatic
    fun copyFile(file: File?, targetPath: String?): Boolean{
        if (file != null && file.exists() && !targetPath.isNullOrEmpty()){
            val targetFile = file.copyTo(File(targetPath),true)
            if (targetFile.exists()) return true
        }
        return false
    }

}