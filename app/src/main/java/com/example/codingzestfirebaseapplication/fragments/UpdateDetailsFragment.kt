package com.example.codingzestfirebaseapplication.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.commit
import com.example.codingzestfirebaseapplication.R
import com.example.codingzestfirebaseapplication.model.ContactModel
import com.example.codingzestfirebaseapplication.utils.CONTACT
import com.example.codingzestfirebaseapplication.utils.PHONE_NUMBER
import com.google.firebase.database.FirebaseDatabase


class UpdateDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contactDetail = arguments?.getParcelable<ContactModel>(CONTACT)

        val username: TextView = view.findViewById(R.id.contact_name_tv)
        val phoneNumber: TextView = view.findViewById(R.id.contact_Phone_number_tv)
        val initials: TextView = view.findViewById(R.id.contact_detail_initials_tv)
        val callButton: ImageView = view.findViewById(R.id.contact_call_icon)
        val deleteButton: ImageView = view.findViewById(R.id.delete_contact_icon)
        val shareButton: ImageView = view.findViewById(R.id.share_contact_icon)
        val messageButton: ImageView = view.findViewById(R.id.contact_message_icon)
        val editButton: ImageView = view.findViewById(R.id.edit_contact_icon)

        username.text = getString(R.string.name, contactDetail?.contactName, contactDetail?.contactLastName)
        phoneNumber.text = contactDetail?.contactPhoneNumber
        initials.text =
            getString(R.string.initials, contactDetail?.contactName?.first(), contactDetail?.contactLastName?.first())

        callButton.setOnClickListener {
            callContact(contactDetail?.contactPhoneNumber ?: "111")
        }

        deleteButton.setOnClickListener {
            deleteAlertDialog(contactDetail?.contactId ?: "")
        }

        shareButton.setOnClickListener {
            shareContact()
        }

        messageButton.setOnClickListener {
            messageContact(contactDetail?.contactPhoneNumber ?: "111")
        }

        editButton.setOnClickListener {
            if (contactDetail != null) {
                editContact(contactDetail)
            }
        }
    }

    private fun callContact(phoneNumber : String) {
        val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phoneNumber"))
        startActivity(dialIntent)
    }

    private fun shareContact() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello, this is my contact!")
        startActivity(Intent.createChooser(shareIntent, "Share via"))
    }

    private fun messageContact(phoneNumber : String) {
        val messageIntent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:$phoneNumber"))
        messageIntent.putExtra("sms_body", "Hello, this is my message!")
        startActivity(messageIntent)
    }

    private fun editContact(contactModel : ContactModel) {
        val bundle = Bundle()
        bundle.putParcelable(CONTACT, contactModel)
        val insertContactsFragment = InsertContactsFragment()
        insertContactsFragment.arguments = bundle
        parentFragmentManager.commit {
            replace(R.id.fcvDisplay, insertContactsFragment)
            addToBackStack(insertContactsFragment::class.java.name)
        }
    }

    private fun deleteContact(contactId: String) {
        val postReference = FirebaseDatabase.getInstance().reference.child("Contacts").child(contactId)
        postReference.removeValue()
        parentFragmentManager.popBackStack()
    }

    private fun deleteAlertDialog(contactId: String) {
        AlertDialog.Builder(requireContext()).also {
            it.setTitle("Contact will be deleted")
            it.setPositiveButton("DELETE") { _, _ ->
                deleteContact(contactId)
                Toast.makeText(requireContext(), "Contact Deleted", Toast.LENGTH_SHORT).show()
            }
            it.setNegativeButton("CANCEL") { dialog, _ ->
                dialog.cancel()
            }
        }.create().show()
    }
}