@file:Suppress("DEPRECATION")

package com.example.rbc_interview

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rbc_interview.adapter.TransactionsAdapter
import com.example.rbc_interview.databinding.ActivityTransactionBinding
import com.example.rbc_interview.viewModel.TransactionViewModel
import com.example.rbc_interview.viewModel.TransactionViewModelFactory
import kotlinx.coroutines.*

@Suppress("DEPRECATION")
class TransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding
    private lateinit var viewModel: TransactionViewModel
    private lateinit var viewModelFactory: TransactionViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_transaction)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Transaction"
        val intent = intent.extras
        intent?.apply {
            binding.accountName.text = getString("name")
            binding.accountNumber.text = getString("number")
            binding.accountBalance.text = getString("balance")
        }
        if(intent!=null)
            viewModelFactory = TransactionViewModelFactory(intent.getString("number")?:"",intent.getString("type")?:"")

        viewModel = ViewModelProviders.of(this,viewModelFactory)[TransactionViewModel::class.java]
        binding.transactionViewModel = viewModel
        binding.lifecycleOwner = this

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.result.observe(this){updatedResult->
            binding.recyclerView.adapter = updatedResult?.let {
                TransactionsAdapter(it, this@TransactionActivity)
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==android.R.id.home)
            finish()
        return super.onOptionsItemSelected(item)
    }
}