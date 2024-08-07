package com.example.cashledger

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
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
    private lateinit var balanceTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_all_book)

        // Initialize Firebase reference
        firebaseRef = FirebaseDatabase.getInstance().getReference("Books")

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.mRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize Balance TextView
        balanceTextView = findViewById(R.id.Balancevalue)

        // Initialize BookListAdapter with edit and delete handlers
        bookListAdapter = BookListAdapter(this, emptyList(), this::editBook, this::deleteBook)
        recyclerView.adapter = bookListAdapter

        // Set onClickListener for Floating Action Button (newBook)
        val btnNewBook = findViewById<FloatingActionButton>(R.id.newBook)
        btnNewBook.setOnClickListener {
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
                val totalBalance: Double = bookListAdapter.updateBooks(books)
                balanceTextView.text = String.format("%.2f", totalBalance)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle possible errors.
                databaseError.toException().printStackTrace()
            }
        })
    }

    private fun editBook(book: Book) {
        // Handle edit action
        // For example, start an activity to edit the book details
//        val intent = Intent(this, EditBookActivity::class.java)
//        intent.putExtra("bookId", book.id)
//        startActivity(intent)
    }
    private fun deleteBook(book: Book) {
        // Handle delete action
        // For example, remove the book from Firebase
        firebaseRef.child(book.id).removeValue()
    }
}
