package com.eradotovic.mbankingapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eradotovic.mbankingapp.R
import com.eradotovic.mbankingapp.data.entity.Account

class AccountItemAdapter(
    private val dataset: List<Account>
) : RecyclerView.Adapter<AccountItemAdapter.ItemViewHolder>(){

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val ibanText: TextView = view.findViewById(R.id.acc_item_iban)
        val amountText: TextView = view.findViewById(R.id.acc_item_amount)
        val currencyText: TextView = view.findViewById(R.id.acc_item_currency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.accounts_pager_item, parent, false)
        return AccountItemAdapter.ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.ibanText.text = item.IBAN
        holder.amountText.text = item.amount
        holder.currencyText.text = item.currency
    }

    override fun getItemCount(): Int {
        return dataset.count()
    }
}