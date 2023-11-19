package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.Viewport;

public class WelcomeScreen implements Screen, InputProcessor {
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private TextButton buttonExit, buttonPlay;
    private BitmapFont white, black;
    private Label heading;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture background;

    private final MyGdxGame parentGame;
    private Sprite bg;

    private AssetManager assetmanager;
    private BitmapFont font;
    private BitmapFontCache continueText, title;
    private static final float BLINK_INTERVAL = 0.5f;
    private float elapsedTime = 0f;
    private boolean showText = true;

    public WelcomeScreen(){
        parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        assetmanager = parentGame.getManager();
        batch = new SpriteBatch();


        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1600, 900);
        background = new Texture("bg-welcome.png");
        bg = new Sprite(background);
        bg.setPosition(0,0);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Redaction-mL8P5.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        //text untuk continue
        parameter.size = 35;
        font = generator.generateFont(parameter);
        continueText = new BitmapFontCache(font);
        continueText.setText("Press Anywhere to Start", 600, 100);

        //text untuk judul game
        parameter.size = 175;
        parameter.borderColor.set(Color.RED);
        font = generator.generateFont(parameter);
        title = new BitmapFontCache(font);
        title.setColor(Color.WHITE);
        title.setText("PIXEL  BRAWL", 200, 350);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        bg = new Sprite(background);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        elapsedTime += delta;

        batch.begin();
        bg.draw(batch);

        if (showText) {
            continueText.setColor(new Color(1, 1, 1, 1));
            continueText.draw(batch);
        }

        title.draw(batch);
        batch.end();

        // Update blink setiap BLINK_INTERVAL detik
        if (elapsedTime > BLINK_INTERVAL) {
            showText = !showText;
            elapsedTime -= BLINK_INTERVAL;
        }
        this.update();
    }

    @Override
    public boolean keyDown(int keycode) {

        if(assetmanager.isFinished()) {
            this.dispose();
            parentGame.setGameScreen(new GameScreen());
            parentGame.setScreen(parentGame.getGameScreen());
        }
        return true;
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

        if(assetmanager.isFinished()) {
            this.dispose();
            parentGame.setLoadingScreen(new GameScreen());
            parentGame.setScreen(parentGame.getLoadingScreen());
        }
        return true;
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void update()
    {
        if(assetmanager.update())
        {
            continueText.setText("Press Anywhere to Start", 600, 100);
        }
    }
}
