package com.globeandi.toeat.util.rx;

import io.reactivex.Scheduler;

/**
 * Created by jay on 3/23/2018.
 * schedule unit of work by specifying delay,not delays and periodically
 */

public interface SchedulerProvider {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();

}
