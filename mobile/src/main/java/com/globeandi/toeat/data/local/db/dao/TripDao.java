package com.globeandi.toeat.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.globeandi.toeat.data.models.db.Trip;
import com.globeandi.toeat.data.models.db.User;

import java.util.List;

@Dao
public interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Trip trip);

    @Delete
    void delete(Trip...trips);

    @Update
    void updateTrips(Trip...trips);

    @Query("SELECT * FROM trips")
    List<Trip> LoadAllTrips();
}
