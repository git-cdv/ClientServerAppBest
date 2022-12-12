package com.chkan.clientserverappbest.ui

import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.chkan.clientserverappbest.base.BaseFragment
import com.chkan.clientserverappbest.databinding.FragmentMainBinding
import com.chkan.clientserverappbest.ui.adapters.LoaderStateAdapter
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
        b.rvPassengers.adapter = adapter.withLoadStateHeaderAndFooter(//add loader and error handling
            header = LoaderStateAdapter(),
            footer = LoaderStateAdapter()
        )

        //add loader for first load or refresh
        adapter.addLoadStateListener { state ->
            with(b){
                rvPassengers.isVisible = state.refresh != LoadState.Loading
                progress.isVisible = state.refresh == LoadState.Loading
            }
            //if(state.refresh is LoadState.Error){//can handle error here}
        }

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