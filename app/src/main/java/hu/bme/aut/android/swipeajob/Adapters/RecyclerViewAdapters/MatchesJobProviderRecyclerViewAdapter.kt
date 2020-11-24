package hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.swipeajob.Adapters.RecyclerViewAdapters.ViewHolders.MatchesViewHolder
import hu.bme.aut.android.swipeajob.Data.QueryHelperClasses.Match
import hu.bme.aut.android.swipeajob.R


class MatchesJobProviderRecyclerViewAdapter(val context: Context)
    : RecyclerView.Adapter<MatchesViewHolder>() {

    var matches = mutableListOf<Match>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_matches_list, parent, false)
        return MatchesViewHolder(itemView, ::onMatchClicked)
    }

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        val match = matches[position]

        if(match.imageUri != null) {
            holder.image.setImageURI(Uri.parse(match.imageUri))
        }
        holder.name.text = "name: ${match.jobsearchername}\n" + "applied for: ${match.jobname}"
        holder.item = match
    }

    override fun getItemCount(): Int = matches.size

    fun addItem(item: Match)
    {
        matches.add(item)
        notifyItemInserted(matches.size - 1)
    }

    fun addItems(items: List<Match>)
    {
        matches.addAll(items)
        notifyDataSetChanged()
    }

    fun setItems(items: MutableList<Match>)
    {
        matches = items
        notifyDataSetChanged()
    }



    fun onMatchClicked(match: Match)
    {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:" + match.jobsearcherPhoneNumber)
        context.startActivity(intent)
    }


}