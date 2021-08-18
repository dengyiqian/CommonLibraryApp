package com.custom.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.custom.base.viewmodel.BaseViewMolde

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/8/18 10:07 上午
 */
abstract class BaseFragment<VB: ViewDataBinding,VM: BaseViewMolde>(clazz: Class<VM>): Fragment() {

    protected lateinit var mContext: Context
    protected lateinit var mBinding: VB

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

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding.unbind()
    }
}