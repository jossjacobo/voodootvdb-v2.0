package com.joss.voodootvdb.interfaces;

import com.joss.voodootvdb.api.models.Movie.Movie;
import com.joss.voodootvdb.api.models.People.Cast;
import com.joss.voodootvdb.api.models.Show.Show;

/**
 * Created by: jossayjacobo
 * Date: 3/6/15
 * Time: 1:30 PM
 */
public interface VoodooClickListener {

    public void onShowClicked(Show show);

    public void onTrailerClicked(String url);

    public void onCastClicked(Cast cast);

    public void onMovieClicked(Movie movie);

    public void onShowMenuClicked(Show show);

    public void onMovieMenuClicked(Movie movie);

}
