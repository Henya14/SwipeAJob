package hu.bme.aut.android.swipeajob.Adapters.CardStackViewAdapter

import androidx.recyclerview.widget.DiffUtil
import hu.bme.aut.android.swipeajob.Data.Spot

class SpotDiffCallback(
    private val old: List<Spot>,
    private val new: List<Spot>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].id == new[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}