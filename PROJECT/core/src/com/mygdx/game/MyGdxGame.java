package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.sun.org.apache.bcel.internal.generic.PUSH;

public class MyGdxGame extends Game implements InputProcessor {
    AssetManager manager = new AssetManager();

    WelcomeScreen welcomeScreen;
    GameScreen gameScreen;
    BitmapFont font;
    Music bgMusic;

    public GameScreen getLoadingScreen() {
        return gameScreen;
    }

    public void setLoadingScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    @Override
    public void create() {
        //loading aset yang diperlukan
        manager.load("bg-welcome.png", Texture.class);
        manager.load("country.png", Texture.class);
        manager.load("rainforest.png", Texture.class);
        //loading music bakground
        manager.load("startmusic.mp3",Music.class);
        manager.load("FOREST.mp3", com.badlogic.gdx.audio.Music.class);
        manager.load("COUNTRY.mp3", com.badlogic.gdx.audio.Music.class);

        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        fontParameter.fontFileName = "Redaction-mL8P5.ttf";
        fontParameter.fontParameters.color = Color.WHITE;
        fontParameter.fontParameters.size = 24;
        fontParameter.fontParameters.borderWidth = 2;
        fontParameter.fontParameters.flip = false;
        manager.load("Redaction-mL8P5.ttf", BitmapFont.class, fontParameter);


        //arahkan ke welcome screen
        this.setScreen(new WelcomeScreen());
    }

//    public void loadMusic()
//    {
//        if(bgMusic == null) bgMusic = manager.get("startmusic.mp3", Music.class);
//        bgMusic.setVolume(0.7f);
//        bgMusic.setLooping(true);
//        if(!bgMusic.isPlaying())
//            bgMusic.play();
//    }
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    public AssetManager getManager() {
        return manager;
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public WelcomeScreen getWelcomeScreen() {
        return welcomeScreen;
    }

    public void setWelcomeScreen(WelcomeScreen welcomeScreen) {
        this.welcomeScreen = welcomeScreen;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }


}
