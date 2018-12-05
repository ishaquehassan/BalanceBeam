package ishaquehassan.balancebeam

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import ishaquehassan.balancebeam.base.*
import ishaquehassan.balancebeam.models.MenuItemModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.nav_header_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        title = "Dashboard"

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

       initList()

        menuList.adapter = RecyclerGeneralAdapter(
            arrayListOf(
                MenuItemModel("Edit Profile") {startActivityForResult(Intent(this,SignUpActivity::class.java).apply { putExtra("ep",true) },1)},
                MenuItemModel("Logout") {signOut()}
            ),R.layout.menu_item_layout
        ){v,i,p->
            v.findViewById<TextView>(R.id.menuItemTitle).text = i.title
            v.setOnClickListener {  drawer_layout.closeDrawer(GravityCompat.START); i.onClick() }
        }
        menuList.addDivider()
        menuList.layoutManager = LinearLayoutManager(this)

        patientsBtn.setOnClickListener {
            startActivity(Intent(this,PatientsActivity::class.java))
        }
        weightLooseBtn.setOnClickListener {
            startActivity(Intent(this, ExercisesActivity::class.java).also {
                it.putExtra("data", getWeightLooseProgram().levels[getUserData().skillLevel])
                it.putExtra("header", getWeightLooseProgram())
            })
        }
    }

    private fun initList(){
        navUnameTv.text =  "${getUserData().name} - ${getUserData().skillLevel}"
        navEmailTv.text = getUserData().email

        val data = getAllExercises()

        dashboardList.adapter =
                RecyclerGeneralAdapter(
                    data.toArrayList(), R.layout.dashboard_item
                ) { v, itm, pos ->
                    v.findViewById<ImageView>(R.id.dashItem_bgImg)
                        .setImageResource(getDrawableResId(itm.image))
                    v.findViewById<TextView>(R.id.dashItem_titleTv).text = itm.title
                    v.findViewById<TextView>(R.id.dashItem_stitleTv).text = "${itm.stitle} for ${getUserData().skillLevel}"
                    v.setOnClickListener {
                        startActivity(Intent(this, ExercisesActivity::class.java).also {
                            it.putExtra("data", itm.levels[getUserData().skillLevel])
                            it.putExtra("header", itm)
                        })
                    }
                }
        dashboardList.layoutManager = LinearLayoutManager(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_logout -> {
                signOut()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun signOut(){
        FirebaseAuth.getInstance().signOut()
        finish()
        startActivity(Intent(this,SplashActivity::class.java))
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            initList()
        }
    }
}
