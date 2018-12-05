package ishaquehassan.balancebeam

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /*proceed()
        return*/
        val imgs = arrayListOf(
            R.drawable.bg_dash,
            R.drawable.bg_push_up_level,
            R.drawable.bg_dash,
            R.drawable.bg_push_up_level,
            R.drawable.bg_dash
        )

        imgs.forEach {
            imageSlider.addSliderView(SliderView(this).apply {
                setImageDrawable(it)
            })
        }

        imageSlider.scrollTimeInSec = 2

        imageSlider.pageChangeListener = {
            if(it == imgs.size-1){
                imageSlider.postDelayed({
                    proceed()
                },1500)
            }
        }

    }

    private fun proceed(){
        if(FirebaseAuth.getInstance().currentUser != null){
            startActivity(Intent(this,MainActivity::class.java))
        }else{
            startActivity(Intent(this,LoginActivity::class.java))
        }
        finish()
    }
}
