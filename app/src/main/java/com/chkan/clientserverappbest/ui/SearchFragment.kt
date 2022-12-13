package com.chkan.clientserverappbest.ui

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import com.chkan.clientserverappbest.base.BaseFragment
import com.chkan.clientserverappbest.databinding.FragmentSearchBinding
import com.chkan.clientserverappbest.ui.adapters.LoaderStateAdapter
import com.chkan.clientserverappbest.ui.adapters.SearchPageAdapter
import com.chkan.clientserverappbest.ui.vm.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        SearchPageAdapter()
    }

    override fun initUI(savedInstanceState: Bundle?) {
        initRecyclerView()
        initSearch()
    }

    private fun initSearch() {
        b.searchView.doAfterTextChanged { text ->
            viewModel.setQuery(text?.toString() ?: "")
        }
    }

    private fun initRecyclerView() {
        b.rvSearch.adapter = adapter.withLoadStateHeaderAndFooter(//add loader and error handling
            header = LoaderStateAdapter(),
            footer = LoaderStateAdapter()
        )

        //add loader for first load or refresh
        adapter.addLoadStateListener { state ->
            with(b){
                rvSearch.isVisible = state.refresh != LoadState.Loading
                progressSearch.isVisible = state.refresh == LoadState.Loading
            }
            //if(state.refresh is LoadState.Error){//can handle error here}
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.pagers.collectLatest(adapter::submitData)
            }
        }
    }
}