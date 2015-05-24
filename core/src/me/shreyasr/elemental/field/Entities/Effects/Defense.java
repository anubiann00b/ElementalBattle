package me.shreyasr.elemental.field.entities.Effects;

/**
 * Created by Benjamin on 5/24/2015.
 */
public class Defense extends Effect{
    public Defense(double powerLevel, double health){
        super(powerLevel,health);
    }
    @Override
    public double getDef(double org){
        return org*powerLevel;
    }
}
