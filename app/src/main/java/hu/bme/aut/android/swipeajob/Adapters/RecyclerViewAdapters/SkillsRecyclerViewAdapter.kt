package hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.SkillItem
import hu.bme.aut.android.swipeajob.R

class SkillsRecyclerViewAdapter : RecyclerView.Adapter<SkillsRecyclerViewAdapter.SkillsViewHolder>() {

    val items = mutableListOf<SkillItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillsViewHolder {
        val itemView :View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_skills_list, parent, false)
        return SkillsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SkillsViewHolder, position: Int) {
        val item = items[position]
        holder.skillText.text = item.skillName
        holder.item = item
    }

    override fun getItemCount(): Int = items.size


    inner class SkillsViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val skillText: TextView
        val deleteButton: ImageButton
        var item: SkillItem? = null
        init {

            skillText = itemView.findViewById(R.id.skillTextView)
            deleteButton = itemView.findViewById(R.id.skillItemRemoveButton)
            deleteButton.setOnClickListener{
                item?.let {
                    removeItem(it)
                }
            }

        }


    }

    fun addItem(item: SkillItem)
    {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(item: SkillItem) {
        val itemIdx: Int = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(itemIdx)
    }

}


