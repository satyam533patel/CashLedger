package com.example.cashledger

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class AllBookActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var bookListAdapter: BookListAdapter
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_book)

        // Initialize Firebase reference
        firebaseRef = FirebaseDatabase.getInstance().getReference("Books")

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.mRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        bookListAdapter = BookListAdapter(this, emptyList()) // Initialize with an empty list
        recyclerView.adapter = bookListAdapter

        // Set onClickListener for Floating Action Button (newBook)
        val btnNewBook = findViewById<FloatingActionButton>(R.id.newBook)
        btnNewBook.setOnClickListener {
//            println("started adding list")
            val intent = Intent(this, AddBookActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        // Attach a listener to fetch and update the book list from Firebase
        firebaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val books = mutableListOf<Book>()
                for (snapshot in dataSnapshot.children) {
                    val book = snapshot.getValue(Book::class.java)
                    book?.let { books.add(it) }
                }
                bookListAdapter.updateBooks(books)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
                databaseError.toException().printStackTrace()
            }
        })
    }
}
