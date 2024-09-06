package com.example.lifecycle

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : MainActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        var txtFname: EditText = findViewById<EditText?>(R.id.txtfName)
        var txtLname: EditText = findViewById<EditText?>(R.id.txtlName)
        var txtEmail: EditText = findViewById<EditText?>(R.id.txtEmail)
        var checkDriver: CheckBox = findViewById<CheckBox>(R.id.checkBox)
        var txtAge: EditText = findViewById<EditText?>(R.id.txtNrAge)
        var driversLicense: Boolean = false;

        checkDriver.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Log.i("Andreas", "Har körkort")
                driversLicense = true
            }

        }

        var btnSave: Button = findViewById<Button?>(R.id.button3)
        btnSave.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Log.i(
                "Andreas",
                "onCreate: ${txtFname.text.toString()}, ${txtLname.text.toString()}, ${txtEmail.text.toString()}, ${driversLicense}, ${
                    txtAge.text.toString().toIntOrNull()
                }"
            )
            write(
                txtFname.text.toString(),
                txtLname.text.toString(),
                txtEmail.text.toString(),
                driversLicense,
                txtAge.text.toString().toIntOrNull()!!
            )
            Toast.makeText(this, "Ny användare skapad, du kan logga in", Toast.LENGTH_SHORT).show()

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}