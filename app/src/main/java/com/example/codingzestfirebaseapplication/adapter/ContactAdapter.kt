package com.example.codingzestfirebaseapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.codingzestfirebaseapplication.R
import com.example.codingzestfirebaseapplication.model.ContactModel

class ContactAdapter(
    private val contactList: ArrayList<ContactModel>,
    private val context: Context,
    private val onItemClick: (ContactModel) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvContactName: TextView = view.findViewById(R.id.tvContactDisplay)
        val rootLayout: CardView = view.findViewById(R.id.root_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentContact = contactList[position]
        holder.tvContactName.text =
            context.getString(R.string.name, currentContact.contactName, currentContact.contactLastName)
        holder.rootLayout.setOnClickListener {
            onItemClick.invoke(currentContact)
        }
    }

    override fun getItemCount(): Int = contactList.size
}