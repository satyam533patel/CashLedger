package com.example.cashledger

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EditBookActivity : AppCompatActivity() {
    private lateinit var bookNameEditText: EditText
    private lateinit var balanceEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_book)

        // Initialize EditText fields
        bookNameEditText = findViewById(R.id.editTextBookName)
        balanceEditText = findViewById(R.id.editTextBalance)

        // Example of showing dialog on delete button click
        val deleteButton = findViewById<ImageButton>(R.id.deleteButton)
        deleteButton.setOnClickListener {
            showEditDialog()
        }
    }

    private fun showEditDialog() {
        val builder = MaterialAlertDialogBuilder(this)
        builder.setTitle("Edit Book Details")

        // Set up the input
        val inputLayout = layoutInflater.inflate(R.layout.dialog_edit_book, null)
        val bookNameInput = inputLayout.findViewById<EditText>(R.id.editTextBookName)
        val balanceInput = inputLayout.findViewById<EditText>(R.id.editTextBalance)

        // Pre-fill with existing values if needed
        val currentBookName = bookNameEditText.text.toString()
        val currentBalance = balanceEditText.text.toString()
        bookNameInput.setText(currentBookName)
        balanceInput.setText(currentBalance)

        builder.setView(inputLayout)

        // Set up buttons
        builder.setPositiveButton("Save") { dialog, _ ->
            // Handle save action
            val newBookName = bookNameInput.text.toString()
            val newBalance = balanceInput.text.toString()

            // Update UI with new values
            bookNameEditText.setText(newBookName)
            balanceEditText.setText(newBalance)

            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }
}
