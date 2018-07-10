package com.globeandi.toeat.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.globeandi.toeat.data.models.db.Group;

import java.util.List;


/**
 * Created by jay on 3/20/2018.
 */
@Dao
public interface GroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Group group);

    @Delete
    void delete(Group... groups);

    @Query("SELECT * FROM groups")
    List<Group>loadAllGroups();

    @Update
    void updateGroups(Group group);
}
