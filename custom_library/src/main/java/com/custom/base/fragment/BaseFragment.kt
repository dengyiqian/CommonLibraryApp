package com.custom.base.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.custom.base.viewmodel.BaseViewMolde
import java.io.Serializable

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/8/18 10:07 上午
 */
abstract class BaseFragment<VB: ViewDataBinding,VM: BaseViewMolde>(clazz: Class<VM>): Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var mBinding: VB
    private var lastClickTime = 0L
    protected val viewModel by lazy {
        ViewModelProvider(this).get(clazz)
    }

    protected abstract fun getContentLayout(): Int

    protected abstract fun initData()
    protected abstract fun initView()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(layoutInflater,getContentLayout(),container,false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    fun startActivity(key: String = "", value: Any? = null, clazz: Class<*>){
        if (!isDoubleClick()){
            val intent = Intent(mContext,clazz)
            if (key.isNotEmpty() && value != null){
                when(value){
                    is Int-> intent.putExtra(key, value)
                    is String-> intent.putExtra(key, value)
                    is Boolean-> intent.putExtra(key, value)
                    is Parcelable -> intent.putExtra(key, value)
                    is Serializable -> intent.putExtra(key, value)
                }
            }
            super.startActivity(intent)
        }
    }


    fun isDoubleClick(): Boolean {
        val time = System.currentTimeMillis()
        val timeD = time - lastClickTime
        if (timeD in 1..1000) {
            return true
        }
        lastClickTime = time
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding.unbind()
    }
}