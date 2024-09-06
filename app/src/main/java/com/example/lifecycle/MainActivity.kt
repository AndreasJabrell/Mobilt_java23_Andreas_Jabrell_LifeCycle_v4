package com.example.lifecycle

//import com.google.api.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Html
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.firestore


open class MainActivity : AppCompatActivity() {


    var loggedOn: Boolean = false


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logIn -> {

                if (!loggedOn) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Du är redan inloggad", Toast.LENGTH_SHORT).show()
                }
                return true
            }

            R.id.create -> {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                Toast.makeText(this, "Du klickade skapa nytt konto", Toast.LENGTH_SHORT).show()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }


    var db = com.google.firebase.Firebase.firestore

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("loggedOn", loggedOn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)


        if (savedInstanceState != null) {
            loggedOn = savedInstanceState.getBoolean("loggedOn", false)
        }

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        var btnNew: TextView = findViewById<Button?>(R.id.textView4)
        var btnLogIn: Button = findViewById(R.id.button2)
        val txtUser: EditText = findViewById<EditText?>(R.id.editTextText)
        var txtPassword: EditText = findViewById<EditText>(R.id.editTextTextPassword)
        Log.i("Andreas", "Inloggad = " + loggedOn)

        //fetstilt på HÄR för att tydliggöra var man kan klicka
        val sourceString = "Inget konto? skapa " + "<b>${"HÄR"}".toString() + "</b> "
        btnNew.text = Html.fromHtml(sourceString)

        btnNew.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)

        }

        btnLogIn.setOnClickListener {
            val intent = Intent(this, MainActivity3::class.java)
            if (txtUser.text.toString() == "admin" && txtPassword.text.toString() == "password") {
                startActivity(intent)
                loggedOn = true
                Log.i("Andreas", "Inloggad = " + loggedOn)
            } else {
                Toast.makeText(this, "FEL VID INLOGG", Toast.LENGTH_SHORT).show()
                vibrate(this)

            }

        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    //funktion för att hämta inmatad användarinfo och spara till Firestore
    fun write(fName: String, lName: String, email: String, driver: Boolean, age: Int) {
        val user = hashMapOf(
            "fName" to fName,
            "lName" to lName,
            "email" to email,
            "driver" to driver,
            "age" to age,
        )

// Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("Andreas", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Andreas", "Error adding document", e)
            }
    }

    //funktion för att kunna köra manuell delete på eventuell felaktigt tillagd info, måste byta id här
    fun delete() {
        db.collection("users").document("ar26xYhc5bXJT9KhV5td")
            .delete()
            .addOnSuccessListener { Log.d("Andreas", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("Andreas", "Error deleting document", e) }

    }

    fun vibrate(mainActivity: MainActivity) {
        val vibrator = mainActivity.getSystemService(Vibrator::class.java)
        // Kontrollera om enheten har en vibrator
        if (vibrator?.hasVibrator() == true) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        500,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(500)
            }
        } else {
            Log.i("Andreas", "Den här enheten har ingen vibrator")
        }
    }
}


