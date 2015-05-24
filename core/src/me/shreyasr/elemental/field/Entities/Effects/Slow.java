package me.shreyasr.elemental.field.entities.Effects;

/**
 * Created by Benjamin on 5/24/2015.
 */
public class Slow extends Effect{
    public Slow(double powerLevel, double health){
        super(powerLevel,health);
    }
    @Override
    public double getSpeed(double org){
        return org/powerLevel;
    }
}
