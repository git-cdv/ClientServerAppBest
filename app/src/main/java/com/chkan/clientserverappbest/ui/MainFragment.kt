package com.chkan.clientserverappbest.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.chkan.clientserverappbest.base.BaseFragment
import com.chkan.clientserverappbest.databinding.FragmentMainBinding
import com.chkan.clientserverappbest.ui.adapters.PageAdapter
import com.chkan.clientserverappbest.ui.vm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @author Dmytro Chkan on 01.12.2022.
 */
@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PageAdapter()
    }

    override fun initUI(savedInstanceState: Bundle?) {
        initViewModel()
        b.rvPassengers.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.listForPaging.collectLatest(adapter::submitData)
            }
        }
    }

    private fun initViewModel() = with(viewModel){
        observe(lifecycleScope,list){
            Log.d("CHKAN", "LIST: $it")
        }
    }
}