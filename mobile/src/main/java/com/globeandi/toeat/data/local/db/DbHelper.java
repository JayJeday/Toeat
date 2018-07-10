package com.globeandi.toeat.data.local.db;

import com.globeandi.toeat.data.models.db.Group;
import com.globeandi.toeat.data.models.db.Trip;
import com.globeandi.toeat.data.models.db.User;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by jay on 3/17/2018.
 *
 * list all the sql transactions that need to be implemented
 */

public interface DbHelper {

    //users queries
    boolean insertUser(final User user);

    Observable<List<User>> getAllUsers();

    //groups queries
    Observable<Boolean> insertGroup(final Group group);

    Observable<List<User>> getAllGroups();


    //trips queries
    Observable<Boolean> insertTrip(final Trip trip);

    Observable<List<Trip>> getAllTrips();
}
