package me.shreyasr.elemental.field.entities.Effects;

/**
 * Created by Benjamin on 5/24/2015.
 */
public abstract class Effect {
    double powerLevel;
    double duration;
    public Effect(double powerLevel, double duration){
        this.powerLevel = powerLevel;
        this.duration = duration;
    }
    public double getAttack(double org){return org;}
    public double getHealth(double org){return org;}
    public double getSpeed(double org){return org;}

    public boolean subtractDuration(double sec){
        duration -= sec;
        return duration < 0;
    }
}