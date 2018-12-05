package ishaquehassan.balancebeam

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.gson.Gson
import ishaquehassan.balancebeam.base.getUserData
import ishaquehassan.balancebeam.base.toast
import ishaquehassan.balancebeam.models.UserData
import kotlinx.android.synthetic.main.activity_signup.*


class SignUpActivity : AppCompatActivity() {

    var isEditProfile:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        isEditProfile = intent.getBooleanExtra("ep",false)


        signup_skillLevel.adapter = ArrayAdapter<String>(this,R.layout.spinner_item,resources.getStringArray(R.array.skill_level))

        if(isEditProfile){
            epCancel.visibility = View.VISIBLE
            epCancel.setOnClickListener {
                finish()
            }
            signup_loginLink.visibility = View.GONE
            pageTitle.text = "EDIT PROFILE"
            pagesTitle.text = "Change your profile info here!"
            signup_nameEt.setText( getUserData().name)
            signup_emailEt.setText( getUserData().email)
            signup_emailEt.isEnabled = false
            signup_ageEt.setText( getUserData().age.toString())
            signup_weightEt.setText( getUserData().weight.toString())
            val skills = resources.getStringArray(R.array.skill_level)
            signup_skillLevel.setSelection(skills.indexOf(getUserData().skillLevel))
            signup_passwordEt.visibility = View.GONE
            signup_btnLogin.text = "Save"
        }



        signup_btnLogin.setOnClickListener {
            val name: String = signup_nameEt.text.toString()
            val email: String = signup_emailEt.text.toString()
            val age: String = signup_ageEt.text.toString()
            val weight: String = signup_weightEt.text.toString()
            val skillLevel:String = signup_skillLevel.selectedItem.toString()
            val password: String = signup_passwordEt.text.toString()
            signup_emailEt.error = null
            signup_passwordEt.error = null
            signup_nameEt.error = null
            signup_ageEt.error = null
            signup_weightEt.error = null
            if (!email.isEmpty() && !age.isEmpty() && !weight.isEmpty() && !skillLevel.isEmpty() && (!password.isEmpty() || isEditProfile)) {
                val pd = ProgressDialog.show(this, if(isEditProfile) "Saving Profile..." else "Signing Up...", "Please wait...", true, false)
                if(isEditProfile){
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(Gson().toJson(UserData(name,email,age.toInt(),weight.toFloat(),skillLevel)))
                        .build()
                    FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            setResult(Activity.RESULT_OK)
                            pd.dismiss()
                            finish()
                        }else{
                            toast("Error while updating please try again later")
                        }
                    }
                }else{
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(Gson().toJson(UserData(name,email,age.toInt(),weight.toFloat(),skillLevel)))
                                .build()

                            FirebaseAuth.getInstance().currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    pd.dismiss()
                                    startActivity(Intent(this,MainActivity::class.java))
                                    finish()
                                }else{
                                    toast("Error while updating please try again later")
                                }
                            }
                        } else {
                            pd.dismiss()
                            signup_emailEt.error = task.exception?.message
                        }
                    }
                }
            } else {
                if (email.isEmpty()) {
                    signup_emailEt.error = "Please enter Email Address!"
                }
                if (password.isEmpty()) {
                    signup_passwordEt.error = "Please enter Password!"
                }
                if (age.isEmpty()) {
                    signup_ageEt.error = "Please enter Age!"
                }
                if (weight.isEmpty()) {
                    signup_weightEt.error = "Please enter Weight!"
                }
                if (name.isEmpty()) {
                    signup_nameEt.error = "Please enter Name!"
                }
            }
        }

        signup_loginLink.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }

    }
}
