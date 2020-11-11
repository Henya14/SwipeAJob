package hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.R

class EducationRecyclerViewAdapter : RecyclerView.Adapter<EducationRecyclerViewAdapter.EducationViewHolder>() {

    private val items = mutableListOf<EducationItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EducationViewHolder {
        val itemView :View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_education_list, parent, false)
        return EducationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EducationViewHolder, position: Int) {
        val item = items[position]

        holder.graduationYear.text = "${item.graduationYear}"
        holder.schoolName.text = item.schoolName
        holder.item = item
    }

    override fun getItemCount(): Int  = items.size



    inner class EducationViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
    {
        val graduationYear: TextView
        val schoolName: TextView
        val deleteButton: ImageButton
        var item: EducationItem? = null
        init {

            graduationYear = itemView.findViewById(R.id.graduationYearTextView)
            schoolName = itemView.findViewById(R.id.schoolNameTextView)
            deleteButton = itemView.findViewById(R.id.educationItemRemoveButton)
            deleteButton.setOnClickListener{
                item?.let {
                    removeItem(it)
                    //TODO: onItemDeleted
                }
            }

        }


    }

    fun addItem(item: EducationItem)
    {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun removeItem(item: EducationItem) {
        val itemIdx: Int = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(itemIdx)
    }


}


