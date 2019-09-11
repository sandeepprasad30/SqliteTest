package com.sandeep.sqlitetest

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat

import kotlinx.android.synthetic.main.activity_main.*

val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        Log.d(TAG, "onCreate starts")

        val database = baseContext.openOrCreateDatabase("sqlite-test-1.db", Context.MODE_PRIVATE, null)
        database.execSQL("drop table if exists contacts")
        var sql = "CREATE TABLE if not exists CONTACTS (_id INTEGER PRIMARY KEY NOT NULL, name TEXT, phone INTEGER, email TEXT)"
        database.execSQL(sql)



        sql = "INSERT INTO CONTACTS (name, phone, email) values ('san', 123456, 'san@test.com')"
        database.execSQL(sql)

        val values = ContentValues().apply {
            put("name", "John")
            put("phone", 123467)
            put("email", "t1@t1.com")
        }

        val generatedId = database.insert("contacts", null, values )

        val query = database.rawQuery("select * from contacts", null)

        query.use {
            while (it.moveToNext()) {
                with(it) {
                    val id = getLong(0)
                    val name = getString(1)
                    val phone = getInt(2)
                    val email = getString(3)

                    val result = "id= $id, name= $name, phone= $phone, email= $email"

                    Log.d(TAG, "read data $result")
                }
            }
        }

        query.close()

        Log.d(TAG, " id $generatedId")

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_INDEFINITE)
                .setAction("Action", null).show()
        }
    }

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
}
