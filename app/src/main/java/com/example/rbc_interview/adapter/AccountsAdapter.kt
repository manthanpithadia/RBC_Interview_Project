package com.example.rbc_interview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rbc_interview.R
import com.rbc.rbcaccountlibrary.Account

class AccountsAdapter(
    private val accounts: List<Account>, private val context: Context,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<AccountsAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return accounts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.custom_view_accounts, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            accountName.text = accounts[position].type.name
            accountBalance.text = accounts[position].balance
            accountNumber.text = accounts[position].number
        }
        holder.itemView.setOnClickListener { listener.onItemClick(position) }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val accountName: TextView = view.findViewById(R.id.account_name)
        val accountBalance: TextView = view.findViewById(R.id.account_balance)
        val accountNumber: TextView = view.findViewById(R.id.account_number)
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}


