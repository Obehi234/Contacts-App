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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab = findViewById(R.id.fab)


        //Set up main fragment
        launchFragment(ContactListFragment())
        fab.setOnClickListener { launchFragment(InsertContactsFragment()) }
    }

     private fun launchFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fcvDisplay, fragment)
        addToBackStack(null)
        commit()
    }

}
