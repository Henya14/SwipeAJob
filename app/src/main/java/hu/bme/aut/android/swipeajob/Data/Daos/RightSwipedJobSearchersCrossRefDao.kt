package hu.bme.aut.android.swipeajob.Data.Daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import hu.bme.aut.android.swipeajob.Data.CrossReferences.RightSwipedJobSearchersCrossRef

@Dao
interface RightSwipedJobSearchersCrossRefDao {

    @Insert
    fun insert(rightSwipedJobSearchersCrossRef: RightSwipedJobSearchersCrossRef)

    @Delete
    fun delete(rightSwipedJobSearchersCrossRef: RightSwipedJobSearchersCrossRef)

    @Query("SELECT * FROM rightswipedjobsearcherscrossref WHERE jobproviderid = :id")
    fun getAllRightSwipedJobSearchersAndJobIdsForJobProviderWithId(id : Long): List<RightSwipedJobSearchersCrossRef>
}