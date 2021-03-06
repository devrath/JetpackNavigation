package com.demo.code.paging.usingRemoteAndLocalSource.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.demo.code.R
import com.demo.code.base.BaseActivity
import com.demo.code.databinding.ActivityPagingFromLocalRemoteApiBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingFromLocalRemoteApiActivity : BaseActivity() {

    // Binding: View references
    private lateinit var binding: ActivityPagingFromLocalRemoteApiBinding

    // Adapter: List of items
    private val adapter = RemoteApiAdapter()

    // viewModel reference
    private val localRemoteApiViewModel: LocalRemoteApiViewModel by lazy {
        ViewModelProvider(this).get(LocalRemoteApiViewModel.thisClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityPagingFromLocalRemoteApiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViews()
        fetchPosts()
    }

    private fun fetchPosts() {
        lifecycleScope.launch {

            localRemoteApiViewModel.fetchPosts().collectLatest { pagingData ->
                // We get the new data from the flow - We publish the new data to adapter
                adapter.submitData(pagingData)
            }
        }
    }

    private fun setupViews() {
        binding.apply {
            // Adapter: List of items
            rvPosts.adapter = adapter
            // Adapters: Header and Footer item
            rvPosts.adapter = adapter.withLoadStateHeaderAndFooter(
                // Header
                header = LocalRemoteApiLoadingAdapter { adapter.retry() },
                // Footer
                footer = LocalRemoteApiLoadingAdapter { adapter.retry() }
            )
        }
    }
}