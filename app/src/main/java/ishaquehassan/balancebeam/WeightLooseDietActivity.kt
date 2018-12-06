package ishaquehassan.balancebeam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ishaquehassan.balancebeam.base.RecyclerGeneralAdapter
import ishaquehassan.balancebeam.base.getWeightLooseProgramDiet
import ishaquehassan.balancebeam.base.toArrayList
import ishaquehassan.balancebeam.models.DietPlanModel
import kotlinx.android.synthetic.main.activity_weight_loose_diet.*

class WeightLooseDietActivity : AppCompatActivity() {



    class DietPlanFrag : Fragment(){
        lateinit var plans:Array<DietPlanModel>
        lateinit var title:String

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val v = inflater.inflate(R.layout.fragment_diet_plan,container,false)
            val rv = v.findViewById<RecyclerView>(R.id.wldietPlanList)
            rv.adapter = RecyclerGeneralAdapter<DietPlanModel>(
                plans.toArrayList(),R.layout.diet_plan_item
            ){v,i,p->
                v.findViewById<TextView>(R.id.d_title).text = i.title
                v.findViewById<TextView>(R.id.d_descp).text = i.descp
            }
            rv.layoutManager = LinearLayoutManager(context)
            return v
        }
    }

    class WlPagerAdapter(fm:FragmentManager,val data:ArrayList<DietPlanFrag>) : FragmentStatePagerAdapter(fm){
        override fun getItem(p0: Int): Fragment=data[p0]
        override fun getCount(): Int = data.size
        override fun getPageTitle(position: Int): CharSequence? = data[position].title
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_loose_diet)
        setSupportActionBar(wdtoolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        val type = intent.getStringExtra("type")
        val dietPlanArr = getWeightLooseProgramDiet()[type]!!
        title = "Diet Plan (${type.capitalize()})"
        dietPlanPager.adapter = WlPagerAdapter(supportFragmentManager,(0..(dietPlanArr.size-1)).map { dayIdex ->
            DietPlanFrag().apply {
                title = "DAY ${dayIdex+1}"
                plans = dietPlanArr[dayIdex]
            }
        }.toTypedArray().toArrayList())

        dietPlanTabs.setupWithViewPager(dietPlanPager)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
