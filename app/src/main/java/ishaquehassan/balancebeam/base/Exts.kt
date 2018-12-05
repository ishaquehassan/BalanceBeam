package ishaquehassan.balancebeam.base

import android.content.Context
import android.support.design.widget.Snackbar
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import ishaquehassan.balancebeam.models.DietPlanModel
import ishaquehassan.balancebeam.models.ExerciseCategory
import ishaquehassan.balancebeam.models.UserData
import org.json.JSONObject
import java.io.IOException
import java.util.*

fun Context.loadFileFromAssets(inFile: String): String {
    var tContents = "{}"

    try {
        val stream = assets.open(inFile)

        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        tContents = String(buffer)
    } catch (e: IOException) {
        // Handle exceptions here
    }

    return tContents
}

fun Context.getAllExercises():Array<ExerciseCategory> = Gson().fromJson(JSONObject(loadFileFromAssets("data.json")).getString("exercises"),Array<ExerciseCategory>::class.javaObjectType)
fun Context.getAllDiseases():Array<ExerciseCategory> =Gson().fromJson(JSONObject(loadFileFromAssets("data.json")).getString("patients"),Array<ExerciseCategory>::class.javaObjectType)
fun Context.getBulkDietPlan():Array<DietPlanModel> =Gson().fromJson(JSONObject(loadFileFromAssets("data.json")).getString("bulk_diet_plan"),Array<DietPlanModel>::class.javaObjectType)
fun Context.getLeanDietPlan():Array<DietPlanModel> =Gson().fromJson(JSONObject(loadFileFromAssets("data.json")).getString("lean_diet_plan"),Array<DietPlanModel>::class.javaObjectType)
fun Context.getWeightLooseProgram():ExerciseCategory =Gson().fromJson(JSONObject(loadFileFromAssets("data.json")).getString("weight_loose_program"),ExerciseCategory::class.javaObjectType)

fun <T> Array<T>.toArrayList():ArrayList<T> = this.toCollection(ArrayList())

fun Context.getDrawableResId(drawable: String):Int = resources.getIdentifier(drawable, "drawable", packageName)

fun Context.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun RecyclerView.addDivider(){
    addItemDecoration(
        DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )
    )
}

fun View.snackMessage(msg:String,actionText:String? = null,onActionClick:(View)->Unit = {}){
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).also {
        if(actionText != null){
            it.setAction(actionText,onActionClick)
        }
        it.show()
    }
}


fun getUserData():UserData = Gson().fromJson(FirebaseAuth.getInstance().currentUser?.displayName ?: "{}",UserData::class.javaObjectType)