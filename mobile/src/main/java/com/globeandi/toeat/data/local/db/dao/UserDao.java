package com.globeandi.toeat.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.globeandi.toeat.data.models.db.User;

import java.util.List;

/**
 * Created by jay on 3/20/2018.
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> users);

    @Delete
    void delete(User... users);

    @Update
    void updateUsers(User...users);

    @Query("SELECT * FROM users")
    List<User> loadAllUsers();
}
