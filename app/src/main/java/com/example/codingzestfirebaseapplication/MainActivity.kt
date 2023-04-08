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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvShowContact = findViewById(R.id.tvshowContact)
        fab = findViewById(R.id.fab)


        //Set up main fragment
        launchFragment(AddFragment())
        fab.setOnClickListener { launchFragment(InsertContactsFragment()) }
        tvShowContact.setOnClickListener { launchFragment(ContactListFragment()) }
    }

    private fun launchFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fcvDisplay, fragment)
        addToBackStack(null)
        commit()
    }

}
