package hu.bme.aut.android.swipeajob.Adapters.CardStackViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.JobSearcherWithJobNameAndId
import hu.bme.aut.android.swipeajob.R

class CardStackAdapterJobProvider(val listener: JobSearcherItemClickedListener,
    private var jobsearchers: MutableList<JobSearcherWithJobNameAndId> = mutableListOf<JobSearcherWithJobNameAndId>()
) : RecyclerView.Adapter<CardStackAdapterJobProvider.ViewHolder>() {

    interface JobSearcherItemClickedListener
    {
        fun JobSearcherClicked(jobsearcher: JobSearcher)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.cardstack_item_jobprovider,
            parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jobsearcher = jobsearchers[position]
        holder.name.text = "Name: ${jobsearcher.jobsearcher.fullname}"
        holder.jobName.text = "Applied For: ${jobsearcher.jobName}"
        Glide.with(holder.image)
            .load(jobsearcher.jobsearcher.pictureUri)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->

            val jobsearcherThatWasClicked = jobsearcher.jobsearcher
            listener.JobSearcherClicked(jobsearcherThatWasClicked)
        }
    }

    override fun getItemCount(): Int {
        return jobsearchers.size
    }

    fun setJobSearchers(jobsearchers: List<JobSearcherWithJobNameAndId>) {
        val list : MutableList<JobSearcherWithJobNameAndId> = mutableListOf<JobSearcherWithJobNameAndId>()
        list.addAll(jobsearchers)
        this.jobsearchers = list
        notifyDataSetChanged()
    }

    fun getJobSearchers(): List<JobSearcherWithJobNameAndId> {
        return jobsearchers
    }

    fun getJobSearcher(index : Int): JobSearcherWithJobNameAndId {
        return jobsearchers[index]
    }

    fun removeJobSearcher(index : Int){
        jobsearchers.removeAt(index)
        notifyItemRemoved(index)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.jobsearcher_name)
        val jobName: TextView = view.findViewById(R.id.job_name)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}