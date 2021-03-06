package com.joss.voodootvdb.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.View;

import com.joss.voodootvdb.R;
import com.joss.voodootvdb.activities.ShowActivity;
import com.joss.voodootvdb.adapters.HomeAdapter;
import com.joss.voodootvdb.api.Api;
import com.joss.voodootvdb.api.ApiService;
import com.joss.voodootvdb.api.models.Movie.Movie;
import com.joss.voodootvdb.api.models.People.Cast;
import com.joss.voodootvdb.api.models.Show.Show;
import com.joss.voodootvdb.interfaces.VoodooClickListener;
import com.joss.voodootvdb.interfaces.VoodooItem;
import com.joss.voodootvdb.provider.shows.ShowsProvider;
import com.joss.voodootvdb.provider.shows_popular.ShowsPopularColumns;
import com.joss.voodootvdb.provider.shows_popular.ShowsPopularCursor;
import com.joss.voodootvdb.utils.Utils;
import com.joss.voodootvdb.views.ErrorView;
import com.joss.voodootvdb.views.VoodooHorizontalScrollView;
import com.joss.voodootvdb.views.LoadingView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by: jossayjacobo
 * Date: 3/2/15
 * Time: 5:57 PM
 */
public class HomeFragment extends BaseListFragment implements
        LoaderManager.LoaderCallbacks<Cursor>, VoodooClickListener {

    public static final String TAG = HomeFragment.class.getSimpleName();
    private static final int POPULAR_SHOWS_CALLBACK = 456;

    @Override
    View getLoadingView() {
        return new LoadingView(getActivity());
    }

    @Override
    View getErrorView() {
        return new ErrorView(getActivity());
    }

    @Override
    View getEmptyView() {
        return null;
    }

    @Override
    List<Integer> getApiTypes() {
        return Arrays.asList(ApiService.REQUEST_SHOWS_POPULAR);
    }

    @Override
    void onErrorMessageReceived() {
        // TODO if adapter.items.size() == 0 show error view
    }

    HomeAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listener.onSetToolbarTitles(getString(R.string.drawer_home), null);

        adapter = new HomeAdapter(getActivity(), this);
        listView.setAdapter(adapter);

        getLoaderManager().initLoader(POPULAR_SHOWS_CALLBACK, null, this);

        showLoadingView();
        Api.getPopularShows(getActivity());
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                ShowsPopularColumns.CONTENT_URI,
                ShowsPopularColumns.FULL_PROJECTION,
                null,
                null,
                ShowsPopularColumns.DEFAULT_ORDER + " DESC LIMIT 5");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null){
            ShowsPopularCursor cursor = new ShowsPopularCursor(data);
            List<VoodooItem> shows = ShowsProvider.getHomeItems(getActivity(), cursor,
                    VoodooHorizontalScrollView.TYPE_FEATURE,
                    "Featured");
            // TODO Think of good title names

            if(shows.size() > 0) {
                showContent();
                List<List<VoodooItem>> items = new ArrayList<>();
                items.add(shows);
                items.add(ShowsProvider.getHomeItems(getActivity(), cursor, VoodooHorizontalScrollView.TYPE_NORMAL, "Recommended"));
                items.add(ShowsProvider.getHomeItems(getActivity(), cursor, VoodooHorizontalScrollView.TYPE_NORMAL, "Action/Adventure"));
                items.add(ShowsProvider.getHomeItems(getActivity(), cursor, VoodooHorizontalScrollView.TYPE_NORMAL, "New Releases"));

                adapter.setContent(items);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onShowClicked(Show show) {
        Intent intent = new Intent(getActivity(), ShowActivity.class);
        intent.putExtra(ShowActivity.ID, show.getIds().getTrakt());
        startActivity(intent);
    }

    @Override
    public void onShowMenuClicked(Show show) {
        // TODO maybe move this into the actual view??
        Utils.toast(getActivity(), show.getTitle() + " : Show Menu Clicked");
    }

    @Override
    public void onMovieMenuClicked(Movie movie) {

    }

    @Override
    public void onTrailerClicked(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public void onCastClicked(Cast cast) {

    }

    @Override
    public void onMovieClicked(Movie movie){

    }
}
