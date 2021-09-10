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
import com.custom.base.expand.getClazz
import com.custom.base.viewmodel.BaseViewMolde
import java.io.Serializable

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/8/18 10:07 上午
 */
abstract class BaseFragment<VB: ViewDataBinding,VM: BaseViewMolde>: Fragment() {

    private var lastClickTime = 0L

    lateinit var mContext: Context

    lateinit var mBinding: VB

    val viewModel: VM by lazy {
        ViewModelProvider(this).get(getClazz(this,1))
    }

    protected abstract fun getContentLayout(): Int

    open fun initData(){}
    open fun initView(){}

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
//        KeyboardDismisser.useWith(requireActivity())
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


    private fun isDoubleClick(): Boolean {
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