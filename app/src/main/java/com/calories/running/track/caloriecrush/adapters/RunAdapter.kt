package com.calories.running.track.caloriecrush.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.calories.running.track.caloriecrush.DB.Run
import com.calories.running.track.caloriecrush.R
import com.calories.running.track.caloriecrush.databinding.RunLayoutBinding

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

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

        return viewHolder
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run=differ.currentList[position]
    }
}