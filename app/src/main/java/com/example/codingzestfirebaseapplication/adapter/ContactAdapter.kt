package com.example.codingzestfirebaseapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.codingzestfirebaseapplication.R
import com.example.codingzestfirebaseapplication.model.ContactModel

class ContactAdapter(private val contactList: ArrayList<ContactModel>): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvContactName: TextView = view.findViewById(R.id.tvContactDisplay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactAdapter.ViewHolder, position: Int) {
        val currentContact = contactList[position]
        holder.tvContactName.text = currentContact.contactName
    }

    override fun getItemCount(): Int = contactList.size
}