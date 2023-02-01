package com.example.rbc_interview.viewModel

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbc.rbcaccountlibrary.AccountProvider
import com.rbc.rbcaccountlibrary.Transaction
import kotlinx.coroutines.*

class TransactionViewModel(private val accountNumber: String, private val type:String) : ViewModel() {

    private var viewModelJob = Job()
    private var coroutineScope = CoroutineScope(viewModelJob  + Dispatchers.Main)

    //including additional credit card transaction if available
    private var allTransactionsList = listOf<Transaction>()

    private var _result = MutableLiveData<List<Transaction>>()
    val result:LiveData<List<Transaction>>
        get() = _result

    // Updates Progressbar Visibility
    private var _isSessionFinished = MutableLiveData<Int>()
    val isSessionFinished:LiveData<Int>
        get() = _isSessionFinished

    private var _dataFound = MutableLiveData<Int>()
    val dataFound:LiveData<Int>
        get() = _dataFound

    init {
        getTransactions()
        _isSessionFinished.value = View.VISIBLE
        _dataFound.value = View.GONE
    }

    private fun getTransactions() {
        val handler = CoroutineExceptionHandler { _, throwable ->
            Log.d("Result", "Exception: $throwable")
            _isSessionFinished.value = View.GONE
            _dataFound.value = View.VISIBLE
        }

        coroutineScope.launch(handler) {
            supervisorScope {
                lateinit var allTransactions: Deferred<List<Transaction>>
                lateinit var creditTransactions: Deferred<List<Transaction>>
                withContext(coroutineContext){
                        launch {
                            allTransactions = async(Dispatchers.IO){
                                AccountProvider.getTransactions(accountNumber)
                            }
                        }
                        launch {
                            creditTransactions = async(Dispatchers.IO){
                                AccountProvider.getAdditionalCreditCardTransactions(accountNumber)
                            }
                        }
                    }

                allTransactionsList = if(type == "CREDIT_CARD"){
                    allTransactions.await().plus(creditTransactions.await())
                } else{
                    allTransactions.await()
                }
                    _result.value = allTransactionsList.sortedBy { it.date.time }
                        .groupBy { it.date.time }.values.flatten().also{
                            _isSessionFinished.value = View.GONE
                        }
                    Log.d("Result","size: ${allTransactionsList.size}")
                    if(_result.value?.size == 0){
                        _dataFound.value = View.VISIBLE
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        _isSessionFinished.value = View.GONE
        _dataFound.value = View.GONE
    }
}