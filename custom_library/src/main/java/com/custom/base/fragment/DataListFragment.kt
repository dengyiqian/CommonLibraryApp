package com.custom.base.fragment

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.custom.base.R
import com.custom.base.databinding.LayoutDataListBinding
import com.custom.base.viewmodel.ListDataViewModel
import com.custom.base.widget.GridItemDecoration
import com.scwang.smart.refresh.layout.api.RefreshLayout
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener


abstract class DataListFragment<T,VM: ListDataViewModel<T>> : CommonFragment<LayoutDataListBinding,VM>(), OnRefreshLoadMoreListener,
    OnItemClickListener {

    //页面恢复是否需要刷新
    private var isResumeRefresh = false

    private var pager = 1
    private var pagerSize = 10

    private val mAdapter by lazy {
        getImpleAdapter().apply{
            setOnItemClickListener(this@DataListFragment)
        }
    }

    protected abstract fun getImpleAdapter(): BaseQuickAdapter<T, *>

    override fun getContentLayout(): Int = R.layout.layout_data_list

    override fun initView() {
        mBinding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = getImplManager()
            addItemDecoration(GridItemDecoration())
        }

        mBinding.refreshLayout.setOnRefreshLoadMoreListener(this)
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
            getData()
        }
    }

    open fun getData(){
        pager = 1
        viewModel.requestDataList(pager,pagerSize)
    }

    open fun getImplManager(): RecyclerView.LayoutManager{
        return LinearLayoutManager(requireContext())
    }

    protected fun isRefresh(enable: Boolean){
        isResumeRefresh = enable
    }

    override fun onRefresh(refreshLayout: RefreshLayout) {
        pager = 1
        viewModel.requestDataList(pager,pagerSize)
    }

    override fun onLoadMore(refreshLayout: RefreshLayout) {
        pager += 1
        viewModel.requestDataList(pager,pagerSize)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    override fun onResume() {
        super.onResume()
        if (isResumeRefresh){
            getData()
        }
    }
}