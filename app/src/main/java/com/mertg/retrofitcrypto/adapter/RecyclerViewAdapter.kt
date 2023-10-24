package com.mertg.retrofitcrypto.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mertg.retrofitcrypto.R
import com.mertg.retrofitcrypto.databinding.RecyclerRowBinding // View Binding sınıfını içe aktar
import com.mertg.retrofitcrypto.model.CryptoModel

class RecyclerViewAdapter(private val cryptoList : ArrayList<CryptoModel>, private val listener : Listener) :
    RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {

    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors : Array<String> = arrayOf("#074548","#414a4c","#3b444b","#353839","#232b2b","#0e1111","#0e1a40","#222f5b","#004444")

    class RowHolder(private val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(cryptoModel: CryptoModel, colors: Array<String>, position: Int, listener: Listener){
            binding.root.setOnClickListener {
                listener.onItemClick(cryptoModel)
            }
            binding.root.setBackgroundColor(Color.parseColor(colors[position % 9]))
            binding.textName.text = cryptoModel.name +  " " + "(" + cryptoModel.symbol.toUpperCase() + ")"
            binding.textPrice.text = cryptoModel.current_price + "$"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position], colors, position, listener)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }
}
