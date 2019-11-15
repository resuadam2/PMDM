package com.resuadam.droidseries;

public class Serie {
    private int lastSeason;
    private int lastEpisode;
    private String name;

    public Serie(String name) throws IllegalArgumentException
    {
        name = name.trim();

        if ( name.isEmpty() ) {
            throw new IllegalArgumentException( "name cannot be empty" );
        }

        this.lastSeason = 1;
        this.lastEpisode = 1;
        this.name = name;
    }

    /**
     * Resets the episode number
     */
    public void resetEpisode() {
        this.lastEpisode = 1;
    }

    /**
     * Increments the last episode seen
     */
    public void incEpisode() {
        ++this.lastEpisode;
    }

    /** Descrements the last episode seen */
    public void decEpisode() {
        --this.lastEpisode;
    }

    /**
     * Increments the last episode seen-
     */
    public void incSeason() {
        ++this.lastSeason;
        this.lastEpisode = 1;
    }

    /**
     * @return the last season seen, as a number
     */
    public int getLastSeason() {
        return lastSeason;
    }

    /**
     * Changes the last season seen.
     * @param lastSeason the last season, as a number
     */
    public void setLastSeason(int lastSeason) {
        this.lastSeason = lastSeason;
    }

    /**
     * @return the last episode seen, as a number
     */
    public int getLastEpisode() {
        return lastEpisode;
    }

    /**
     * Changes the last episode seen.
     * @param lastEpisode the new episode number.
     */
    public void setLastEpisode(int lastEpisode) {
        this.lastEpisode = lastEpisode;
    }

    /**
     * @return the name of the series
     */
    public String getName() {
        return name;
    }

    /**
     * @return a textual identifier based on the name, for example:
     * The last ship -> the_last_ship
     */
    public String getId() {
        String id = this.getName();
        StringBuilder toret = new StringBuilder();

        id = id.toLowerCase().replace( ' ', '_' );

        for(int i = 0; i < id.length(); ++i) {
            char ch = id.charAt( i );

            if ( ( ch < 'a'
                    || ch > 'z' )
                    && ( ch < '0'
                    || ch > '9' )
                    && ch != '_' )
            {
                ch = '_';
            }

            toret.append( ch );
        }

        return toret.toString();
    }

    /**
     * @return the last episode and season encoded in a single integer
     * 2x03 -> 2003
     */
    public int getCodedEpisode() {
        return this.getLastSeason() * 1000 + this.getLastEpisode();
    }

    /**
     * Sets the episode in coded form, i.e., 2003 instead of 2x03
     * @param codedEpisode, an int in the form seasonx100 + episode
     */
    public void setCodedEpisode(int codedEpisode) {
        this.lastEpisode = codedEpisode % 1000;
        this.lastSeason = codedEpisode / 1000;
    }

    @Override
    public String toString() {
        return String.format( "%s %02dx%02d", this.getName(), this.getLastSeason(), this.getLastEpisode() );
    }
}
