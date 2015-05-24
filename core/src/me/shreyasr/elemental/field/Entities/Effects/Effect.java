package me.shreyasr.elemental.field.entities.Effects;

public class Effect {

    double powerLevel;
    double duration;

    public Effect(double powerLevel, double duration) {
        this.powerLevel = powerLevel;
        this.duration = duration;
    }

    public int getAttack(int org){return org;}
    public int getDef(int org){return org;}
    public double getSpeed(double org){return org;}

    public boolean subtractDuration(double sec) {
        duration -= sec;
        return duration < 0;
    }
    public Effect clone(){
        return (Effect)clone();
    }
}