package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

abstract class PowerUp {
   float y = MyGdxGame.WORLD_HEIGHT - 1;
   float x = 0;
   MyGdxGame app;
   Sprite heal, attackUp, infiniteJump, speedUp;
   int dy = -1;
   int speed = 100;
   float stateTime = 0.0f;
   enum State {
      PICKABLE,
      PICKED
   }
   State state = State.PICKABLE;

   public PowerUp(){
      generateAnimation();
   }

   public void generateAnimation() {
      app = (MyGdxGame) Gdx.app.getApplicationListener();
      AssetManager assetManager = app.getManager();

      Texture Heal = assetManager.get("HealIcon.png");
      Texture AttackUp = assetManager.get("AttackUpIcon.png");
      Texture InfiniteJump = assetManager.get("InfiniteJumpIcon.png");
      Texture SpeedUp = assetManager.get("speedupicon.png");

      heal = new Sprite(Heal);
      heal.setSize(48, 48);
      heal.setColor(Color.RED);

      attackUp = new Sprite(AttackUp);
      attackUp.setSize(48, 48);
      attackUp.setColor(Color.WHITE);

      infiniteJump = new Sprite(InfiniteJump);
      infiniteJump.setSize(48, 48);
      infiniteJump.setColor(Color.BLACK);

      speedUp = new Sprite(SpeedUp);
      speedUp.setSize(48, 48);
      speedUp.setColor(Color.WHITE);

   }
   public void draw(SpriteBatch batch, int x) {
      if (state == State.PICKABLE){
         if (x == 0) {
            batch.draw(heal, this.x - 24, y - 24);
         }
         if (x == 1) {
            batch.draw(attackUp, this.x - 24, y - 24);
         }
         if (x == 2) {
            batch.draw(infiniteJump, this.x - 24, y - 24);
         }
         if (x == 3) {
            batch.draw(speedUp, this.x - 24, y - 24);
         }
      }
   }

   public void update() {
      float elapsed = Gdx.graphics.getDeltaTime();
      stateTime += elapsed;
      y += dy * speed * elapsed;
      if (y < 140) {
         y = 140;
         this.Stop();
      }
   }

   void Stop()
   {
      if (y > 140) {
         dy = -1;
      }
      else dy = 0;
   }

   boolean isPickedP1(Player1 p1) {
      return p1.getX() - x <= 20 && p1.getX() - x >= -20 && p1.getY() - y >= -20 && p1.getY() - y <= 20;
   }

   boolean isPickedP2(Player2 p2) {
      return p2.getX() - x <= 20 && p2.getX() - x >= -20 && p2.getY() - y >= -20 && p2.getY() - y <= 20;
   }

   void onPickUp(Player1 p1) {

   }

   void onPickUp(Player2 p2) {

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
