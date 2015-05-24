package me.shreyasr.elemental.field.entities;

/**
 * Created by Benjamin on 5/23/2015.
 */
public class Player implements Entity{
    public double health;
    @Override
    public void takeDamage(Attack a){
        health-=a.baseDamage;

        if(health < 0)
            die();
    }
    @Override
    public void die(){

    }
}