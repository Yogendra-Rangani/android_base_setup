package com.example.android_base.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_base.R

class SearchableAdapter(private val originalItems: List<String>) :
    RecyclerView.Adapter<SearchableAdapter.SearchViewHolder>(), Filterable {

    private var filteredItems = originalItems.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.item_searchable, parent, false)
        return SearchViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(filteredItems[position])
    }

    override fun getItemCount(): Int {
        return filteredItems.size
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textItem = itemView.findViewById<TextView>(R.id.textItem)

        fun bind(item: String) {
            textItem.text = item
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<String>()
                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(originalItems)
                } else {
                    val filterPattern = constraint.toString().trim().toLowerCase()
                    for (item in originalItems) {
                        if (item.toLowerCase().contains(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredItems.clear()
                filteredItems.addAll(results?.values as MutableList<String>)
                notifyDataSetChanged()
            }
        }
    }
}
