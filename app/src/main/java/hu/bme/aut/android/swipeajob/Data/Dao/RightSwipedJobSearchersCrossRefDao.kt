package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import hu.bme.aut.android.swipeajob.Data.CrossReferences.RightSwipedJobSearchersCrossRef

@Dao
interface RightSwipedJobSearchersCrossRefDao {

    @Insert
    fun insert(rightSwipedJobSearchersCrossRef: RightSwipedJobSearchersCrossRef)

    @Delete
    fun delete(rightSwipedJobSearchersCrossRef: RightSwipedJobSearchersCrossRef)
}