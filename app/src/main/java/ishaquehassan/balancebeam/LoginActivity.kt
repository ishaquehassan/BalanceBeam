package ishaquehassan.balancebeam

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        login_btnLogin.setOnClickListener {
            val email: String = login_emailEt.text.toString()
            val password: String = login_passwordEt.text.toString()
            login_emailEt.error = null
            login_passwordEt.error = null
            if (!email.isEmpty() && !password.isEmpty()) {

                val pd = ProgressDialog.show(this, "Logging In...", "Please wait...", true, false)

                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    pd.dismiss()
                    if (task.isSuccessful) {
                        startActivity(Intent(this,MainActivity::class.java))
                        finish()
                    } else {
                        login_emailEt.error = task.exception?.message
                    }
                }
            } else {
                if (email.isEmpty()) {
                    login_emailEt.error = "Please enter Email Address!"
                }
                if (password.isEmpty()) {
                    login_passwordEt.error = "Please enter Password!"
                }
            }
        }

        login_signUpLink.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
            finish()
        }
    }
}
