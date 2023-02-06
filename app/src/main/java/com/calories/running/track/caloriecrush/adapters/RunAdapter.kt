package com.calories.running.track.caloriecrush.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.calories.running.track.caloriecrush.DB.Run
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.RunLayoutBinding
import com.calories.running.track.caloriecrush.other.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RunAdapter(val listner: onItemClick, val longClickListner: onLongClickInterface) :
    RecyclerView.Adapter<RunAdapter.RunViewHolder>() {


    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = RunLayoutBinding.bind(itemView)


    }

    //Checking if Two Lists differs or not
    val diffCallBAck = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer(this, diffCallBAck)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val viewHolder = RunViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.run_layout, parent, false)
        )

        viewHolder.binding.runCardView.setOnLongClickListener {
            longClickListner.onLongClick(
                differ.currentList[viewHolder.adapterPosition],
                viewHolder.itemView
            )
            return@setOnLongClickListener true
        }
//        viewHolder.itemView.setOnLongClickListener(object:AdapterView.OnItemLongClickListener,
//            View.OnLongClickListener {
//
//            override fun onLongClick(p0: View?): Boolean {
//                isSelected=true
//                if(selectItems.contains(differ.currentList.get(viewHolder.adapterPosition))){
//                    viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT)
//                    selectItems.remove(differ.currentList.get(viewHolder.adapterPosition))
//                }else{
//                    viewHolder.itemView.setBackgroundResource(R.color.selectedColor)
//
//                    selectItems.add(differ.currentList.get(viewHolder.adapterPosition))
//                }
//
//                if(selectItems.size==0){
//                    isSelected=false
//                }
//                return true
//            }
//
//            override fun onItemLongClick(
//                p0: AdapterView<*>?,
//                p1: View?,
//                p2: Int,
//                p3: Long
//            ): Boolean {
//               return true
//            }
//
//        })

//        viewHolder.itemView.setOnClickListener(object:AdapterView.OnItemClickListener,
//            View.OnClickListener {
//            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onClick(p0: View?) {
//                if(isSelected){
//                    if(selectItems.contains(differ.currentList.get(viewHolder.adapterPosition))){
//                        viewHolder.itemView.setBackgroundColor(Color.TRANSPARENT)
//                        selectItems.remove(differ.currentList.get(viewHolder.adapterPosition))
//                    }else{
//                        viewHolder.itemView.setBackgroundResource(R.color.selectedColor)
//
//                        selectItems.add(differ.currentList.get(viewHolder.adapterPosition))
//                    }
//
//                    if(selectItems.size==0){
//                        isSelected=false
//                    }
//                }else{
//
//                }
//            }
//
//        })

        viewHolder.binding.runCardView.setOnClickListener {
            listner.onClick(differ.currentList[viewHolder.adapterPosition], viewHolder.itemView)

        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]

        Glide.with(holder.itemView).load(run.img).into(holder.binding.imageView)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }

        val dateFormat = SimpleDateFormat("MMM dd, yy", Locale.getDefault())
        holder.binding.runDate.text = dateFormat.format(calendar.time)

        val avgSpeed = "${run.avgSpeedInKMH}km/h"
        holder.binding.runAvgSpeed.text = avgSpeed

        val distanceInKm = "${(run.distanceInMeters) / 1000f} KM"
        holder.binding.runKmRan.text = distanceInKm

        holder.binding.runDuration.text =
            TrackingUtility.getFormattedStopwatchTime(run.timeInMillis)

        val calBurned = "${run.caloriesBurned}Cal"
        holder.binding.runCalBurned.text = calBurned

        holder.binding.runCardView.startAnimation(
            AnimationUtils.loadAnimation(
                holder.itemView.context,
                R.anim.recyclerview_anim
            )
        )
    }
}

interface onItemClick {
    fun onClick(
        run: Run,
        itemView: View
    )
}

interface onLongClickInterface {
    fun onLongClick(
        run: Run,
        itemView: View
    )

}