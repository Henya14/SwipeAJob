package hu.bme.aut.android.swipeajob.Adapters.CardStackViewAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hu.bme.aut.android.swipeajob.Data.Entities.JobSearcher
import hu.bme.aut.android.swipeajob.R

class CardStackAdapterJobProvider(
    private var jobsearchers: List<JobSearcher> = emptyList()
) : RecyclerView.Adapter<CardStackAdapterJobProvider.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.cardstack_item_jobprovider,
            parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val jobsearcher = jobsearchers[position]
        holder.name.text = "${jobsearcher.fullname}"
        Glide.with(holder.image)
            .load(jobsearcher.pictureUri)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            TODO("JobsearcherDetails")
        }
    }

    override fun getItemCount(): Int {
        return jobsearchers.size
    }

    fun setJobSearchers(jobsearchers: List<JobSearcher>) {
        this.jobsearchers = jobsearchers
    }

    fun getJobSearchers(): List<JobSearcher> {
        return jobsearchers
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.item_name)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}