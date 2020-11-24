package hu.bme.aut.android.swipeajob.Data.Dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import hu.bme.aut.android.swipeajob.Data.CrossReferences.LeftSwipedJobSearchersCrossRef

@Dao
interface LeftSwipedJobSearchersCrossRefDao {

    @Insert
    fun insert(matchesCrossRef: LeftSwipedJobSearchersCrossRef)

    @Delete
    fun delete(matchesCrossRef: LeftSwipedJobSearchersCrossRef)
}