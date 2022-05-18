package com.eradotovic.mbankingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eradotovic.mbankingapp.R
import com.eradotovic.mbankingapp.data.entity.Transaction

class TransactionItemAdapter(
    private val dataset: List<Transaction>
    ) : RecyclerView.Adapter<TransactionItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val dateTextView: TextView = view.findViewById(R.id.tran_item_date)
        val descriptTextView: TextView = view.findViewById(R.id.tran_item_decript)
        val amountTextView: TextView = view.findViewById(R.id.tran_item_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.transactions_list_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.dateTextView.text = item.date
        if(item.type != null){
            holder.descriptTextView.text = item.description + " " + item.type
        } else {
            holder.descriptTextView.text = item.description
        }
        holder.amountTextView.text = item.amount
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}