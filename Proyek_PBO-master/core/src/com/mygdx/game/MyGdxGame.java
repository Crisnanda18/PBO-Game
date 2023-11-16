package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.awt.*;

public class MyGdxGame extends Game implements InputProcessor {
	public static final float WORLD_WIDTH = 1000;
	public static final float WORLD_HEIGHT = 600;
	AssetManager manager = new AssetManager();
	Music music;
	public GameScreen gameScreen;
	public LoadingScreen loadingScreen;

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setLoadingScreen(LoadingScreen loadingScreen) {
		this.loadingScreen = loadingScreen;
	}

	public LoadingScreen getLoadingScreen() {
		return loadingScreen;
	}

	@Override
	public void create() {
		FileHandleResolver resolver = new InternalFileHandleResolver();
		manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		FreetypeFontLoader.FreeTypeFontLoaderParameter fontParameter = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
		fontParameter.fontFileName = "04b_25__.ttf";
		fontParameter.fontParameters.size = 24;
		fontParameter.fontParameters.color = Color.WHITE;
		fontParameter.fontParameters.borderColor = Color.BLACK;
		fontParameter.fontParameters.borderWidth = 2;
		fontParameter.fontParameters.flip = false;
		manager.load("04b_25__.ttf", BitmapFont.class, fontParameter);

//		asset buat hp bar
		manager.load("black rectangle.png", Texture.class);
		manager.load("red rectangle.png", Texture.class);
		manager.load("vs_logo.png", Texture.class);

//		asset wallpaper loading screen
		manager.load("loading.jpg", Texture.class);

//		asset buat player 1
		manager.load("Sprite1/Attack1.png", Texture.class);
		manager.load("Sprite1/Death.png", Texture.class);
		manager.load("Sprite1/Take Hit - white silhouette.png", Texture.class);
		manager.load("Sprite1/Idle.png", Texture.class);
		manager.load("Sprite1/Run.png", Texture.class);
		manager.load("Sprite1/Jump.png", Texture.class);
		manager.load("Lost City Cover.jpeg", Texture.class);
		manager.load("Sprite1/Fall.png", Texture.class);

//		asset buat player 2
		manager.load("Sprite2/Attack1.png", Texture.class);
		manager.load("Sprite2/Death.png", Texture.class);
		manager.load("Sprite2/Take Hit.png", Texture.class);
		manager.load("Sprite2/Idle.png", Texture.class);
		manager.load("Sprite2/Run.png", Texture.class);
		manager.load("Sprite2/Jump.png", Texture.class);
		manager.load("Sprite2/Fall.png", Texture.class);

//		asset buat powerUp
		manager.load("AttackUpIcon.png", Texture.class);
		manager.load("HealIcon.png", Texture.class);
		manager.load("InfiniteJumpIcon.png", Texture.class);
		manager.load("speedupicon.png", Texture.class);
		manager.load("Collected.png", Texture.class);

//		asset buat sound
		manager.load("slash.mp3", Sound.class);
		manager.load("take-damage.mp3", Sound.class);
		manager.load("exp_power.wav", Sound.class);
		manager.load("chiptune.mp3", Music.class);
		manager.load("sound.wav", Sound.class);
		SkinLoader.SkinParameter skinParam = new SkinLoader.SkinParameter("uiskin.atlas");
		manager.load("uiskin.json", Skin.class, skinParam);

		this.setScreen(new LoadingScreen());
	}

	public void PlayMusic()
	{
		if(music == null)
			music = manager.get("chiptune.mp3", Music.class);
		music.setVolume(0.7f);
		music.setLooping(true);
		if(!music.isPlaying())
			music.play();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
//		viewport.update(width, height);
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void dispose() {
		manager.dispose();
	}
	@Override
	public boolean keyDown(int keycode) {
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		return true;
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

	public static TextureRegion[] CreateAnimationFrames(Texture tex, int frameWidth, int frameHeight, int frameCount, boolean flipx, boolean flipy) {
		//akan membuat frame animasi dari texture, texture dipotong2 sebesar frameWidth x frameHeight
		// frame akan diambil dari posisi kiri atas ke kanan bawah
		TextureRegion[][] tmp = TextureRegion.split(tex, frameWidth, frameHeight);
		TextureRegion[] frames = new TextureRegion[frameCount];
		int index = 0;
		int row = tex.getHeight() / frameHeight;
		int col = tex.getWidth() / frameWidth;
		for (int i = 0; i < row && index < frameCount; i++) {
			for (int j = 0; j < col && index < frameCount; j++) {
				frames[index] = tmp[i][j];
				frames[index].flip(flipx, flipy);
				index++;
			}
		}
		return frames;
	}

	public AssetManager getManager() {
		return manager;
	}
}
