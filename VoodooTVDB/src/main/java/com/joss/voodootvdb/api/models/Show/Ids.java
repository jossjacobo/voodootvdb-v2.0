
package com.joss.voodootvdb.api.models.Show;

import com.google.gson.annotations.Expose;

public class Ids {

    @Expose
    private Integer trakt;
    @Expose
    private String slug;
    @Expose
    private Integer tvdb;
    @Expose
    private String imdb;
    @Expose
    private Integer tmdb;
    @Expose
    private Integer tvrage;

    /**
     * 
     * @return
     *     The trakt
     */
    public Integer getTrakt() {
        return trakt == null ? 0 : trakt;
    }

    /**
     * 
     * @param trakt
     *     The trakt
     */
    public Ids setTrakt(Integer trakt) {
        this.trakt = trakt;
        return this;
    }

    /**
     * 
     * @return
     *     The slug
     */
    public String getSlug() {
        return slug == null ? "" : slug;
    }

    /**
     * 
     * @param slug
     *     The slug
     */
    public void setSlug(String slug) {
        this.slug = slug;
    }

    /**
     * 
     * @return
     *     The tvdb
     */
    public Integer getTvdb() {
        return tvdb == null ? 0 : tvdb;
    }

    /**
     * 
     * @param tvdb
     *     The tvdb
     */
    public void setTvdb(Integer tvdb) {
        this.tvdb = tvdb;
    }

    /**
     * 
     * @return
     *     The imdb
     */
    public String getImdb() {
        return imdb == null ? "" : imdb;
    }

    /**
     * 
     * @param imdb
     *     The imdb
     */
    public void setImdb(String imdb) {
        this.imdb = imdb;
    }

    /**
     * 
     * @return
     *     The tmdb
     */
    public Integer getTmdb() {
        return tmdb == null ? 0 : tmdb;
    }

    /**
     * 
     * @param tmdb
     *     The tmdb
     */
    public void setTmdb(Integer tmdb) {
        this.tmdb = tmdb;
    }

    /**
     * 
     * @return
     *     The tvrage
     */
    public Integer getTvrage() {
        return tvrage == null ? 0 : tvrage;
    }

    /**
     * 
     * @param tvrage
     *     The tvrage
     */
    public void setTvrage(Integer tvrage) {
        this.tvrage = tvrage;
    }

}
