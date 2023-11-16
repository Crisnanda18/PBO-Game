package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen, InputProcessor {
    private MyGdxGame parentGame;
    private AssetManager manager;

    private String pWin;
    private SpriteBatch batch;
    private Sprite attackUpIcon, infiniteJumpIcon, speedUpIcon, obj, p1_hp, p2_hp, black_bar, logo;
    private Texture background_image, HP_p1, HP_p2, bar, vs;
    private OrthographicCamera camera, stageCamera;
    private InputMultiplexer multiInput;
    private Stage stage;
    private Viewport viewport;
    private Sound slash, expire, pickUp;
    private BitmapFont font;
    private BitmapFontCache fontcache1, fontcache2;

    private Player1 p1;
    private Player2 p2;
    private boolean isPowerUpSpawn, isP1AttackUp, isP1InfiniteJump, isP2AttackUp, isP2InfiniteJump, isP1SpeedUp, isP2SpeedUp = false;
    private PowerUp powerUp;
    private int countElapsed;
    private int elapsed = 0;
    private ArrayList<PowerUp> powerUps = new ArrayList<PowerUp>();
    private int randomNumber;
    private int buffDuration;
    private float stateTime;
    private int count=0;
    public GameScreen() {
        parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        manager = parentGame.getManager();

        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(true, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        viewport = new ExtendViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, camera);


        batch = new SpriteBatch();
        font = manager.get("04b_25__.ttf");
        fontcache1 = new BitmapFontCache(font);
        fontcache2 = new BitmapFontCache(font);
        fontcache1.setText("Player 1 HP: 100", 120, 500);
        fontcache2.setText("Player 2 HP: 100", 655, 500);

        Texture AttackUp = manager.get("AttackUpIcon.png");
        Texture InfiniteJump = manager.get("InfiniteJumpIcon.png");
        Texture SpeedUp = manager.get("speedupicon.png");
        Texture Collected = manager.get("Collected.png");

        attackUpIcon = new Sprite(AttackUp);
        attackUpIcon.setSize(48, 48);
        attackUpIcon.setColor(Color.WHITE);

        infiniteJumpIcon = new Sprite(InfiniteJump);
        infiniteJumpIcon.setSize(48, 48);
        infiniteJumpIcon.setColor(Color.BLACK);

        speedUpIcon = new Sprite(SpeedUp);
        speedUpIcon.setSize(48, 48);
        speedUpIcon.setColor(Color.WHITE);

        background_image = manager.get("Lost City Cover.jpeg");
        obj = new Sprite(background_image);
        obj.setSize(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        obj.setPosition(0, 0);

        camera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        camera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        p1 = new Player1();
        p1.setX(200);
        p1.setY(140);

        p2 = new Player2();
        p2.setX(1000 - 200);
        p2.setY(140);

        //HP bar
        bar = manager.get("black rectangle.png");
        black_bar = new Sprite(bar);
        black_bar.setPosition(0, 450);
        black_bar.setSize(1000, 50);
        black_bar.setColor(Color.BLACK);

        HP_p1 = manager.get("red rectangle.png");
        p1_hp = new Sprite(HP_p1);
        p1_hp.setPosition(10, 455);
        p1_hp.setSize(450, 40);
        p1_hp.setColor(Color.RED);

        HP_p2 = new Texture("red rectangle.png");
        p2_hp = new Sprite(HP_p2);
        p2_hp.setPosition(540, 455);
        p2_hp.setSize(450, 40);
        p2_hp.setColor(Color.RED);

        vs = manager.get("vs_logo.png");
        logo = new Sprite(vs);
        logo.setSize(100, 100);
        logo.setPosition(500 - 50, 455 - 30);
        Gdx.input.setInputProcessor(this);

        powerUps.add(new PowerUp() {
            @Override
            void onPickUp(Player1 p1) {
                if (p1.getHP() <= 80) {
                    p1.setHP(p1.getHP() + 20);
                } else {
                    p1.setHP(100.0);
                }
            }

            @Override
            void onPickUp(Player2 p2) {
                if (p2.getHP() <= 80) {
                    p2.setHP(p2.getHP() + 20);
                } else {
                    p2.setHP(100.0);
                }
            }
        });
        powerUps.add(new PowerUp() {
            @Override
            void onPickUp(Player1 p1) {
                p1.setDmg(20);
                isP1AttackUp = true;
            }

            @Override
            void onPickUp(Player2 p2) {
                p2.setDmg(20.0);
                isP2AttackUp = true;
            }
        });
        powerUps.add(new PowerUp() {
            @Override
            void onPickUp(Player1 p1) {
                p1.setInfiniteJump(true);
                isP1InfiniteJump = true;
            }

            @Override
            void onPickUp(Player2 p2) {
                p2.setInfiniteJump(true);
                isP2InfiniteJump = true;
            }
        });
        powerUps.add(new PowerUp() {
            @Override
            void onPickUp(Player1 p1){
                p1.setSpeed(300);
                isP1SpeedUp = true;
            }

            @Override
            void onPickUp(Player2 p2){
                p2.setSpeed(300);
                isP2SpeedUp = true;
            }
        });

        stageCamera = new OrthographicCamera(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stageCamera.setToOrtho(false, MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT);
        stage = new Stage(new FitViewport(MyGdxGame.WORLD_WIDTH, MyGdxGame.WORLD_HEIGHT, stageCamera));

        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);

        slash = manager.get("slash.mp3");
        expire = manager.get("exp_power.wav");
        pickUp = manager.get("sound.wav");
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

    public String getpWin() {
        return pWin;
    }

    public void setpWin(String pWin) {
        this.pWin = pWin;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public Sprite getAttackUpIcon() {
        return attackUpIcon;
    }

    public void setAttackUpIcon(Sprite attackUpIcon) {
        this.attackUpIcon = attackUpIcon;
    }

    public Sprite getInfiniteJumpIcon() {
        return infiniteJumpIcon;
    }

    public void setInfiniteJumpIcon(Sprite infiniteJumpIcon) {
        this.infiniteJumpIcon = infiniteJumpIcon;
    }

    public Sprite getSpeedUpIcon() {
        return speedUpIcon;
    }

    public void setSpeedUpIcon(Sprite speedUpIcon) {
        this.speedUpIcon = speedUpIcon;
    }

    public Sprite getObj() {
        return obj;
    }

    public void setObj(Sprite obj) {
        this.obj = obj;
    }

    public Sprite getP1_hp() {
        return p1_hp;
    }

    public void setP1_hp(Sprite p1_hp) {
        this.p1_hp = p1_hp;
    }

    public Sprite getP2_hp() {
        return p2_hp;
    }

    public void setP2_hp(Sprite p2_hp) {
        this.p2_hp = p2_hp;
    }

    public Sprite getBlack_bar() {
        return black_bar;
    }

    public void setBlack_bar(Sprite black_bar) {
        this.black_bar = black_bar;
    }

    public Sprite getLogo() {
        return logo;
    }

    public void setLogo(Sprite logo) {
        this.logo = logo;
    }

    public Texture getBackground_image() {
        return background_image;
    }

    public void setBackground_image(Texture background_image) {
        this.background_image = background_image;
    }

    public Texture getHP_p1() {
        return HP_p1;
    }

    public void setHP_p1(Texture HP_p1) {
        this.HP_p1 = HP_p1;
    }

    public Texture getHP_p2() {
        return HP_p2;
    }

    public void setHP_p2(Texture HP_p2) {
        this.HP_p2 = HP_p2;
    }

    public Texture getBar() {
        return bar;
    }

    public void setBar(Texture bar) {
        this.bar = bar;
    }

    public Texture getVs() {
        return vs;
    }

    public void setVs(Texture vs) {
        this.vs = vs;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public OrthographicCamera getStageCamera() {
        return stageCamera;
    }

    public void setStageCamera(OrthographicCamera stageCamera) {
        this.stageCamera = stageCamera;
    }

    public InputMultiplexer getMultiInput() {
        return multiInput;
    }

    public void setMultiInput(InputMultiplexer multiInput) {
        this.multiInput = multiInput;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public Sound getSlash() {
        return slash;
    }

    public void setSlash(Sound slash) {
        this.slash = slash;
    }

    public Sound getExpire() {
        return expire;
    }

    public void setExpire(Sound expire) {
        this.expire = expire;
    }

    public Sound getPickUp() {
        return pickUp;
    }

    public void setPickUp(Sound pickUp) {
        this.pickUp = pickUp;
    }

    public BitmapFont getFont() {
        return font;
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    public BitmapFontCache getFontcache1() {
        return fontcache1;
    }

    public void setFontcache1(BitmapFontCache fontcache1) {
        this.fontcache1 = fontcache1;
    }

    public BitmapFontCache getFontcache2() {
        return fontcache2;
    }

    public void setFontcache2(BitmapFontCache fontcache2) {
        this.fontcache2 = fontcache2;
    }

    public Player1 getP1() {
        return p1;
    }

    public void setP1(Player1 p1) {
        this.p1 = p1;
    }

    public Player2 getP2() {
        return p2;
    }

    public void setP2(Player2 p2) {
        this.p2 = p2;
    }

    public boolean isPowerUpSpawn() {
        return isPowerUpSpawn;
    }

    public void setPowerUpSpawn(boolean powerUpSpawn) {
        isPowerUpSpawn = powerUpSpawn;
    }

    public boolean isP1AttackUp() {
        return isP1AttackUp;
    }

    public void setP1AttackUp(boolean p1AttackUp) {
        isP1AttackUp = p1AttackUp;
    }

    public boolean isP1InfiniteJump() {
        return isP1InfiniteJump;
    }

    public void setP1InfiniteJump(boolean p1InfiniteJump) {
        isP1InfiniteJump = p1InfiniteJump;
    }

    public boolean isP2AttackUp() {
        return isP2AttackUp;
    }

    public void setP2AttackUp(boolean p2AttackUp) {
        isP2AttackUp = p2AttackUp;
    }

    public boolean isP2InfiniteJump() {
        return isP2InfiniteJump;
    }

    public void setP2InfiniteJump(boolean p2InfiniteJump) {
        isP2InfiniteJump = p2InfiniteJump;
    }

    public boolean isP1SpeedUp() {
        return isP1SpeedUp;
    }

    public void setP1SpeedUp(boolean p1SpeedUp) {
        isP1SpeedUp = p1SpeedUp;
    }

    public boolean isP2SpeedUp() {
        return isP2SpeedUp;
    }

    public void setP2SpeedUp(boolean p2SpeedUp) {
        isP2SpeedUp = p2SpeedUp;
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(PowerUp powerUp) {
        this.powerUp = powerUp;
    }

    public int getCountElapsed() {
        return countElapsed;
    }

    public void setCountElapsed(int countElapsed) {
        this.countElapsed = countElapsed;
    }

    public int getElapsed() {
        return elapsed;
    }

    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
    }

    public ArrayList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public void setPowerUps(ArrayList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public void setRandomNumber(int randomNumber) {
        this.randomNumber = randomNumber;
    }

    public int getBuffDuration() {
        return buffDuration;
    }

    public void setBuffDuration(int buffDuration) {
        this.buffDuration = buffDuration;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    @Override
    public boolean keyDown(int keycode) {
        //Player 1 Control
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
            slash.play();
            p1.doAction(Player1.Action.ATTACK);
        }
        if (keycode == Input.Keys.S) {
            p1.Jump(Player1.State.FALL);
        }

        //Player 2 Control
        if (keycode == Input.Keys.UP && (p2.getState() != Player2.State.FALL || p2.isInfiniteJump())) {
            p2.Jump(Player2.State.JUMP);
        }
        if (keycode == Input.Keys.RIGHT) {
            p2.setMove(Player2.Direction.RIGHT);
        }
        if (keycode == Input.Keys.LEFT) {
            p2.setMove(Player2.Direction.LEFT);
        }
        if (keycode == Input.Keys.CONTROL_RIGHT) {
            slash.play();
            p2.doAction(Player2.Action.ATTACK);

        }
        if (keycode == Input.Keys.DOWN) {
            p2.Jump(Player2.State.FALL);
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

        if (keycode == Input.Keys.LEFT && p2.getDirection() == Player2.Direction.LEFT)
            p2.Stop();
        else if (keycode == Input.Keys.RIGHT && p2.getDirection() == Player2.Direction.RIGHT)
            p2.Stop();
        else if (keycode == Input.Keys.UP && p2.getState() == Player2.State.JUMP)
            p2.Jump(Player2.State.FALL);
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

    @Override
    public void show() {
        Gdx.input.setInputProcessor(multiInput);
        parentGame.PlayMusic();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 0);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

//		batch.draw(bar, 0, 450, 1000, 100);
        obj.draw(batch);
        black_bar.draw(batch);
        p2_hp.draw(batch);
        p1_hp.draw(batch);
        logo.draw(batch);

        p2.draw(batch);
        p1.draw(batch);
        fontcache1.draw(batch);
        fontcache2.draw(batch);

        if (isP1AttackUp){
            batch.draw(attackUpIcon, 10, 400);
        }
        if (isP1InfiniteJump){
            batch.draw(infiniteJumpIcon, 10, 400);
        }
        if (isP1SpeedUp){
            batch.draw(speedUpIcon, 10, 400);
        }
        if (isP2AttackUp){
            batch.draw(attackUpIcon, 990 - 48, 400);
        }
        if (isP2InfiniteJump){
            batch.draw(infiniteJumpIcon, 990 - 48, 400);
        }
        if (isP2SpeedUp){
            batch.draw(speedUpIcon, 990 - 48, 400);
        }

        countElapsed += 1;
        if (countElapsed == 60) {
            elapsed += 1;
            buffDuration -= 1;
            System.out.println(elapsed);
            countElapsed = 0;
            if (buffDuration == 0){
                isP1AttackUp = false;
                isP1InfiniteJump = false;
                isP1SpeedUp = false;
                isP2AttackUp = false;
                isP2InfiniteJump = false;
                isP2SpeedUp = false;
                p1.setDmg(10);
                p1.setInfiniteJump(false);
                p1.setSpeed(200);
                p2.setDmg(10.0);
                p2.setInfiniteJump(false);
                p2.setSpeed(200);
            }
        }
        stateTime += Gdx.graphics.getDeltaTime();
        this.update();
        batch.end();
        stage.act();
        stage.draw();

    }

    public void update() {
        p1_hp.setSize((float) (450 * p1.getRatioHP()), p1_hp.getHeight());
        p2_hp.setSize((float) (450 * p2.getRatioHP()), p2_hp.getHeight());
        fontcache1.setText("Player 1 HP : " + p1.getHP(), 120, 500);
        fontcache2.setText("Player 2 HP : " + p2.getHP(), 655, 500);
        p1.update();
        p2.update();
        updatePowerUp(elapsed);
        if (p1.getHP() <= 1) {
            pWin = "PLAYER 2 WIN";
            parentGame.setScreen(new EndScreen());
        }
        else if (p2.getHP() <= 1) {
            pWin = "PLAYER 1 WIN";
            parentGame.setScreen(new EndScreen());
        }
    }

    public void updatePowerUp(double elapsed) {
        if (!isPowerUpSpawn) {
            if (count != 0) {
                if (elapsed >= count){
                    isPowerUpSpawn = true;
                    final Random random = new Random();
                    randomNumber = random.nextInt(4);
                    System.out.println(randomNumber);
                    powerUp = new PowerUp() {
                        @Override
                        void onPickUp(Player1 p1) {
                            powerUps.get(randomNumber).onPickUp(p1);
                        }

                        @Override
                        void onPickUp(Player2 p2) {
                            powerUps.get(randomNumber).onPickUp(p2);
                        }
                    };
                    powerUp.setX(random.nextInt(900) + 50);
                }
            } else count = (int) (elapsed + 10);
        } else {
            if (powerUp.state == PowerUp.State.PICKABLE) {
                powerUp.draw(batch, randomNumber);
                powerUp.update();
                if (powerUp.isPickedP1(p1)) {
                    pickUp.play();
                    buffDuration = 7;
                    powerUp.onPickUp(p1);
                    powerUp.state = PowerUp.State.PICKED;
                    isPowerUpSpawn = false;
                    count = 0;
                }
                else if (powerUp.isPickedP2(p2)) {
                    pickUp.play();
                    buffDuration = 7;
                    powerUp.onPickUp(p2);
                    powerUp.state = PowerUp.State.PICKED;
                    isPowerUpSpawn = false;
                    count = 0;
                }
            }
        }
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
        batch.dispose();
        manager.dispose();
        background_image.dispose();
    }
}
