package com.globeandi.toeat.ui.search;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.SearchView;

import com.globeandi.toeat.BR;
import com.globeandi.toeat.R;
import com.globeandi.toeat.data.models.api.SearchResponse;
import com.globeandi.toeat.databinding.ActivitySearchBinding;
import com.globeandi.toeat.ui.base.BaseActivity;
import com.globeandi.toeat.util.RxSearchObservable;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends BaseActivity<ActivitySearchBinding, SearchViewModel> implements SearchUserItemNavigator{

    private Toolbar mSearchToolbar;
    private SearchView mSearchView;

    @Inject
    SearchViewModel mSearchViewModel;

    @Inject
    SearchUserAdapter mSearchUserAdapter;

    ActivitySearchBinding mSearchBinding;

    public static final String EXTRA_GROUP_ID = "GROUP_ID";

    private static final String TAG = "SearchActivity";

    @Override
    public int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public SearchViewModel getViewModel() {
        return mSearchViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchBinding = getViewDataBinding();
        setToolbar();

        this.getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        setupSearchUserRV();

        //listener setter
        mSearchUserAdapter.setUserItemNavigator(this);
        mSearchViewModel.setNavigator(this);

        subscribeToLiveData();

        //store groupId
        mSearchViewModel.groupId.set(getIntent().getLongExtra(EXTRA_GROUP_ID,0));
        getWindow().getDecorView().setSystemUiVisibility(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
        mSearchView.setSearchableInfo(searchableInfo);

        //open search immediately
        mSearchView.setIconified(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mSearchViewModel.searchUser(mSearchViewModel.groupId.get(),s);
                mSearchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }


    /*
    navigate back trip activity
     */
    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return true;
    }


    private void setToolbar() {
        mSearchToolbar = mSearchBinding.searchToolbar;
        setSupportActionBar(mSearchToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setupSearchUserRV() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mSearchBinding.rvUserSearch.setLayoutManager(linearLayoutManager);

        mSearchBinding.rvUserSearch.setAdapter(mSearchUserAdapter);
    }

    /*
    Fill list of search
     */
    private void subscribeToLiveData(){
        mSearchViewModel.getUserListLiveData().observe(this,users -> mSearchUserAdapter.submitList(users));
    }

    @Override
    public void onFragmentAttached() {

    }

    @Override
    public void onFragmentDetached(String tag) {

    }

    @SuppressLint("CheckResult")
    private void searchViewObserve() {
        RxSearchObservable.fromView(mSearchView)
                //milli-seconds until the item is trigger
                .debounce(300,TimeUnit.MILLISECONDS)
                //eliminate whitespace
                .filter((text)-> !text.isEmpty())
                //prevent repeated calls
                .distinctUntilChanged()
                //provide the results of the last search
                .switchMap(new Function<String, Observable<SearchResponse>>() {
                    @Override
                    public Observable<SearchResponse> apply(String s) throws Exception {
                        //this will return an observable string that will be emitted bi the observable source
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SearchResponse>() {
                    @Override
                    public void accept(SearchResponse s) throws Exception {
                        //do something with the user selected string
                        //in my case present user and option to view his profile or invite to group button
                       mSearchUserAdapter.submitList(s.getNames());
                    }
                });

    }


    @Override
    public void onSendInviteClicked(SearchResponse.User user, ObservableField<Boolean> isInvited) {
        mSearchViewModel.inviteUser(user.getId(), isInvited);

    }

    @Override
    public void onSendUninviteClicked(SearchResponse.User user, ObservableField<Boolean> isInvited) {
        mSearchViewModel.deleteInvite(user.getId(),isInvited);
    }

    @Override
    public void onViewProfileClicked(SearchResponse.User user) {
        //Open profile activity in a intent

    }

    @Override
    public void handleError(Throwable throwable) {

    }

}
