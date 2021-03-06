package com.inmersoft.trinidadpatrimonial.database.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.inmersoft.trinidadpatrimonial.database.data.entity.PlaceType
import com.inmersoft.trinidadpatrimonial.database.data.entity.PlaceTypeWithPlaces

@Dao
interface PlaceTypeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(placeType: PlaceType)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(place: List<PlaceType>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(placeType: PlaceType)

    @Query("SELECT * FROM place_types")
    fun getAllPlacesType(): LiveData<List<PlaceType>>

    @Query("SELECT COUNT(*) FROM place_types")
    fun getCantPlacesType(): LiveData<Int>

    @Query("DELETE FROM place_types")
    suspend fun deleteAll()

    @Transaction
    @Query("SELECT * FROM place_types")
    suspend fun getPlaceTypeWithPlaces(): List<PlaceTypeWithPlaces>

    @Transaction
    @Query("SELECT * FROM place_types WHERE place_type_id=:id")
    suspend fun getPlacesTypeById(id: Int): PlaceTypeWithPlaces

}