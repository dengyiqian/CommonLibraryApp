package com.custom.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.custom.base.expand.getClazz
import com.custom.base.viewmodel.BaseViewMolde
import java.io.Serializable

/**
 *  作者 : dao sanqing
 *  描述 : description
 *  日期 : 2021/7/23 11:50 上午
 */
abstract class ToolbarActivity<VB: ViewDataBinding,VM: BaseViewMolde> : AppCompatActivity(){

    protected lateinit var mContext: Context
    protected lateinit var mBinding: VB

    private var lastClickTime = 0L

    val viewModel: VM by lazy {
        ViewModelProvider(this).get(getClazz(this,1))
    }

    protected abstract fun settingTitle(): CharSequence?
    protected abstract fun getContentLayout(): Int

    open fun initData(){}
    open fun initView(){}

    private lateinit var parentView: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        initToolBar()
        setContentView(getContentLayout())
        initData()
        initView()
//        KeyboardDismisser.useWith(this)
    }

    override fun setContentView(layoutResID: Int) {
        mBinding = DataBindingUtil.inflate(layoutInflater,layoutResID,parentView,true)
        mBinding.lifecycleOwner = this
        setContentView(parentView)
    }

    private fun initToolBar(){
        parentView = LinearLayout(mContext).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(-1,-1)
        }
        val rootView = findViewById<ViewGroup>(android.R.id.content)
        rootView.removeAllViews()
        rootView.addView(parentView)

        val toolbar = layoutInflater.inflate(R.layout.layout_base_toolbar,parentView,false) as Toolbar
        parentView.addView(toolbar)
        toolbar.findViewById<TextView>(R.id.toolbarTitle).text = settingTitle()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    open fun startActivity(key: String = "", value: Any? = null, clazz: Class<*>){
        if (!isDoubleClick()){
            val intent = Intent(mContext,clazz)
            if (key.isNotEmpty() && value != null){
                when(value){
                    is Int-> intent.putExtra(key, value)
                    is String-> intent.putExtra(key, value)
                    is Boolean-> intent.putExtra(key, value)
                    is Parcelable-> intent.putExtra(key, value)
                    is Serializable-> intent.putExtra(key, value)
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

    override fun onDestroy() {
        super.onDestroy()
        mBinding.unbind()
    }

}