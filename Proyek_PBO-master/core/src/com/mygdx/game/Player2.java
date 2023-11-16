package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player2 extends Players {
    public Player2() {
        this.generatePlayerAnimation();
    }
    private GameScreen gameScreen;
    private Direction animationDirection = Direction.LEFT;
    private Direction direction = Direction.LEFT;
    private State state = State.IDLE;
    private Action act = Action.NO_ATTACK;


    enum State {
        IDLE,
        RUN,
        JUMP,
        FALL
    }
    enum Direction {
        LEFT,
        RIGHT
    }

    enum Action {
        ATTACK,
        NO_ATTACK,
        HITTED
    }

    private Animation<TextureRegion> idleLeftAnimation, runLeftAnimation, idleRightAnimation, runRightAnimation, runRightJump, runLeftJump, runRightAttack, runLeftAttack,
            runRightDeath, runLeftDeath, runRightHitted, runLeftHitted, runRightFall, runLeftFall;

    private MyGdxGame app;

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public Direction getAnimationDirection() {
        return animationDirection;
    }

    public void setAnimationDirection(Direction animationDirection) {
        this.animationDirection = animationDirection;
    }

    public Animation<TextureRegion> getIdleLeftAnimation() {
        return idleLeftAnimation;
    }

    public void setIdleLeftAnimation(Animation<TextureRegion> idleLeftAnimation) {
        this.idleLeftAnimation = idleLeftAnimation;
    }

    public Animation<TextureRegion> getRunLeftAnimation() {
        return runLeftAnimation;
    }

    public void setRunLeftAnimation(Animation<TextureRegion> runLeftAnimation) {
        this.runLeftAnimation = runLeftAnimation;
    }

    public Animation<TextureRegion> getIdleRightAnimation() {
        return idleRightAnimation;
    }

    public void setIdleRightAnimation(Animation<TextureRegion> idleRightAnimation) {
        this.idleRightAnimation = idleRightAnimation;
    }

    public Animation<TextureRegion> getRunRightAnimation() {
        return runRightAnimation;
    }

    public void setRunRightAnimation(Animation<TextureRegion> runRightAnimation) {
        this.runRightAnimation = runRightAnimation;
    }

    public Animation<TextureRegion> getRunRightJump() {
        return runRightJump;
    }

    public void setRunRightJump(Animation<TextureRegion> runRightJump) {
        this.runRightJump = runRightJump;
    }

    public Animation<TextureRegion> getRunLeftJump() {
        return runLeftJump;
    }

    public void setRunLeftJump(Animation<TextureRegion> runLeftJump) {
        this.runLeftJump = runLeftJump;
    }

    public Animation<TextureRegion> getRunRightAttack() {
        return runRightAttack;
    }

    public void setRunRightAttack(Animation<TextureRegion> runRightAttack) {
        this.runRightAttack = runRightAttack;
    }

    public Animation<TextureRegion> getRunLeftAttack() {
        return runLeftAttack;
    }

    public void setRunLeftAttack(Animation<TextureRegion> runLeftAttack) {
        this.runLeftAttack = runLeftAttack;
    }

    public Animation<TextureRegion> getRunRightDeath() {
        return runRightDeath;
    }

    public void setRunRightDeath(Animation<TextureRegion> runRightDeath) {
        this.runRightDeath = runRightDeath;
    }

    public Animation<TextureRegion> getRunLeftDeath() {
        return runLeftDeath;
    }

    public void setRunLeftDeath(Animation<TextureRegion> runLeftDeath) {
        this.runLeftDeath = runLeftDeath;
    }

    public Animation<TextureRegion> getRunRightHitted() {
        return runRightHitted;
    }

    public void setRunRightHitted(Animation<TextureRegion> runRightHitted) {
        this.runRightHitted = runRightHitted;
    }

    public Animation<TextureRegion> getRunLeftHitted() {
        return runLeftHitted;
    }

    public void setRunLeftHitted(Animation<TextureRegion> runLeftHitted) {
        this.runLeftHitted = runLeftHitted;
    }

    public Animation<TextureRegion> getRunRightFall() {
        return runRightFall;
    }

    public void setRunRightFall(Animation<TextureRegion> runRightFall) {
        this.runRightFall = runRightFall;
    }

    public Animation<TextureRegion> getRunLeftFall() {
        return runLeftFall;
    }

    public void setRunLeftFall(Animation<TextureRegion> runLeftFall) {
        this.runLeftFall = runLeftFall;
    }

    public MyGdxGame getApp() {
        return app;
    }

    public void setApp(MyGdxGame app) {
        this.app = app;
    }

    public void generatePlayerAnimation()
    {
        app = (MyGdxGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = app.getManager();
        gameScreen = app.getGameScreen();

        Texture idle = assetManager.get("Sprite2/Idle.png");
        Texture run = assetManager.get("Sprite2/Run.png");
        Texture attack1 = assetManager.get("Sprite2/Attack1.png");
        Texture jump = assetManager.get("Sprite2/Jump.png");
        Texture death = assetManager.get("Sprite2/Death.png");
        Texture hitted = assetManager.get("Sprite2/Take Hit.png");
        Texture fall = assetManager.get("Sprite2/Fall.png");
        setTakeDamage(assetManager.get("take-damage.mp3", Sound.class));


        //membuat animasi diam hadap kanan
        TextureRegion[] frames = MyGdxGame.CreateAnimationFrames(idle, idle.getWidth()/4, idle.getHeight(), 4, false, false);
        idleRightAnimation = new Animation<TextureRegion>(0.09f, frames);

        //membuat animasi diam hadap kiri, sumbu x di-flip
        frames = MyGdxGame.CreateAnimationFrames(idle, idle.getWidth()/4, idle.getHeight(), 4, true, false);
        idleLeftAnimation = new Animation<TextureRegion>(0.09f, frames);

        //membuat animasi jalan hadap kanan
        frames = MyGdxGame.CreateAnimationFrames(run, run.getWidth()/8, run.getHeight(), 8, false, false);
        runRightAnimation = new Animation<TextureRegion>(0.09f, frames);

        //membuat animasi jalan hadap kiri, sumbu x di-flip
        frames = MyGdxGame.CreateAnimationFrames(run, run.getWidth()/8, run.getHeight(), 8, true, false);
        runLeftAnimation = new Animation<TextureRegion>(0.09f, frames);

        //animasi loncat ke kanan
        frames = MyGdxGame.CreateAnimationFrames(jump, jump.getWidth()/2, jump.getHeight(), 2, false, false);
        runRightJump = new Animation<TextureRegion>(0.09f, frames);

        //animasi loncat ke kiri
        frames = MyGdxGame.CreateAnimationFrames(jump, jump.getWidth()/2, jump.getHeight(), 2, true, false);
        runLeftJump = new Animation<TextureRegion>(0.09f, frames);

        //attack hadap kanan
        frames = MyGdxGame.CreateAnimationFrames(attack1, attack1.getWidth()/4, attack1.getHeight(), 4, false, false);
        runRightAttack = new Animation<TextureRegion>(0.09f, frames);

        //attack hadap kiri
        frames = MyGdxGame.CreateAnimationFrames(attack1, attack1.getWidth()/4, attack1.getHeight(), 4, true, false);
        runLeftAttack = new Animation<TextureRegion>(0.09f, frames);

        //mati hadap kanan
        frames = MyGdxGame.CreateAnimationFrames(death, death.getWidth()/7, death.getHeight(), 7, false, false);
        runRightDeath = new Animation<TextureRegion>(0.09f, frames);

        //mati hadap kiri
        frames = MyGdxGame.CreateAnimationFrames(death, death.getWidth()/7, death.getHeight(), 7, true, false);
        runLeftDeath = new Animation<TextureRegion>(0.09f, frames);

        //kena hit dari kanan
        frames = MyGdxGame.CreateAnimationFrames(hitted, hitted.getWidth()/3, hitted.getHeight(), 3, false, false);
        runRightHitted = new Animation<TextureRegion>(0.09f, frames);


        //kena hit dari kiri
        frames = MyGdxGame.CreateAnimationFrames(hitted, hitted.getWidth()/3, hitted.getHeight(), 3, true, false);
        runLeftHitted= new Animation<TextureRegion>(0.09f, frames);


        frames = MyGdxGame.CreateAnimationFrames(fall, fall.getWidth()/2, fall.getHeight(), 2, false, false);
        runRightFall = new Animation<TextureRegion>(0.09f, frames);

        frames = MyGdxGame.CreateAnimationFrames(fall, fall.getWidth()/2, fall.getHeight(), 2, true, false);
        runLeftFall = new Animation<TextureRegion>(0.09f, frames);

    }

    public void draw(SpriteBatch batch)
    {
        gameScreen = app.getGameScreen();
        TextureRegion currentFrame = null;

        if (getHP() <= 0) {
            if (animationDirection == Direction.LEFT) {
                currentFrame = runLeftDeath.getKeyFrame(getStateTime(), false);
            } else if (animationDirection == Direction.RIGHT) {
                currentFrame = runRightDeath.getKeyFrame(getStateTime(), false);
            }
        }
        else {
            if (state == State.RUN && animationDirection == Direction.LEFT && act == Action.NO_ATTACK)
                currentFrame = runLeftAnimation.getKeyFrame(getStateTime(), true);
            else if (state == State.RUN && animationDirection == Direction.RIGHT && act == Action.NO_ATTACK)
                currentFrame = runRightAnimation.getKeyFrame(getStateTime(), true);
            else if (state == State.IDLE && animationDirection == Direction.LEFT && act == Action.NO_ATTACK)
                currentFrame = idleLeftAnimation.getKeyFrame(getStateTime(), true);
            else if (state == State.IDLE && animationDirection == Direction.RIGHT && act == Action.NO_ATTACK)
                currentFrame = idleRightAnimation.getKeyFrame(getStateTime(), true);
            else if ((state == State.IDLE || state == State.RUN || state == State.JUMP) && animationDirection == Direction.RIGHT && act == Action.ATTACK) {
                currentFrame = runRightAttack.getKeyFrame(getStateTime(), true);
                if (state == State.RUN) Stop();
                if (getStateTime() >= Gdx.graphics.getDeltaTime() * 17) {
                    if (gameScreen.getP2().canHit(gameScreen.getP1())) {
                        getTakeDamage().play();
                        gameScreen.getP1().setHP(gameScreen.getP1().getHP()-gameScreen.getP2().getDmg());
                        gameScreen.getFontcache1().setText(String.format("Player 1 HP: %.2f",gameScreen.getP1().getHP()), 120, 500);
                        gameScreen.getP1_hp().setSize((float) (450 * gameScreen.getP1().getHP()/100.0), gameScreen.getP1_hp().getHeight());
                        gameScreen.getP1().doAction(Player1.Action.HITTED);
                    }
                    doAction(Action.NO_ATTACK);
                }
            } else if ((state == State.IDLE || state == State.RUN || state == State.JUMP) && animationDirection == Direction.LEFT && act == Action.ATTACK) {
                currentFrame = runLeftAttack.getKeyFrame(getStateTime(), true);
                if (state == State.RUN) Stop();
                if (getStateTime() >= Gdx.graphics.getDeltaTime() * 17) {
                    if (gameScreen.getP2().canHit(gameScreen.getP1())) {
                        getTakeDamage().play();
                        gameScreen.getP1().setHP(gameScreen.getP1().getHP()-gameScreen.getP2().getDmg());
                        gameScreen.getFontcache1().setText(String.format("Player 1 HP: %.2f",gameScreen.getP1().getHP()), 120, 500);
                        gameScreen.getP1_hp().setSize((float) (450*gameScreen.getP1().getHP()/100.0), gameScreen.getP1_hp().getHeight());
                        gameScreen.getP1().doAction(Player1.Action.HITTED);
                    }
                    doAction(Action.NO_ATTACK);
                }
            } else if ((state == State.JUMP) && animationDirection == Direction.RIGHT) {
                currentFrame = runRightJump.getKeyFrame(getStateTime(), true);
            } else if (state == State.FALL && animationDirection == Direction.RIGHT) {
                currentFrame = runRightFall.getKeyFrame(getStateTime(), true);
            } else if ((state == State.JUMP) && animationDirection == Direction.LEFT) {
                currentFrame = runLeftJump.getKeyFrame(getStateTime(), true);
            } else if (state == State.FALL && animationDirection == Direction.LEFT) {
                currentFrame = runLeftFall.getKeyFrame(getStateTime(), true);
            }
            if (act == Action.HITTED) {
                if (animationDirection == Direction.LEFT) {
                    currentFrame = runLeftHitted.getKeyFrame(getStateTime(), true);
                }
                else if (animationDirection == Direction.RIGHT) {
                    currentFrame = runRightHitted.getKeyFrame(getStateTime(), true);
                }
                if (getStateTime() >= Gdx.graphics.getDeltaTime() * 9) doAction(Action.NO_ATTACK);
            }
        }

        batch.draw(currentFrame, getX()-100, getY()-100);
    }

    public void update()
    {
        setRatioHP(getHP()/100);
        float elapsed = Gdx.graphics.getDeltaTime();
        setStateTime(getStateTime() + elapsed);
        if (getHP() > 0) {


            setX(getX() + getDx() * getSpeed() * elapsed);
            if (getX() > MyGdxGame.WORLD_WIDTH - 20) {
                setX(MyGdxGame.WORLD_WIDTH - 20);
                this.Stop();
            } else if (getX() < 20) {
                setX(20);
                this.Stop();
            }


            setY(getY() + getDy() * getSpeed() * elapsed);
            if (getY() > 200 + 120) {
                setY(300);
                setDy(-1);
                this.Jump(State.FALL);
            } else if (getY() < 140) {
                setY(140);
                this.Stop();
            }
            if (act == Action.HITTED) {
                if (gameScreen.getP1().getDirection() == Player1.Direction.RIGHT) {
                    setX(getX() + getSpeed() * elapsed * 0.8f);
                }
                else if (gameScreen.getP1().getDirection() == Player1.Direction.LEFT) {
                    setX(getX() - getSpeed() * elapsed * 0.8f);
                }

            }
        }
    }

    void Jump (State s){
        if (getHP() > 0) {
            if ((state == State.IDLE || state == State.FALL || state == State.RUN) && s == State.JUMP) {
                state = State.JUMP;
                setDy(1);
            }
            if ((state == State.JUMP || state == State.IDLE || state == State.RUN) && s == State.FALL) {
                state = State.FALL;
                setDy(-1);
            }
        }
    }

    public void setMove(Direction d)
    {
        if (getHP() > 0) {
            direction = d;

            if (animationDirection == Direction.LEFT && d == Direction.RIGHT) {
                animationDirection = Direction.RIGHT;
                setStateTime(0);
            } else if (animationDirection == Direction.RIGHT && d == Direction.LEFT) {
                animationDirection = Direction.LEFT;
                setStateTime(0);
            }
            if (d == Direction.RIGHT) {
                setDx(1);
                setDy(0);
            } else if (d == Direction.LEFT) {
                setDx(-1);
                setDy(0);
            }

            if (getY() == 140) {
                state = State.RUN; // update state dan arah animasi
            } else {
                if (state == State.FALL) {
                    setDy(-1);
                }
                if (state == State.JUMP) {
                    setDy(1);
                }
            }
        }
    }


    void doAction (Action a) {
        float elapsed = Gdx.graphics.getDeltaTime();
        setStateTime(getStateTime() + elapsed);
        if (getHP() > 0) {
            if (a == Action.NO_ATTACK && (act == Action.ATTACK || act == Action.HITTED)) {
                act = a;
                setStateTime(0);
            }
            else if (a == Action.ATTACK && (act == Action.NO_ATTACK || act == Action.HITTED)) {
                act = a;
                setStateTime(0);
            }
            else if (a == Action.HITTED && (act == Action.NO_ATTACK || act == Action.ATTACK)) {
                act = a;
                setStateTime(0);
            }
        }
    }


    void Stop()
    {
        if(state != State.IDLE && getY() == 140) {
            setDx(0);
            setDy(0);
            state = State.IDLE;
        }
        else if (getY() > 140) {
            state = State.FALL;
            setDy(-1);
        }
    }

    public void setHP(Double HP) {
        super.setHP(HP);
        if (getHP() < 0) setHP(0.0f);
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    public boolean canHit (Players p1) {
        if (getHP() > 0) {
            if (act == Action.NO_ATTACK) {
                return false;
            }
            else if (act == Action.ATTACK) {
                float jarak = 0;
                if (animationDirection == Direction.RIGHT) {
                    jarak = p1.getX() - getX();
                } else if (animationDirection == Direction.LEFT) {
                    jarak = getX() - p1.getX();
                }
                return jarak <= 100 && jarak > 10 && Math.abs(p1.getY() - getY()) <= 10;
            }
        }
        return false;
    }

    public Action getAct() {
        return act;
    }

    public void setAct(Action act) {
        this.act = act;
    }
}
