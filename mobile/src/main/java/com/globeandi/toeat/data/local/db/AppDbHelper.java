package com.globeandi.toeat.data.local.db;

import com.globeandi.toeat.data.models.db.Group;
import com.globeandi.toeat.data.models.db.Trip;
import com.globeandi.toeat.data.models.db.User;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

/**
 * Created by jay on 3/20/2018.
 * manage / implement all the database transactions
 * create task for db call
 */
@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public boolean insertUser(final User user) {
        mAppDatabase.userDao().insert(user);
        return true;
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return null;
    }

    @Override
    public Observable<Boolean> insertGroup(final Group group) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.groupDao().insert(group);
                return true;
            }
        });
    }

    @Override
    public Observable<List<User>> getAllGroups() {
        return null;
    }

    @Override
    public Observable<Boolean> insertTrip(final Trip trip) {
        return Observable.fromCallable(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                mAppDatabase.tripDao().insert(trip);
                return true;
            }
        });
    }

    @Override
    public Observable<List<Trip>> getAllTrips() {
        return null;
    }
}
