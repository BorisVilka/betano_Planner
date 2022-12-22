package com.betano.planner

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.betano.planner.databinding.ListItemBinding
import java.util.*
import kotlin.collections.HashSet

class MyAdapter(var context: Context, var mode: Int): RecyclerView.Adapter<MyAdapter.Companion.MyHolder>() {

    var checks = mutableListOf<Boolean>()
    var data = mutableListOf<String>()

    init {
        var set = context.getSharedPreferences("prefs",Context.MODE_PRIVATE).getStringSet("set",HashSet<String>())
        data.addAll(set!!.toList())
        data.sortBy {
            it.split("^")[0].toInt()
        }
        for(i in data.indices) checks.add(false)
    }

    companion object {

        class  MyHolder(var binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        var s = data[position].split("^")
        holder.binding.textView21.text = "${s[1]} by ${s[2]}"
       if(mode==0) {
           holder.binding.checkBox.isChecked = true
           holder.binding.checkBox.isClickable = false
       } else {
           holder.binding.checkBox.setOnCheckedChangeListener { _, b ->
               checks[position] = b
           }
       }
    }

    override fun onViewRecycled(holder: MyHolder) {
        holder.binding.checkBox.setOnCheckedChangeListener(null)
        super.onViewRecycled(holder)
    }

    override fun getItemCount(): Int {
        return  data.size
    }
}