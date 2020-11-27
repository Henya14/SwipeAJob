package hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.Match
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem
import hu.bme.aut.android.swipeajob.R

class JobsRecyclerViewAdapter(val context: Context,
                            val jobItemRemovedListener: JobItemRemovedListener)
    :RecyclerView.Adapter<JobsRecyclerViewAdapter.JobsViewHolder>() {

    var jobs = mutableListOf<Job>()
    interface JobItemRemovedListener{
        fun jobItemRemoved(jobItem: Job)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobsViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_jobs_list, parent, false)
        return JobsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: JobsViewHolder, position: Int) {
        val job  = jobs[position]

        Glide.with(context).load(job.pictureUri).into(holder.jobPicture)
        holder.jobName.text = job.name
        holder.jobDescription.text = job.description
        holder.item = job

    }

    override fun getItemCount(): Int = jobs.size

    inner class JobsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val jobPicture = itemView.findViewById<ImageView>(R.id.jobPicture)
        val jobName = itemView.findViewById<TextView>(R.id.jobNameTextView)
        val jobDescription = itemView.findViewById<TextView>(R.id.jobDescriptionTextView)
        val deleteButton = itemView.findViewById<ImageButton>(R.id.jobItemRemoveButton)
        var item : Job? = null

        init {
            deleteButton.setOnClickListener{
                    item?.let {
                        jobItemRemovedListener.jobItemRemoved(it)
                        removeItem(it)
                    }

            }
        }


    }


    fun setItems(items: List<Job>)
    {
        val mutableItems = mutableListOf<Job>()
        mutableItems.addAll(items)
        jobs = mutableItems
        notifyDataSetChanged()
    }

    private fun removeItem(item: Job) {
        val itemIdx: Int = jobs.indexOf(item)
        jobs.removeAt(itemIdx)
        notifyItemRemoved(itemIdx)
    }



}