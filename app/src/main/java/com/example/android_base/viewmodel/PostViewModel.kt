package com.example.android_base.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_base.model.PostResponse
import com.example.android_base.retrofit.ApiServiceRepo
import com.example.android_base.retrofit.CoroutinesEvent
import com.example.android_base.retrofit.ResponseStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class PostViewModel @Inject constructor(private val apiServiceRepo: ApiServiceRepo) :
    ViewModel() {

    private val _post = MutableLiveData<CoroutinesEvent<ResponseStatus<List<PostResponse>>>>()
    val post: LiveData<CoroutinesEvent<ResponseStatus<List<PostResponse>>>> get() = _post

//    val post: MutableLiveData<CoroutinesEvent<ResponseStatus<List<PostResponse>>>> =
//        MutableLiveData()

    fun getPostVM(activity: Activity) {
        _post.value = CoroutinesEvent(ResponseStatus.Loading())
        viewModelScope.launch {
            val response = apiServiceRepo.getPostRepo(activity)
            _post.value = CoroutinesEvent(response)
        }
    }
    /*
       val allTransactionsLiveData: MutableLiveData<CoroutinesEvent<ResponseStatus<TransactionModel>>?> = MutableLiveData()

       fun allTransactionsVM(token: String?, corID: Int?, remoteAddress: String, req: FinancialFilterReq, page: Int, size: Int) {
           allTransactionsLiveData.value = CoroutinesEvent(ResponseStatus.Loading())
           viewModelScope.launch {
               val response = ApiServiceRepo().allTransactionsRepo(token, corID, remoteAddress, req, page, size)
               allTransactionsLiveData.value = CoroutinesEvent(response)
           }
       }
   */
}