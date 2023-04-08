package com.example.codingzestfirebaseapplication.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.codingzestfirebaseapplication.R
import com.example.codingzestfirebaseapplication.adapter.ContactAdapter
import com.example.codingzestfirebaseapplication.model.ContactModel
import com.google.firebase.database.*
class ContactListFragment : Fragment() {

    private lateinit var rvContact: RecyclerView
    private lateinit var tvLoading: TextView
    private lateinit var contactList: ArrayList<ContactModel>
    private lateinit var dbReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)
        tvLoading = view.findViewById(R.id.tvLoading)
        rvContact = view.findViewById(R.id.rvContactList)
        rvContact.layoutManager = LinearLayoutManager(requireContext())
        rvContact.setHasFixedSize(true)

        contactList = arrayListOf<ContactModel>()
        getContactData()

        return view
    }

    private fun getContactData() {
        rvContact.visibility = View.GONE
        tvLoading.visibility = View.VISIBLE

        dbReference = FirebaseDatabase.getInstance().getReference("Contacts")

        dbReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                contactList.clear()
                if (snapshot.exists()) {
                    for (contactSnap in snapshot.children) {
                        val contactData = contactSnap.getValue(ContactModel::class.java)
                        if (contactData != null) {
                            contactList.add(contactData)
                        }
                    }
                    val cAdapter = ContactAdapter(contactList)
                    rvContact.adapter = cAdapter
                    rvContact.visibility = View.VISIBLE
                    tvLoading.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle cancelled database request
            }
        })
    }
}
