package com.example.codingzestfirebaseapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.codingzestfirebaseapplication.fragments.AddFragment
import com.example.codingzestfirebaseapplication.fragments.ContactListFragment
import com.example.codingzestfirebaseapplication.fragments.InsertContactsFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var fab: FloatingActionButton
    private lateinit var tvShowContact: TextView
    private var isContactListDisplayed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvShowContact = findViewById(R.id.tvshowContact)
        fab = findViewById(R.id.fab)


        //Set up main fragment
        launchFragment(AddFragment())
        fab.setOnClickListener { launchFragment(InsertContactsFragment()) }
        tvShowContact.setOnClickListener {  toggleContactList()  }
    }

    private fun toggleContactList() {
        if (isContactListDisplayed) {
            // ContactListFragment is currently displayed, so remove it
            supportFragmentManager.popBackStack()
            isContactListDisplayed = false
        } else {
            // ContactListFragment is not displayed, so show it
            launchFragment(ContactListFragment())
            isContactListDisplayed = true
        }
    }

    private fun launchFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fcvDisplay, fragment)
        addToBackStack(null)
        commit()
    }

}
