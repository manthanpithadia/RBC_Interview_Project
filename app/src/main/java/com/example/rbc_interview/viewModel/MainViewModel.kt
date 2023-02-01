package com.example.rbc_interview.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbc.rbcaccountlibrary.Account
import com.rbc.rbcaccountlibrary.AccountProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _result = MutableLiveData<List<Account>>()
    val result: LiveData<List<Account>>
        get() = _result

    init {
        getAccounts()
    }

    private fun getAccounts() {
        val mainScope = MainScope()
        mainScope.launch {
            val iOScope = async(Dispatchers.IO){
                AccountProvider.getAccountsList()
            }
            _result.value = iOScope.await().groupBy { it.type }.values.flatten()
        }
    }

}