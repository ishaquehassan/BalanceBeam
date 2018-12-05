package ishaquehassan.balancebeam.base

import android.content.Context
import android.util.AttributeSet
import com.smarteist.autoimageslider.SliderLayout

class CustomSliderView : SliderLayout{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr:Int): super(context, attrs,defStyleAttr)

    var pageChangeListener:(Int)->Unit = {}

    override fun onCurrentPageChanged(currentPosition: Int) {
        super.onCurrentPageChanged(currentPosition)
        pageChangeListener(currentPosition)
    }
}