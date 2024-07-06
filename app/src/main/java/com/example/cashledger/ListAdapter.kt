package com.example.cashledger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class BookListAdapter(private val context: Context, private var bookList: List<Book>) :
    RecyclerView.Adapter<BookListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = bookList[position]
        holder.bind(book)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    fun updateBooks(newBooks: List<Book>): Double {
        bookList = newBooks
        notifyDataSetChanged()
        return bookList.sumByDouble { it.balance }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvBookName: TextView = itemView.findViewById(R.id.textViewBookName)
        private val tvBookBalance: TextView = itemView.findViewById(R.id.textViewBalance)
        private val btnDelete: ImageButton = itemView.findViewById(R.id.deleteButton)

        fun bind(book: Book) {
            tvBookName.text = "Book Name: ${book.name}"
            tvBookBalance.text = "Balance: ${book.balance}"

            btnDelete.setOnClickListener {
                deleteBook(book)
            }
        }

        private fun deleteBook(book: Book) {
            // Remove the book from Firebase
            val firebaseRef = FirebaseDatabase.getInstance().getReference("Books")
            firebaseRef.child(book.id).removeValue()

            // Update the list
            val updatedList = bookList.toMutableList().apply {
                remove(book)
            }
            updateBooks(updatedList)
        }
    }
}
