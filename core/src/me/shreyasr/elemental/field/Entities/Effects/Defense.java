package me.shreyasr.elemental.field.entities.Effects;

public class Defense extends Effect{
    public Defense(double powerLevel, double health){
        super(powerLevel,health);
    }
    @Override
    public int getDef(int org){
        return (int) (org*powerLevel);
    }
}
