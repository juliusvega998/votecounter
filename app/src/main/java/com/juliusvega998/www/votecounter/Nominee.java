package com.juliusvega998.www.votecounter;

/**
 * Created by VegaCentre on 4/21/2017.
 */

public class Nominee {
    private String name;
    private int votes;

    public Nominee(String name) {
        this.name = name;
        this.votes = 0;
    }

    public void vote(){ this.votes++; }
    public void undo(){ this.votes--; }
    public void reset() { this.votes = 0; }
    public String getName(){ return this.name; }
    public int getVotes(){ return this.votes; }
}
