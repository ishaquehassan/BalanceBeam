package ishaquehassan.balancebeam.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RecyclerGeneralAdapter<T>(val data:ArrayList<T>,val layoutFile:Int,val onBindItem:(View,T,Int)->Unit) : RecyclerView.Adapter<RecyclerGeneralAdapter<T>.RecyclerGeneralViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerGeneralViewHolder = RecyclerGeneralViewHolder(LayoutInflater.from(parent.context).inflate(layoutFile,parent,false))
    override fun getItemCount(): Int = data.size
    override fun onBindViewHolder(holder: RecyclerGeneralViewHolder, position: Int) = holder.onBindItem(data[position])

    inner class RecyclerGeneralViewHolder(v:View) : RecyclerView.ViewHolder(v){
        fun onBindItem(item:T){
            onBindItem(itemView,item,adapterPosition)
        }
    }

}