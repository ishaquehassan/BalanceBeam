package ishaquehassan.balancebeam

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import ishaquehassan.balancebeam.base.*
import kotlinx.android.synthetic.main.activity_patients.*

class PatientsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_patients)
        title = "Patients"
        setSupportActionBar(ptoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        patientsList.adapter =
                RecyclerGeneralAdapter(
                    getAllDiseases().toArrayList(), R.layout.dashboard_item
                ) { v, itm, pos ->
                    v.findViewById<ImageView>(R.id.dashItem_bgImg)
                        .setImageResource(getDrawableResId(itm.image))
                    v.findViewById<TextView>(R.id.dashItem_titleTv).text = itm.title
                    v.findViewById<TextView>(R.id.dashItem_stitleTv).text = "${itm.stitle}"
                    v.setOnClickListener {
                        startActivity(Intent(this, ExercisesActivity::class.java).also {
                            it.putExtra("data", itm.levels[getUserData().skillLevel])
                            it.putExtra("header", itm)
                        })
                    }
                }
        patientsList.layoutManager = LinearLayoutManager(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
