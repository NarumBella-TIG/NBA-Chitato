package com.example.nba_chitato.Beranda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nba_chitato.databinding.ItemTipsBinding

class TipsAdapter(
    private val tipsList: List<TipsModel>,
    private val onItemClick: (TipsModel) -> Unit
) : RecyclerView.Adapter<TipsAdapter.TipsViewHolder>() {

    inner class TipsViewHolder(val binding: ItemTipsBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipsViewHolder {
        val binding = ItemTipsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TipsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TipsViewHolder, position: Int) {
        val item = tipsList[position]
        with(holder.binding) {
            textJudulTips.text = item.judul
            textIsiTips.text = item.isi
            imgIkonTips.setImageResource(item.ikon)

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = tipsList.size
}
