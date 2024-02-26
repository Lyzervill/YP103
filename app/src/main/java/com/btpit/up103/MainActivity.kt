package com.btpit.up103

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.btpit.up103.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this){post ->
            with(binding){
                textViewAutor.text = post.autor
                textViewContent.text = post.content
                textViewLikeCount.text= post.likecount.toString()



                imageButtonLike.setOnClickListener{
                    if (post.likedByMe) post.likecount++
                    else post.likecount--
                    textViewLikeCount.text= amountToStr(post.likecount)


                    imageButtonLike.setImageResource(
                        if(post.likedByMe) R.drawable.icons8__24
                        else R.drawable._589054)
                }
                imageButtonShare.setOnClickListener{
                    post.sharecount++
                    textViewShareCount.text= amountToStr(post.sharecount)
                }
            }

        }


    }
    class ViewModelLazy<VM : ViewModel>(
        private val viewModelClass: KClass<VM>,
        private val storeProducer:() -> ViewModelStore,
        private val factoryProducer: () -> ViewModelProvider.Factory
    ) : Lazy<VM>{
        private var cached: VM? = null

        override val value: VM
            get() {
                val viewModel = cached
                return if (viewModel == null){
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
        fun amountToStr(count:Int): String {
            return when(count){
                in 0..<1_000 -> count.toString()
                1000 -> "1K"
                in 999..<10_000 -> ((count/100).toFloat()/10).toString() + "K"
                in 10_000..<1_000_000 -> (count/1_000).toString() + "K"
                1_000_000 -> "1M"
                in 999_999..<10_000_000 -> ((count/100_000).toFloat()/10).toString() + "M"
                in 10_000_000..<1_000_000_000 -> (count/1_000_000).toString() + "M"
                else -> "Более млрд"
            }
        }



}