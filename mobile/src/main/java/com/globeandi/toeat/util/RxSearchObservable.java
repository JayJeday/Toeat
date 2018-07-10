package com.globeandi.toeat.util;

import android.widget.SearchView;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

public class RxSearchObservable {

    private RxSearchObservable(){

    }

    /*
     have to make that view observable by implementing the text change listener
    */
    public static Observable<String> fromView(SearchView searchView){

        //start consuming items when subscriptions
        final PublishSubject<String> subject = PublishSubject.create();

        /*
        manage text input in search
        */
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //finish consuming items
                subject.onComplete();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                subject.onNext(s);
                return true;
            }
        });

        return subject;
    }
}
