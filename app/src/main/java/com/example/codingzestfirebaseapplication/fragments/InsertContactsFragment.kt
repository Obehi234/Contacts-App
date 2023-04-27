package com.example.codingzestfirebaseapplication.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.codingzestfirebaseapplication.R
import com.example.codingzestfirebaseapplication.model.ContactModel
import com.example.codingzestfirebaseapplication.utils.CONTACT
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertContactsFragment : Fragment() {
    private lateinit var etName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etPhoneNumber: EditText
    private lateinit var tvSave: TextView
    private lateinit var dbRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_insert_contacts, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contactDetail = arguments?.getParcelable<ContactModel>(CONTACT)

        etName = view.findViewById(R.id.etName)
        etLastName = view.findViewById(R.id.etLastName)
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber)
        tvSave = view.findViewById(R.id.tvSave)
        dbRef = FirebaseDatabase.getInstance().getReference("Contacts")

        if (contactDetail != null) {
            etName.setText(contactDetail.contactName)
            etLastName.setText(contactDetail.contactLastName)
            etPhoneNumber.setText(contactDetail.contactPhoneNumber)

            tvSave.setOnClickListener{
                contactDetail.contactId?.let { contactId -> saveEditedContactData(contactId) }
            }
        } else {
            tvSave.setOnClickListener { saveContactData() }
        }
    }

    private fun saveContactData() {
        // Extract user value
        val contactName = etName.text.toString()
        val contactLastName = etLastName.text.toString()
        val contactPhoneNumber = etPhoneNumber.text.toString()

        if (contactName.isEmpty()) {
            etName.error = "Please enter contact name"
            return
        }
        if (contactLastName.isEmpty()) {
            etLastName.error = "Please enter contact last name"
            return
        }
        if (contactPhoneNumber.isEmpty()) {
            etPhoneNumber.error = "Please enter contact phone number"
            return
        }

        val contactId = dbRef.push().key!!
        val contact = ContactModel(contactId, contactName, contactLastName, contactPhoneNumber)

        dbRef.child(contactId).setValue(contact)
            .addOnCompleteListener {
                Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_SHORT).show()

                etName.text.clear()
                etLastName.text.clear()
                etPhoneNumber.text.clear()

                // Exit the fragment and go back to the previous fragment
                requireActivity().supportFragmentManager.popBackStack()

            }.addOnFailureListener {err ->
                Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun saveEditedContactData(contactId: String) {
        // Extract user value
        val contactName = etName.text.toString()
        val contactLastName = etLastName.text.toString()
        val contactPhoneNumber = etPhoneNumber.text.toString()

        if (contactName.isEmpty()) {
            etName.error = "Please enter contact name"
            return
        }
        if (contactLastName.isEmpty()) {
            etLastName.error = "Please enter contact last name"
            return
        }
        if (contactPhoneNumber.isEmpty()) {
            etPhoneNumber.error = "Please enter contact phone number"
            return
        }

        val contact = ContactModel(contactId, contactName, contactLastName, contactPhoneNumber)

        dbRef.child(contactId).setValue(contact)
            .addOnCompleteListener {
                Toast.makeText(context, "Data Edited successfully", Toast.LENGTH_SHORT).show()

                etName.text.clear()
                etLastName.text.clear()
                etPhoneNumber.text.clear()

                // Exit the fragment and go back to the previous fragment
                requireActivity().supportFragmentManager.popBackStack()

            }.addOnFailureListener {err ->
                Toast.makeText(context, "Error ${err.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
