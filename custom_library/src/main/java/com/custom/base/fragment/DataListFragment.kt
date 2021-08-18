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
import org.apache.http.params.HttpParams


abstract class DataListFragment<T,VM: ListDataViewModel<T>> :
    BaseFragment<LayoutDataListBinding,ListDataViewModel<T>>(ListDataViewModel::class.java) {

    //页面恢复是否需要刷新
    private var isResumeRefresh = false

    private var pager = 1
    private var pagerSize = 10
    
    companion object {
        const val POST = "post"
        const val GET = "get"
    }

    private val mAdapter by lazy { getImpleAdapter() }

    protected val viewModel by lazy { getDataViewModel() }

    override fun setFragmentView(): Int = R.layout.layout_data_list

    protected abstract fun getDataViewModel(): ListDataViewModel<T>
    protected abstract fun getHttpPatams(): HttpParams
    protected abstract fun getImpleAdapter(): BaseQuickAdapter<T, BaseDataBindingHolder<ViewDataBinding>>

    override fun initViews() {
        mBinding.recyclerView.apply {
            adapter = mAdapter
            layoutManager = getImplManager()
            addItemDecoration(GridItemDecoration())
        }

        binding.smartLayout.setOnRefreshLoadMoreListener(this)

        initData()
    }

    override fun initData() {
        viewModel.dataList.observe(viewLifecycleOwner, Observer<MutableList<T>> {
            mBinding.smartLayout.closeHeaderOrFooter()
            if (pager == 1) {
                mAdapter.setNewInstance(it)
            } else {
                mAdapter.addData(it)
            }
            if (it.size < pagerSize){
                mBinding.smartLayout.finishRefreshWithNoMoreData()
            } else {
                mBinding.smartLayout.finishLoadMore()
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

    open fun setRequestType(): String = POST

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