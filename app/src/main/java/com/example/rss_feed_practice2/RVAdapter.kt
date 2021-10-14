package com.example.rss_feed_practice2
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rss_feed_practice2.databinding.SingleItemBinding


class MyAdapter(private val names: List<XMLParser>) : RecyclerView.Adapter<MyAdapter.ItemViewHolder>(){
    class ItemViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
           SingleItemBinding.inflate( LayoutInflater.from(parent.context)
               ,parent
               ,false
           ))


    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val name =names [position]

        holder.binding.apply{
            textView.text=name.name+"\n"+name.marks+"\n"+"\n"
        }

    }

    override fun getItemCount() =    names.size

}

