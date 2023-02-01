package com.example.rbc_interview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbc_interview.R
import com.rbc.rbcaccountlibrary.Transaction
import java.text.SimpleDateFormat

class TransactionsAdapter(
    private val transactions: List<Transaction>, private val context: Context
) : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.custom_view_transactions, parent, false)
        )
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            accountName.text = transactions[position].description
            accountBalance.text = transactions[position].amount
            transactionDate.text =
                SimpleDateFormat("dd/MM/yyyy").format(transactions[position].date.time)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val accountName: TextView = view.findViewById(R.id.account_name)
        val accountBalance: TextView = view.findViewById(R.id.account_balance)
        val transactionDate: TextView = view.findViewById(R.id.transaction_date)

    }
}