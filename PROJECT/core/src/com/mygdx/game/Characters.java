package com.mygdx.game;


import com.badlogic.gdx.audio.Sound;

public abstract class Characters {
    private boolean isInfiniteJump = false;
    private double HP = 100.0;
    private double dmg = 10.0;
    private double ratioHP = 1f;

    private float stateTime = 0.0f;
    private float x, y;
    private float dx=0, dy=0, speed=200;
    private Sound takeDamage;

    public boolean isInfiniteJump() {
        return isInfiniteJump;
    }

    public void setInfiniteJump(boolean infiniteJump) {
        isInfiniteJump = infiniteJump;
    }

    public double getHP() {
        return HP;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public double getDmg() {
        return dmg;
    }

    public void setDmg(double dmg) {
        this.dmg = dmg;
    }

    public double getRatioHP() {
        return ratioHP;
    }

    public void setRatioHP(double ratioHP) {
        this.ratioHP = ratioHP;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getDx() {
        return dx;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public Sound getTakeDamage() {
        return takeDamage;
    }

    public void setTakeDamage(Sound takeDamage) {
        this.takeDamage = takeDamage;
    }

    public boolean hit (Characters players) {return true;}
}
