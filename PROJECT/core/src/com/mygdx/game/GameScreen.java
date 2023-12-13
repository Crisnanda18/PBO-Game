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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

public class GameScreen implements Screen, InputProcessor {
    private User1 p1;
    private User2 p2;
    private boolean isBuffSpawn, isP1AtkUp, isP1Armor, isP2AtkUp, isP2Armor = false;

    private Buff buff;

    private ArrayList<Buff> buffs = new ArrayList<Buff>();
    private int buffTime;
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
    private Texture fight, HP_p1, HP_p2, p1_bar, p2_bar;
    private Music bgmusic;
    private Music fightmusic;
    private Music warCry1;
    private Music warCry2;

    private Sprite  p1_hp, p2_hp, p1_border,p2_border;


    private long startTime = System.currentTimeMillis();
    private boolean isTextVisible = true;

    private String[] backgrounds = {"country.png", "rainforest.png"};
    private String currentBackground;

    private InputMultiplexer multiInput;

    private final MyGdxGame parentGame;
    private Sprite bg;
    private Sprite fightSprite;
    private Array<Sprite> platforms;


    private Sprite  platform;

    private AssetManager assetmanager;

    private BitmapFont font;

    public GameScreen() {
        parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        assetmanager = parentGame.getManager();
        batch = new SpriteBatch();

        randomizeBackground();
        fight = assetmanager.get("fight-opening.png", Texture.class);
        fightSprite = new Sprite(fight);

        createPlatforms();

        p1 = new User1();
        p1.setX(200);
        p1.setY(100);

        p2 = new User2();
        p2.setX(1100);
        p2.setY(100);

        fightSprite.setPosition(400, 300);

        //HP bar
        p1_bar = assetmanager.get("black rectangle.png");
        p1_border = new Sprite(p1_bar);
        p1_border.setPosition(100, 750);
        p1_border.setSize(320, 35);
        p1_border.setColor(Color.BLACK);

        p2_bar = assetmanager.get("black rectangle.png");
        p2_border = new Sprite(p2_bar);
        p2_border.setPosition(1200, 750);
        p2_border.setSize(320, 35);
        p2_border.setColor(Color.BLACK);


        HP_p1 = assetmanager.get("red rectangle.png");
        p1_hp = new Sprite(HP_p1);
        p1_hp.setPosition(93, 748);
        p1_hp.setSize(328, 40);
        p1_hp.setColor(Color.RED);

        HP_p2 = new Texture("red rectangle.png");
        p2_hp = new Sprite(HP_p2);
        p2_hp.setPosition(1193, 748);
        p2_hp.setSize(328, 40);
        p2_hp.setColor(Color.RED);

        //Take Heal buff
        buffs.add(new Buff() {
            @Override
            void onTaken(User1 user1) {
                if (p1.getHP() <= 90){
                    p1.setHP(p1.getHP()+10);
                }
                else {
                    p1.setHP(100.0);
                }

            }
        });


        buffs.add(new Buff() {
            @Override
            void onTaken(User2 user2) {
                if (p1.getHP() <= 90){
                    p2.setHP(p2.getHP()+10);
                }
                else {
                    p2.setHP(100.0);
                }

            }
        });

        //Take Attack Up Buff

        buffs.add(new Buff() {
            @Override
            void onTaken(User1 user1) {
                p1.setDmg(p1.getDmg()+10);
            }
        });

        buffs.add(new Buff() {
            @Override
            void onTaken(User2 user2) {
                p2.setDmg(p2.getDmg()+10);
            }
        });

        //Take Armor Buff

        buffs.add(new Buff() {
            @Override
            void onTaken(User1 user1) {
                p2.setDmg(p2.getDmg()/2);
            }
        });
        buffs.add(new Buff() {
            @Override
            void onTaken(User2 user2) {
                p1.setDmg(p1.getDmg()/2);
            }
        });



        camera = new OrthographicCamera(1600, 900);
        camera.setToOrtho(true, 1600, 900);
        viewport = new ExtendViewport(1600, 900, camera);

        stageCamera = new OrthographicCamera(1600, 900);
        stageCamera.setToOrtho(false, 1600, 900);
        stage = new Stage(new FitViewport(1600, 900, stageCamera));

        multiInput = new InputMultiplexer();
        multiInput.addProcessor(this);
        multiInput.addProcessor(stage);

    }

    public void createPlatforms() {
    platforms = new Array<>();

    if(currentBackground.equals("country.png")) {
        Sprite platform1 = new Sprite(assetmanager.get("platform.png", Texture.class));
        platform1.setPosition(400, 200); // Set the position of platform1
        platforms.add(platform1);

        Sprite platform2 = new Sprite(assetmanager.get("platform.png", Texture.class));
        platform2.setPosition(750, 380); // Set the position of platform2
        platforms.add(platform2);

        Sprite platform3 = new Sprite(assetmanager.get("platform.png", Texture.class));
        platform3.setPosition(1000, 250); // Set the position of platform3
        platforms.add(platform3);

        Sprite platform4 = new Sprite(assetmanager.get("platform.png", Texture.class));
        platform4.setPosition(500, 300); // Set the position of platform4
        platforms.add(platform4);

    }else if (currentBackground.equals("rainforest.png")){
        Sprite platform5 = new Sprite(assetmanager.get("platform2.png", Texture.class));
        platform5.setPosition(400, 200); // Set the position of platform5
        platforms.add(platform5);

        Sprite platform6 = new Sprite(assetmanager.get("platform2.png", Texture.class));
        platform6.setPosition(920, 320); // Set the position of platform6
        platforms.add(platform6);

        Sprite platform7 = new Sprite(assetmanager.get("platform2.png", Texture.class));
        platform7.setPosition(1200, 200); // Set the position of platform7
        platforms.add(platform7);

        Sprite platform8 = new Sprite(assetmanager.get("platform2.png", Texture.class));
        platform8.setPosition(200, 280); // Set the position of platform8
        platforms.add(platform8);

        Sprite platform9 = new Sprite(assetmanager.get("platform2.png", Texture.class));
        platform9.setPosition(520, 400); // Set the position of platform9
        platforms.add(platform9);
    }
}


    @Override
    public boolean keyDown(int keycode) {
        //USER1
        if (keycode == Input.Keys.W && (p1.getState() != User1.State.FALLING || p1.isInfiniteJump())) {
            p1.userJump(User1.State.JUMPING);
        }
        if (keycode == Input.Keys.D) {
            p1.userMove(User1.Direction.RIGHT);
        }
        if (keycode == Input.Keys.A) {
            p1.userMove(User1.Direction.LEFT);
        }
        if (keycode == Input.Keys.SPACE) {
            p1.userACT(User1.Action.ATTACKING);
            warCry1.play();

        }
        if (keycode == Input.Keys.S) {
            p1.userJump(User1.State.FALLING);
        }
        //USER2
        if (keycode == Input.Keys.UP && (p2.getState() != User2.State.FALLING || p2.isInfiniteJump())) {
            p2.userJump(User2.State.JUMPING);
        }
        if (keycode == Input.Keys.DOWN) {
            p2.userJump(User2.State.FALLING);
        }

        if (keycode == Input.Keys.RIGHT) {
            p2.userMove(User2.Direction.RIGHT);
        }
        if (keycode == Input.Keys.LEFT) {
            p2.userMove(User2.Direction.LEFT);
        }
        if (keycode == Input.Keys.CONTROL_RIGHT) {
            p2.userACT(User2.Action.ATTACKING);
            warCry2.play();
        }


       return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        //User1
        if (keycode == Input.Keys.A && p1.getDirection() == User1.Direction.LEFT)
            p1.StopChar();
        if (keycode == Input.Keys.D && p1.getDirection() == User1.Direction.RIGHT)
            p1.StopChar();
        if (keycode == Input.Keys.W && p1.getState() == User1.State.JUMPING)
            p1.userJump(User1.State.FALLING);

        //User2
        if (keycode == Input.Keys.LEFT && p2.getDirection() == User2.Direction.LEFT)
            p2.StopChar();
        if (keycode == Input.Keys.RIGHT && p2.getDirection() == User2.Direction.RIGHT)
            p2.StopChar();
        if (keycode == Input.Keys.UP && p2.getState() == User2.State.JUMPING)
            p2.userJump(User2.State.FALLING);

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
        p1.loadSound();
        p2.loadSound();
    }

    private void loadMusic() {
        // Stop the previous music if it was playing
        if (bgmusic != null && fightmusic != null) {
            fightmusic.stop();
            fightmusic.dispose();
            bgmusic.stop();
            bgmusic.dispose();
        }


        warCry1 = assetmanager.get("warcry1.mp3", Music.class);
        warCry2 = assetmanager.get("warcry2.mp3", Music.class);
        fightmusic = assetmanager.get("FIGHT.mp3", Music.class);

        // Retrieve the loaded music and set it to loop and play
        if (currentBackground.equals("country.png")) {
            bgmusic = assetmanager.get("COUNTRY.mp3", Music.class);
        } else if (currentBackground.equals("rainforest.png")) {
            bgmusic = assetmanager.get("FOREST.mp3", Music.class);
        }
        warCry1.setVolume(0.7f);
        warCry2.setVolume(0.7f);
        bgmusic.setVolume(0.5f);
        bgmusic.setLooping(true);
        bgmusic.play();
        fightmusic.setLooping(false);
        fightmusic.play();
    }
    public void update() {
        p1.update();
        p2.update();
        camera.update();
        stageCamera.update();
    }
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        bg = new Sprite(background);
        batch.begin();
        bg.draw(batch);
        if (isTextVisible) {
            mapText.draw(batch);
            fightSprite.draw(batch);
        }

        for (Sprite platform : platforms) {
            platform.draw(batch);
        }
        p1.draw(batch);
        p2.draw(batch);

        p1_border.draw(batch);
        p2_border.draw(batch);

        p2_hp.draw(batch);
        p1_hp.draw(batch);

        this.update();

        batch.end();


        if (System.currentTimeMillis() - startTime >= 3000 && isTextVisible) {
            isTextVisible = false;

        }
    }

    private void randomizeBackground() {
        Random random = new Random();
        currentBackground = backgrounds[random.nextInt(backgrounds.length)];
        if(currentBackground.equals("country.png")) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Redaction-mL8P5.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 35;
            font = generator.generateFont(parameter);
            mapText = new BitmapFontCache(font);
            mapText.setText("Midsummer Moor \n       476 A.D", 650, 750);
        }
        else {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Redaction-mL8P5.ttf"));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = 35;
            parameter.borderColor.add(Color.RED);
            font = generator.generateFont(parameter);
            mapText = new BitmapFontCache(font);
            mapText.setText("Midnight Forest\n       500 A.D", 650, 750);
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

    public User1 getP1() {
        return p1;
    }

    public void setP1(User1 p1) {
        this.p1 = p1;
    }

    public User2 getP2() {
        return p2;
    }

    public void setP2(User2 p2) {
        this.p2 = p2;
    }

    public Array<Sprite> getPlatforms() {
        return platforms;
    }

    public void setPlatforms(Array<Sprite> platforms) {
        this.platforms = platforms;
    }

    public Sprite getPlatform() {
        return platform;
    }

    public void setPlatform(Sprite platform) {
        this.platform = platform;
    }

    //Buff Spawn
    public boolean isBuffSpawn(){
        return isBuffSpawn();
    }

    public void setBuffSpawn(boolean buffSpawn){
        isBuffSpawn = buffSpawn;
    }

    //Player 2 Buff
    public boolean isP1AtkUp(){
        return isP1AtkUp();
    }

    public void setP1AtkUp(boolean p1AtkUp){
        isP1AtkUp = p1AtkUp;
    }
    public boolean isP1Armor(){
        return isP1Armor;
    }

    public void setP1Armor(boolean p1Armor) {
        isP1Armor = p1Armor;
    }

    //Player 2 Buff
    public boolean isP2AtkUp(){
        return isP2AtkUp();
    }

    public void setP2AtkUp(boolean p2AtkUp){
        isP2AtkUp = p2AtkUp;
    }
    public boolean isP2Armor(){
        return isP2Armor;
    }

    public void setP2Armor(boolean p2Armor) {
        isP2Armor = p2Armor;
    }

    public Buff getBuff(){
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
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

    public Texture getP1_bar() {
        return p1_bar;
    }

    public void setP1_bar(Texture p1_bar) {
        this.p1_bar = p1_bar;
    }

    public Texture getP2_bar() {
        return p2_bar;
    }

    public void setP2_bar(Texture p2_bar) {
        this.p2_bar = p2_bar;
    }

    public Sprite getP1_border() {
        return p1_border;
    }

    public void setP1_border(Sprite p1_border) {
        this.p1_border = p1_border;
    }

    public Sprite getP2_border() {
        return p2_border;
    }

    public void setP2_border(Sprite p2_border) {
        this.p2_border = p2_border;
    }
}
