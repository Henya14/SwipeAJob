package hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.R

class ExperienceRecyclerViewAdapter : RecyclerView.Adapter<ExperienceRecyclerViewAdapter.ExperienceViewHolder>() {

    private val items = mutableListOf<ExperienceItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExperienceViewHolder {
        val itemView :View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_experience_list, parent, false)
        return ExperienceViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val item = items[position]

        holder.startEndText.text = ("${item.startYear}-${item.endYear}")
        holder.companyName.text = item.companyName
        holder.item = item
    }

    override fun getItemCount(): Int = items.size

    inner class ExperienceViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val startEndText: TextView
        val companyName: TextView
        val delteButton: ImageButton
        var item: ExperienceItem? = null
        init {

            startEndText = itemView.findViewById(R.id.startEndTextView)
            companyName = itemView.findViewById(R.id.companyNameTextView)
            delteButton = itemView.findViewById(R.id.experienceItemRemoveButton)

        }


    }

}