package com.globeandi.toeat.data.local.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.globeandi.toeat.data.local.db.dao.GroupDao;
import com.globeandi.toeat.data.local.db.dao.InviteDao;
import com.globeandi.toeat.data.local.db.dao.TripDao;
import com.globeandi.toeat.data.local.db.dao.UserDao;
import com.globeandi.toeat.data.models.db.Group;
import com.globeandi.toeat.data.models.db.Invite;
import com.globeandi.toeat.data.models.db.Trip;
import com.globeandi.toeat.data.models.db.User;

/**
 * it represent the database with their tables
 * Created by jay on 3/20/2018.
 */
@Database(entities = {User.class,Group.class, Trip.class, Invite.class},version = 2)
public abstract class AppDatabase extends RoomDatabase{

    public abstract UserDao userDao();

    public abstract GroupDao groupDao();

    public abstract TripDao tripDao();

    public abstract InviteDao inviteDao();



}
