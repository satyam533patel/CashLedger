package com.example.cashledger

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.cashledger.databinding.ActivityAddBookBinding
import com.google.firebase.database.*

class AddBookActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBookBinding
    private lateinit var firebaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize view binding
        binding = ActivityAddBookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase reference
        firebaseRef = FirebaseDatabase.getInstance().getReference("Books")

        // Set onClickListener for Cancel button
        binding.btnCancel.setOnClickListener {
            navigateToAllBooks()
        }

        // Set onClickListener for Save button
        binding.btnSave.setOnClickListener {
            val bookName = binding.bookName.text.toString().trim()
            val bookBalanceText = binding.bookBalance.text.toString().trim()

            if (isValidBookName(bookName) && isValidBookBalance(bookBalanceText)) {
                val bookBalance = bookBalanceText.toDouble()
                checkForDuplicateBookNameAndSave(bookName, bookBalance)
            } else {
                if (!isValidBookName(bookName)) {
                    binding.bookName.error = "Book name must start with a letter."
                }
                if (!isValidBookBalance(bookBalanceText)) {
                    binding.bookBalance.error = "Balance must be a valid number."
                }
            }
        }
    }

    // Function to validate if the book name starts with a letter
    private fun isValidBookName(bookName: String): Boolean {
        val regex = "^[a-zA-Z].*".toRegex()
        return regex.matches(bookName)
    }

    // Function to validate if the balance is a valid double number
    private fun isValidBookBalance(bookBalance: String): Boolean {
        return bookBalance.toDoubleOrNull() != null
    }

    // Function to check for duplicate book names and save the book if no duplicate is found
    private fun checkForDuplicateBookNameAndSave(bookName: String, bookBalance: Double) {
        firebaseRef.orderByChild("name").equalTo(bookName)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        binding.bookName.error = "Book name already exists."
                    } else {
                        saveBookToFirebase(bookName, bookBalance)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle possible errors.
                    databaseError.toException().printStackTrace()
                }
            })
    }

    // Function to save book to Firebase
    private fun saveBookToFirebase(bookName: String, bookBalance: Double) {
        val bookId = firebaseRef.push().key

        if (bookId != null) {
            val book = Book(bookId, bookName, bookBalance)
            firebaseRef.child(bookId).setValue(book).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navigateToAllBooks()
                } else {
                    task.exception?.printStackTrace()
                }
            }
        } else {
            binding.bookName.error = "Error saving book. Please try again."
        }
    }

    // Function to navigate to AllBookActivity
    private fun navigateToAllBooks() {
        val intent = Intent(this, AllBookActivity::class.java)
        startActivity(intent)
        finish() // Optional: Finish current activity to prevent back navigation to AddBookActivity
    }
}
