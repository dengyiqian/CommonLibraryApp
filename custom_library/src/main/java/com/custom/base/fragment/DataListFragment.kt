package com.custom.base.fragment

import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.custom.base.R
import com.custom.base.databinding.LayoutDataListBinding
import com.custom.base.viewmodel.BaseViewMolde
import com.custom.base.viewmodel.ListDataViewModel
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener
import org.apache.http.params.HttpParams


abstract class DataListFragment<T,VM: ListDataViewModel<T>> :
    BaseFragment<LayoutDataListBinding,ListDataViewModel<T>>(ListDataViewModel::class.java),
    OnRefreshLoadMoreListener {

    //页面恢复是否需要刷新
    private var isResumeRefresh = false

    private var pager = 1
    private var pagerSize = 10

    private val mAdapter by lazy { getImpleAdapter() }

    protected abstract fun getDataViewModel(): ListDataViewModel<T>
    protected abstract fun getHttpPatams(): Map<String,Any>
    protected abstract fun getImpleAdapter(): BaseQuickAdapter<T, BaseDataBindingHolder<ViewDataBinding>>

    override fun getContentLayout(): Int = R.layout.layout_data_list

    override fun initViews() {
        mBinding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = getImplManager()
            addItemDecoration(GridItemDecoration())
        }

        mBinding.refreshLayout.setOnRefreshLoadMoreListener(this)

        initData()
    }

    override fun initData() {
        viewModel.dataList.observe(viewLifecycleOwner, Observer<MutableList<T>> {
            if (pager == 1) {
                mAdapter.setNewInstance(it)
                mBinding.refreshLayout.finishRefresh()
            } else if (!it.isNullOrEmpty()){
                mAdapter.addData(it)
            }
            if (it.size == pagerSize){
                mBinding.refreshLayout.finishLoadMore()
            } else {
                mBinding.refreshLayout.finishRefreshWithNoMoreData()
            }
        })

        if (!isResumeRefresh){
            pager = 1
            viewModel.requestDataList(pager,pagerSize,getHttpPatams())
        }
    }

    open fun getImplManager(): RecyclerView.LayoutManager{
        return LinearLayoutManager(requireContext())
    }

    protected fun isRefresh(enable: Boolean){
        isResumeRefresh = enable
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pager = 1
        viewModel.requestDataList(pager,pagerSize,getHttpPatams())
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pager += 1
        viewModel.requestDataList(pager,pagerSize,getHttpPatams())
    }

    override fun onResume() {
        super.onResume()
        if (isResumeRefresh){
            pager = 1
            viewModel.requestDataList(pager,pagerSize,getHttpPatams())
        }
    }
}