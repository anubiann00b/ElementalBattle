package me.shreyasr.elemental.field.entities.Effects;

/**
 * Created by Benjamin on 5/24/2015.
 */
public class Damage extends Effect{
    public Damage(double powerLevel, double duration){
        super(powerLevel,duration);
    }
    public double getAttack(double org){return powerLevel*org;}

}