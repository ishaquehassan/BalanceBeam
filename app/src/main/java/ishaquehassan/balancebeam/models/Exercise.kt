package ishaquehassan.balancebeam.models

import java.io.Serializable

class Exercise (val title:String, val stitle:String, val image:String,val video:String): Serializable {
    constructor():this("","","","")
}