package com.example.android_base.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android_base.R
import com.example.android_base.databinding.SearchableDialogLayoutBinding

class SearchableDialogFragment : DialogFragment() {
    lateinit var binding: SearchableDialogLayoutBinding
    private val items = mutableListOf<String>()
    private lateinit var adapter: SearchableAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            SearchableDialogLayoutBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the RecyclerView and its adapter
        adapter = SearchableAdapter(items)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        // Set up the search functionality
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        // Add some dummy items for demonstration purposes
        items.add("Item 1")
        items.add("Item 2")
        items.add("Item 3")
        items.add("Item 4")
        items.add("Item 5")
        // ... Add more items as needed
    }
}
