package hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.ExperienceItem
import hu.bme.aut.android.swipeajob.R

class ExperienceRecyclerViewAdapter(val withButtons : Boolean = true,
                                    val experienceItemRemovedListener: ExperienceItemRemovedListener? = null)
    :RecyclerView.Adapter<ExperienceRecyclerViewAdapter.ExperienceViewHolder>() {

    interface ExperienceItemRemovedListener{
        fun experienceItemRemoved(experienceItem: ExperienceItem)
    }
    val items = mutableListOf<ExperienceItem>()

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
        val startEndText: TextView = itemView.findViewById(R.id.startEndTextView)
        val companyName: TextView = itemView.findViewById(R.id.companyNameTextView)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.experienceItemRemoveButton)
        var item: ExperienceItem? = null
        init {
            if(!withButtons)
            {
                deleteButton.visibility = View.INVISIBLE
            }
            deleteButton.setOnClickListener{
                item?.let {
                    experienceItemRemovedListener?.experienceItemRemoved(it)
                    removeItem(it)
                }
            }

        }


    }
    fun addItem(item: ExperienceItem)
    {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(itemslist: List<ExperienceItem>)
    {
        items.addAll(itemslist)
        notifyDataSetChanged()
    }

    fun removeItem(item: ExperienceItem) {
        val itemIdx: Int = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(itemIdx)
    }


}
