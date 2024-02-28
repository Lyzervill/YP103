package com.btpit.up103

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.btpit.up103.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter {
            viewModel.likeById(it.id)
        }
        binding.container2.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }


    }

    class ViewModelLazy<VM : ViewModel>(
        private val viewModelClass: KClass<VM>,
        private val storeProducer: () -> ViewModelStore,
        private val factoryProducer: () -> ViewModelProvider.Factory
    ) : Lazy<VM> {
        private var cached: VM? = null

        override val value: VM
            get() {
                val viewModel = cached
                return if (viewModel == null) {
                    val factory = factoryProducer()
                    val store = storeProducer()
                    ViewModelProvider(store, factory).get(viewModelClass.java).also {
                        cached = it
                    }
                } else {
                    viewModel
                }
            }

        override fun isInitialized() = cached != null

    }


}