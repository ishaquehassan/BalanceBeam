package ishaquehassan.balancebeam.models

class UserData(val name:String,val email:String,val age:Int,val weight:Float,val skillLevel:String){
    constructor():this("","",0,0f,"")
}