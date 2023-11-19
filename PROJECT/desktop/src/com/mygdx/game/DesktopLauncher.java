package com.mygdx.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;


public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setWindowedMode(1600, 900);
		config.setTitle("Project PBO : Pixel Brawl");
		config.setWindowIcon("game-icon.png");
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
