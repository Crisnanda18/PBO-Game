package com.mygdx.game;

import com.badlogic.gdx.audio.Sound;

public abstract class Characters {
    private double health = 100.0;
    private double damage = 10.0;
    private double ratioHP = 1f;

    private float stateTime = 0.0f;
    private float x, y;

    private float dx=0, dy=0, speed=200;

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
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
}
