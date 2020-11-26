package hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.ViewHolders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.Match
import hu.bme.aut.android.swipeajob.R

class MatchesViewHolder(itemView: View, val onClickFunction: (Match)  -> Unit): RecyclerView.ViewHolder(itemView)
{

    val image: ImageView
    val name: TextView
    var item: Match? = null

    init {
        itemView.setOnClickListener{
            onClickFunction(item!!)
        }
        image = itemView.findViewById(R.id.jobOrJobsearcher_image)
        name = itemView.findViewById(R.id.nameTextView)
    }

}