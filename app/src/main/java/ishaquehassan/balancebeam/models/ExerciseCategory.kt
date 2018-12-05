package ishaquehassan.balancebeam.models

import java.io.Serializable

open class ExerciseCategory (val title:String, val stitle:String, val image:String,val levels:Map<String,Array<Exercise>>):Serializable{
    constructor():this("","","", mapOf())
}