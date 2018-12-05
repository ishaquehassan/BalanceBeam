package ishaquehassan.balancebeam

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import ishaquehassan.balancebeam.base.*
import ishaquehassan.balancebeam.models.Exercise
import ishaquehassan.balancebeam.models.ExerciseCategory
import kotlinx.android.synthetic.main.activity_exercises.*

class ExercisesActivity : AppCompatActivity() {

    private lateinit var exercises:Array<Exercise>
    private lateinit var exercisesHeader: ExerciseCategory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercises)
        exercises = intent.getSerializableExtra("data") as Array<Exercise>
        exercisesHeader = intent.getSerializableExtra("header") as ExerciseCategory
        title = exercisesHeader.title+" Exercises"

        exHead_bgImg.setImageResource(getDrawableResId(exercisesHeader.image))
        exHead_titleTv.text = exercisesHeader.title
        exHead_stitleTv.text = "${exercisesHeader.stitle} for ${getUserData().skillLevel}"

        exercisesList.adapter =
                RecyclerGeneralAdapter(
                    exercises.toArrayList(), R.layout.exercise_item
                ) { v, i, p ->
                    v.findViewById<TextView>(R.id.exItem_countText).text = (p + 1).toString()
                    v.findViewById<TextView>(R.id.exItem_title).text = i.title
                    v.findViewById<TextView>(R.id.exItem_stitle).text = i.stitle
                    v.setOnClickListener {
                        val ft = supportFragmentManager.beginTransaction()
                        val preFrag = supportFragmentManager.findFragmentByTag("ExerciseDialog")
                        if(preFrag != null){
                            ft.remove(preFrag)
                        }
                        ft.addToBackStack(null)
                        ExerciseDetailsFragment().apply {
                            arguments = Bundle().apply {
                                putString("title",exercisesHeader.title)
                                putSerializable("exercise",i)
                            }
                        }.show(ft,"ExerciseDialog")
                    }
                }
        exercisesList.addDivider()
        exercisesList.layoutManager = LinearLayoutManager(this)

        exHead_dietPlanBtn.setOnClickListener {
            startActivity(Intent(this,DietPlanActivity::class.java).apply {
                putExtra("isLean",true)
            })
        }
        exHead_dietPlanBtnBulk.setOnClickListener {
            startActivity(Intent(this,DietPlanActivity::class.java))
        }
    }
}
