package com.example.abbreviationappassignmentaren.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.abbreviationappassignmentaren.R
import com.example.abbreviationappassignmentaren.databinding.ItemBinding
import com.example.abbreviationappassignmentaren.models.DefinitionsModel

class ItemAdapter(
    val list: DefinitionsModel
): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val binding = ItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = list[0].lfs[position]
        holder.binding.tvCount.text = "freq: ${item.freq}"
        holder.binding.tvDefinition.text = item.lf
        holder.binding.tvVariation.text = "vars: ${item.vars.size}"
    }

    override fun getItemCount(): Int = list[0].lfs.size

}