package ashex.flightloc

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    lateinit var loginBtn: Button
    lateinit var registerBtn: Button
    lateinit var emailEdit: EditText
    lateinit var passwordEdit: EditText

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        loginBtn = findViewById(R.id.loginBtn)
        registerBtn = findViewById(R.id.registerBtn)
        emailEdit = findViewById(R.id.emailEdit)
        passwordEdit = findViewById(R.id.passwordEdit)

        mAuth = Firebase.auth

        loginBtn.setOnClickListener {
            val email = emailEdit.text.toString()
            val pass = passwordEdit.text.toString()
            mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser
                        Log.d("Auth", "$user signed in")
                        Toast.makeText(this, "${user?.email} Signed In", Toast.LENGTH_LONG).show()
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Log.w("Auth", "Failed to sign in user, Email: $email, Pass: $pass", task.exception)
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        registerBtn.setOnClickListener {
            val email = emailEdit.text.toString()
            val pass = passwordEdit.text.toString()
            mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    Log.d("Auth", "Created user $user")
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    Log.w("Auth", "Failed to create user, Email: $email, Pass: $pass", task.exception)
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser?.email
        if (currentUser != null) {
            Toast.makeText(this, "$currentUser Signed In", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}