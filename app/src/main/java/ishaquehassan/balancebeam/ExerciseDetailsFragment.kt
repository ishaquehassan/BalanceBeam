package ishaquehassan.balancebeam

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import ishaquehassan.balancebeam.base.getDrawableResId
import ishaquehassan.balancebeam.models.Exercise
import pl.droidsonroids.gif.GifImageView
import android.content.Intent
import android.net.Uri
import android.widget.Button


class ExerciseDetailsFragment : DialogFragment(){

    lateinit var exercise: Exercise

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.exercise_dialog,container,false)

        val title = arguments?.getString("title")
        exercise = arguments?.getSerializable("exercise") as Exercise

        v.findViewById<TextView>(R.id.ed_eTitleTv).text = title
        v.findViewById<TextView>(R.id.ed_eDetailsTv).text = "${exercise.title} \n ${exercise.stitle}"
        v.findViewById<GifImageView>(R.id.ed_eImg).setImageResource(context?.getDrawableResId(exercise.image) ?: 0)
        v.findViewById<ImageView>(R.id.ed_closeBtn).setOnClickListener {
            dialog.dismiss()
        }
        if(exercise.video.isEmpty()){
            v.findViewById<Button>(R.id.ed_eVideoBtn).visibility = View.GONE
        }else{
            v.findViewById<Button>(R.id.ed_eVideoBtn).visibility = View.VISIBLE
            v.findViewById<Button>(R.id.ed_eVideoBtn).setOnClickListener {
                startActivity( Intent(Intent.ACTION_VIEW).apply {data = Uri.parse(exercise.video)})
            }
        }
        return v
    }

    override fun onResume() {
        super.onResume()
        dialog.setCancelable(false)
        val params = dialog.window!!.attributes
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }
}