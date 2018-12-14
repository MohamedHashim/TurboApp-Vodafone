package com.mohamedhashim.turboapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)
//        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("bins")
//
        myRef.setValue("Hello, World!")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue(String::class.java)
                Log.d("logs_", "Value is: " + value)
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("logs", "Failed to read value.", error.toException())
            }
        })

    }
}
