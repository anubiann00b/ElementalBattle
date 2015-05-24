package me.shreyasr.elemental.field.entities;

/**
 * Created by Benjamin on 5/24/2015.
 */
public abstract class Effect {
    private double duration;
    public Effect(double duration){
        this.duration = duration;
    }
    public abstract void applyEffect(Monster m);
    public abstract void unapplyEffect(Monster m);
    public boolean subtractDuration(double sec){
        duration -= sec;
        return duration < 0;
    }
}