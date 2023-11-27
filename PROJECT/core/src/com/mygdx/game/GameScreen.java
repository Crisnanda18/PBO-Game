package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.Random;

public class GameScreen implements Screen, InputProcessor {
    private Player1 p1;
    private Player2 p2;
    private Stage stage;
    private TextureAtlas atlas;
    private Skin skin;
    private Table table;
    private BitmapFont white, black;
    private Label heading;

    private OrthographicCamera camera, stageCamera;
    private Viewport viewport;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFontCache mapText;
    private Music bgmusic;

    private long startTime = System.currentTimeMillis();
    private boolean isTextVisible = true;

    private String[] backgrounds = {"country.png", "rainforest.png"};
    private String currentBackground;

    private InputMultiplexer multiInput;

    private final MyGdxGame parentGame;
    private Sprite bg;

    private AssetManager assetmanager;

    private BitmapFont font;

    public GameScreen() {
        parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        assetmanager = parentGame.getManager();
        batch = new SpriteBatch();

        randomizeBackground();


        p1 = new Player1();
        p1.setX(200);
        p1.setY(220);

        p2 = new Player2();
        p2.setX(1000 - 200);
        p2.setY(220);

        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(true, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        viewport = new ExtendViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, camera);

        stageCamera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stageCamera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stage = new Stage(new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, stageCamera));

        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);

    }



    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.W && (p1.getState() != Player1.State.FALL || p1.isInfiniteJump())) {
            p1.Jump(Player1.State.JUMP);
        }
        if (keycode == Input.Keys.D) {
            p1.setMove(Player1.Direction.RIGHT);
        }
        if (keycode == Input.Keys.A) {
            p1.setMove(Player1.Direction.LEFT);
        }
        if (keycode == Input.Keys.CONTROL_LEFT) {
            p1.doAction(Player1.Action.ATTACK);
        }
        if (keycode == Input.Keys.S) {
            p1.Jump(Player1.State.FALL);
        }
       return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.A && p1.getDirection() == Player1.Direction.LEFT)
            p1.Stop();
        if (keycode == Input.Keys.D && p1.getDirection() == Player1.Direction.RIGHT)
            p1.Stop();
        if (keycode == Input.Keys.W && p1.getState() == Player1.State.JUMP)
            p1.Jump(Player1.State.FALL);
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
        randomizeBackground();
        loadMusic();
    }

    private void loadMusic() {
        // Stop the previous music if it was playing
        if (bgmusic != null) {
            bgmusic.stop();
            bgmusic.dispose();
        }

        // Retrieve the loaded music and set it to loop and play
        if (currentBackground.equals("country.png")) {
            bgmusic = assetmanager.get("COUNTRY.mp3", Music.class);
        } else if (currentBackground.equals("rainforest.png")) {
            bgmusic = assetmanager.get("FOREST.mp3", Music.class);
        }
        bgmusic.setVolume(0.7f);
        bgmusic.setLooping(true);
        bgmusic.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        bg = new Sprite(background);
        batch.begin();
        bg.draw(batch);
        if (isTextVisible) {
            mapText.draw(batch);
        }

        p1.draw(batch);

        batch.end();

        // Use the Timer class to schedule a task that will make the mapText disappear
        if (System.currentTimeMillis() - startTime >= 4000 && isTextVisible) {
            isTextVisible = false;
            // Do any additional actions if needed
        }
    }

    private void randomizeBackground() {
        Random random = new Random();
        currentBackground = backgrounds[random.nextInt(backgrounds.length)];
        if(currentBackground.equals("country.png")) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Redaction-mL8P5.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 45;
            parameter.borderColor.add(Color.RED);
            font = generator.generateFont(parameter);
            mapText = new BitmapFontCache(font);
            mapText.setText("Midsummer Moor \n       476 A.D", 650, 800);
        }
        else {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Redaction-mL8P5.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 45;
            parameter.borderColor.add(Color.RED);
            font = generator.generateFont(parameter);
            mapText = new BitmapFontCache(font);
            mapText.setText("Midnight Forest\n       500 A.D", 650, 800);
        }

        background = new Texture(currentBackground);
        bg = new Sprite(background);
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
}
