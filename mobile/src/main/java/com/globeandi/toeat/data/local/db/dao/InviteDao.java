package com.globeandi.toeat.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import com.globeandi.toeat.data.models.db.Invite;


@Dao
public interface InviteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Invite invite);
}
