package ishaquehassan.balancebeam.base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex



class MyApp : Application(){

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}