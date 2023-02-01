@file:Suppress("DEPRECATION")

package com.example.rbc_interview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rbc_interview.adapter.AccountsAdapter
import com.example.rbc_interview.databinding.ActivityMainBinding
import com.example.rbc_interview.viewModel.MainViewModel
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(),AccountsAdapter.OnItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        supportActionBar?.title = "Accounts"
        viewModel.result.observe(this) { updatedResult ->
            binding.recyclerView.adapter =
                AccountsAdapter(updatedResult, applicationContext, this@MainActivity)
        }
    }

    override fun onItemClick(position: Int) {
        val bundle = Bundle()
        bundle.apply {
            putString("name", viewModel.result.value?.let{ it[position].name})
            putString("number", viewModel.result.value?.let { it[position].number })
            putString("balance", viewModel.result.value?.let { it[position].balance })
            putString("type", viewModel.result.value?.let { it[position].type.name })
        }
        val intent = Intent(this,TransactionActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

}