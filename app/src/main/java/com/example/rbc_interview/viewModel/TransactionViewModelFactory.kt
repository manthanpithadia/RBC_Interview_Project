package com.example.rbc_interview.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class TransactionViewModelFactory(private val accountNumber:String, private val type:String): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TransactionViewModel::class.java)){
            return TransactionViewModel(accountNumber, type) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}