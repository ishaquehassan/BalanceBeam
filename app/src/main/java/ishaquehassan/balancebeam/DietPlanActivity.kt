package ishaquehassan.balancebeam

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import ishaquehassan.balancebeam.base.RecyclerGeneralAdapter
import ishaquehassan.balancebeam.base.getBulkDietPlan
import ishaquehassan.balancebeam.base.getLeanDietPlan
import ishaquehassan.balancebeam.base.toArrayList
import kotlinx.android.synthetic.main.activity_diet_plan.*

class DietPlanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diet_plan)
        setSupportActionBar(dtoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val isLean = intent.getBooleanExtra("isLean",false)
        title = "${if(isLean) "Lean" else "Bulk"} Diet Plan"
        dietPlanList.adapter =
                RecyclerGeneralAdapter(
                    if(isLean)getLeanDietPlan().toArrayList() else getBulkDietPlan().toArrayList(), R.layout.diet_plan_item
                ) { v, itm, pos ->
                    v.findViewById<TextView>(R.id.d_title).text = itm.title
                    v.findViewById<TextView>(R.id.d_descp).text = itm.descp
                }
        dietPlanList.layoutManager = LinearLayoutManager(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
