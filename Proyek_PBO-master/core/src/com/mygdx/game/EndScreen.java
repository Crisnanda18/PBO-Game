package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class EndScreen implements Screen, InputProcessor {
    private MyGdxGame parentGame;
    private AssetManager manager;
    private Viewport viewport;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFontCache text, pressKeyText;
    private Texture loadingBG;
    private Sprite BGRatio;
    public EndScreen() {
        parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        manager = parentGame.getManager();

        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        viewport = new ExtendViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, camera);
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("04b_25__.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 45;
        parameter.color = Color.YELLOW;
        parameter.borderColor.add(Color.BLACK);
        parameter.borderWidth = 3;
        font = generator.generateFont(parameter);

        text = new BitmapFontCache(font);
        text.setColor(Color.YELLOW);
        text.setText(parentGame.getGameScreen().getpWin(), 350, 350);

        pressKeyText = new BitmapFontCache(font);
        pressKeyText.setColor(Color.YELLOW);
        pressKeyText.setText("Press R Key Or Click To Continue", 130, 100);

        loadingBG = new Texture("loading.jpg");
        BGRatio = new Sprite(loadingBG);
        BGRatio.setPosition(0,0);
        BGRatio.setSize(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
    }

    public MyGdxGame getParentGame() {
        return parentGame;
    }

    public void setParentGame(MyGdxGame parentGame) {
        this.parentGame = parentGame;
    }

    public AssetManager getManager() {
        return manager;
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public BitmapFontCache getText() {
        return text;
    }

    public void setText(BitmapFontCache text) {
        this.text = text;
    }

    public BitmapFontCache getPressKeyText() {
        return pressKeyText;
    }

    public void setPressKeyText(BitmapFontCache pressKeyText) {
        this.pressKeyText = pressKeyText;
    }

    public Texture getLoadingBG() {
        return loadingBG;
    }

    public void setLoadingBG(Texture loadingBG) {
        this.loadingBG = loadingBG;
    }

    public Sprite getBGRatio() {
        return BGRatio;
    }

    public void setBGRatio(Sprite BGRatio) {
        this.BGRatio = BGRatio;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(manager.isFinished() && keycode == Input.Keys.R) {
            this.dispose();
            parentGame.setLoadingScreen(new LoadingScreen());
            parentGame.setScreen(parentGame.getLoadingScreen());
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
        if(manager.isFinished()) {
            this.dispose();
            parentGame.setLoadingScreen(new LoadingScreen());
            parentGame.setScreen(parentGame.getLoadingScreen());
        }
        return true;
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        BGRatio.draw(batch);
        pressKeyText.draw(batch);
        text.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
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
