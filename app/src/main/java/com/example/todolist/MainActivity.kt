package com.example.todolist

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Layout
import android.view.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.core.content.getSystemService
import io.realm.Realm

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            var addIntent = Intent(this, AddToDoActivity::class.java)
            startActivity(addIntent)
        }

        // Realm

    }

    override fun onResume() {
        super.onResume()

        val realm = Realm.getDefaultInstance()

        val query = realm.where(ToDoItem::class.java)
        val results = query.findAll()

        val listView = findViewById<ListView>(R.id.toDoItemListView)

        listView.setOnItemClickListener { adapterView, view, i, l ->
            val selectedToDo = results[i]
            val finishIntent = Intent(this, FinishToDoActivity::class.java)
            finishIntent.putExtra("toDoItem", selectedToDo.getId())
            startActivity(finishIntent)
        }

        val adapter = ToDoAdapter(this,android.R.layout.simple_list_item_1, results)
        listView.adapter = adapter
    }
/*
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
    */
}


class ToDoAdapter(context: Context?, resource: Int, objects: MutableList<ToDoItem>?) :
    ArrayAdapter<ToDoItem>(context, resource, objects) {
    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
       val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       val toDoView =  inflator.inflate(android.R.layout.simple_list_item_1,parent,false) as TextView

        val toDoItem = getItem(position)

        toDoView.text = toDoItem.name

        if (toDoItem.important) {
            toDoView.setTypeface(Typeface.DEFAULT_BOLD)
        }

        return toDoView
    }
}
