package ishaquehassan.balancebeam

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.smarteist.autoimageslider.SliderView
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.activity_splash.view.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        /*proceed()
        return*/
        val imgs = arrayListOf(
            R.drawable.sp_1,
            R.drawable.sp_2,
            R.drawable.sp_3,
            R.drawable.sp_4
        )

        imgs.forEach {
            imageSlider.addSliderView(SliderView(this).apply {
                setImageDrawable(it)
                setImageScaleType(ImageView.ScaleType.FIT_XY)
            })
        }

        imageSlider.scrollTimeInSec = 2

        imageSlider.pageChangeListener = {
            if(it == imgs.size-1){
                imageSlider.postDelayed({
                    imageSlider.pageChangeListener = {}
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
