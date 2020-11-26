package hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.swipeajob.Data.RegistrationRecyclerViewsData.EducationItem
import hu.bme.aut.android.swipeajob.R

class EducationRecyclerViewAdapter(val withButtons : Boolean = true,
                                   val educationItemRemovedListener: EducationItemRemovedListener? = null)
: RecyclerView.Adapter<EducationRecyclerViewAdapter.EducationViewHolder>() {

    interface EducationItemRemovedListener {
        fun educationItemRemoved(educationItem: EducationItem)
    }
    val items = mutableListOf<EducationItem>()

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
        val graduationYear: TextView = itemView.findViewById(R.id.graduationYearTextView)
        val schoolName: TextView = itemView.findViewById(R.id.schoolNameTextView)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.educationItemRemoveButton)
        var item: EducationItem? = null
        init {


            if(!withButtons)
            {
                deleteButton.visibility = View.INVISIBLE
            }
            deleteButton.setOnClickListener{
                item?.let {
                    educationItemRemovedListener?.educationItemRemoved(it)
                    removeItem(it)
                }
            }

        }


    }

    fun addItem(item: EducationItem)
    {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun addItems(itemslist: List<EducationItem>)
    {
        items.addAll(itemslist)
        notifyDataSetChanged()
    }

    fun removeItem(item: EducationItem) {
        val itemIdx: Int = items.indexOf(item)
        items.remove(item)
        notifyItemRemoved(itemIdx)
    }


}


