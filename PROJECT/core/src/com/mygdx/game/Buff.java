package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class Buff {

    private float x, y;
    MyGdxGame app;
    Sprite Heal, AttackBuff, Armor;
    float stateTime = 0.0f;


    enum state {
        TAKEABLE, TAKED;
    }

    state State = state.TAKEABLE;

    public void powerUp(){
        generateBuff();
    }

    public void generateBuff() {
        app = (MyGdxGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = app.getManager();

        Texture heal = assetManager.get("Heal.png");
        Texture atkUp = assetManager.get("AtkUp.png");
        Texture armor = assetManager.get("Armor.png");

        Heal = new Sprite(heal);
        Heal.setColor(Color.GREEN);

        AttackBuff = new Sprite(atkUp);
        AttackBuff.setColor(Color.RED);

        Armor = new Sprite(armor);
        Armor.setColor(Color.WHITE);

    }


    public void power(SpriteBatch spriteBatch, int x) {
        if (State == state.TAKEABLE) {
            if (x == 0) {
                spriteBatch.draw(Heal, this.x - 24, this.y);
            }
            if (x == 1) {
                spriteBatch.draw(AttackBuff, this.x,this.y);
            }
            if (x == 2) {
                spriteBatch.draw(Armor, this.x, this.y);
            }
        }

    }


    boolean isTaken(User1 user1) {
        return user1.getX() - x <= 20 && user1.getX() - x >= -20 && user1.getY() - y >= -20 && user1.getY() - y <= 20;

    }

    boolean isTaken(User2 user2) {
        return user2.getX() - x <= 20 && user2.getX() - x >= -20 && user2.getY() - y >= -20 && user2.getY() - y <= 20;

    }

    void onTaken(User1 user1){

    }
    void onTaken(User2 user2){

    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }
}






