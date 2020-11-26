package hu.bme.aut.android.swipeajob.Adapters.CardStackViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.swipeajob.Data.Entities.Job
import hu.bme.aut.android.swipeajob.R

class CardStackAdapterJobSearcher (
    private var jobs: MutableList<Job> = mutableListOf()
) : RecyclerView.Adapter<CardStackAdapterJobSearcher.ViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.cardstack_item_jobsearcher,
            parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val job = jobs[position]
        holder.jobname.text = job.name
        holder.jobDescription.text = job.description

        Glide.with(holder.image)
            .load(job.pictureUri)
            .into(holder.image)

    }

    override fun getItemCount(): Int {
        return jobs.size
    }

    fun setJobs(jobs: List<Job>) {
        val list : MutableList<Job> = mutableListOf()
        list.addAll(jobs)
        this.jobs = list
        notifyDataSetChanged()
    }

    fun getJobs(): List<Job> {
        return jobs
    }

    fun getJob(index : Int): Job {
        return jobs[index]
    }

    fun removeJob(index : Int) {

        jobs.removeAt(index)
        notifyItemRemoved(index)
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val jobname: TextView = view.findViewById(R.id.job_name)
        val jobDescription: TextView = view.findViewById(R.id.job_description)
        var image: ImageView = view.findViewById(R.id.item_image)
    }
}