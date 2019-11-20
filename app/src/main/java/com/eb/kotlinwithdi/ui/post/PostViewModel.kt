package com.eb.kotlinwithdi.ui.post

import androidx.lifecycle.MutableLiveData
import com.eb.kotlinwithdi.common.ApiService
import com.eb.kotlinwithdi.common.base.BaseViewModel
import com.eb.kotlinwithdi.common.models.Post
import com.eb.kotlinwithdi.common.schedulers.SchedulerProvider


class PostViewModel (schedulerProvider: SchedulerProvider, private val api: ApiService): BaseViewModel(schedulerProvider){

    val laoding: MutableLiveData<Boolean> = MutableLiveData()

    init{
        loadPosts()
    }

    private fun loadPosts(){
        add {
            api.getPosts()
                .compose(applySchedulers())
                .doOnSubscribe { onRetrievePostListStart() }
                .doOnTerminate { onRetrievePostListFinish() }
                .subscribe(
                    // Add result
                    { result -> onRetrievePostListSuccess(result) },
                    { onRetrievePostListError() }
                )
        }
    }


    private fun onRetrievePostListStart(){
        laoding.value = true
    }

    private fun onRetrievePostListFinish(){
        laoding.value = false
    }

    private fun onRetrievePostListSuccess(list: List<Post>){

    }

    private fun onRetrievePostListError(){

    }

}