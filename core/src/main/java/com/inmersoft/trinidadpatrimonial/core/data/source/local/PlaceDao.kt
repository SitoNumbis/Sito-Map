package com.inmersoft.trinidadpatrimonial.core.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.inmersoft.trinidadpatrimonial.core.data.entity.Place

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(place: Place)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(place: Place)

    @Query("SELECT * FROM places")
    fun getAllPlaces(): LiveData<List<Place>>

    @Query("DELETE FROM places")
    suspend fun deleteAll()

}