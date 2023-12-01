package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class User2 extends Characters {
    public User2() {
        parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        this.AnimatePlayer2();
        assetManager = parentGame.getManager();
    }

    private MyGdxGame parentGame;
    private GameScreen gameScreen;
    private Music hitSound;
    private AssetManager assetManager;

    //DEFAULT UNTUK USER 1 (PLAYER 1)
    private Direction animationDirection = Direction.LEFT;
    private Direction direction = Direction.LEFT;
    private State state = State.IDLE;
    private Action action = Action.SILENT;

    private Animation<TextureRegion> leftIdle, rightIdle, leftRunning,
            rightRunning, rightJump, leftJump, rightATK, leftATK,
            rightDead, leftDead, rightDamaged, leftDamaged, leftFalling, rightFalling;

    enum State {
        IDLE,
        RUNNING,
        JUMPING,
        FALLING
    }
    enum Action {
        ATTACKING,
        SILENT,
        DAMAGED
    } enum Direction {
        LEFT,
        RIGHT
    }




    public void AnimatePlayer2()
    {
        parentGame = (MyGdxGame) Gdx.app.getApplicationListener();
        AssetManager assetManager = parentGame.getManager();
        gameScreen = parentGame.getGameScreen();

        Texture idle = assetManager.get("Sprite2/Idle.png");
        Texture jump = assetManager.get("Sprite2/Jump.png");
        Texture run = assetManager.get("Sprite2/Run.png");
        Texture fall = assetManager.get("Sprite2/Fall.png");
        Texture mati = assetManager.get("Sprite2/Death.png");
        Texture attack1 = assetManager.get("Sprite2/Attack1.png");
        Texture kena_hit = assetManager.get("Sprite2/Take Hit.png");


        //ketika idle, hadap kanan
        TextureRegion[] frames = MyGdxGame.loadFrames(idle, idle.getWidth()/10, idle.getHeight(), 10, false, false);
        rightIdle = new Animation<TextureRegion>(0.09f, frames);

        //ketika idle, hadap kiri
        frames = MyGdxGame.loadFrames(idle, idle.getWidth()/10, idle.getHeight(), 10, true, false);
        leftIdle = new Animation<TextureRegion>(0.09f, frames);

        //ketika jalan, hadap kanan
        frames = MyGdxGame.loadFrames(run, run.getWidth()/10, run.getHeight(), 10, false, false);
        rightRunning = new Animation<TextureRegion>(0.09f, frames);

        //ketika jalan, hadap kiri
        frames = MyGdxGame.loadFrames(run, run.getWidth()/10, run.getHeight(), 10, true, false);
        leftRunning = new Animation<TextureRegion>(0.09f, frames);

        //ketika loncat ke kanan
        frames = MyGdxGame.loadFrames(jump, jump.getWidth()/3, jump.getHeight(), 3, false, false);
        rightJump = new Animation<TextureRegion>(0.09f, frames);

        //ketika loncat ke kiri
        frames = MyGdxGame.loadFrames(jump, jump.getWidth()/3, jump.getHeight(), 3, true, false);
        leftJump = new Animation<TextureRegion>(0.09f, frames);

        //ketika attack ke kanan
        frames = MyGdxGame.loadFrames(attack1, attack1.getWidth()/4, attack1.getHeight(), 4, false, false);
        rightATK = new Animation<TextureRegion>(0.06f, frames);

        //ketika attack ke kiri
        frames = MyGdxGame.loadFrames(attack1, attack1.getWidth()/4, attack1.getHeight(), 4, true, false);
        leftATK = new Animation<TextureRegion>(0.06f, frames);

        //ketika mati hadap kanan
        frames = MyGdxGame.loadFrames(mati, mati.getWidth()/10, mati.getHeight(), 10, false, false);
        rightDead = new Animation<TextureRegion>(0.09f, frames);

        //ketika mati hadap kiri
        frames = MyGdxGame.loadFrames(mati, mati.getWidth()/10, mati.getHeight(), 10, true, false);
        leftDead = new Animation<TextureRegion>(0.09f, frames);

        //kena hit dari kanan
        frames = MyGdxGame.loadFrames(kena_hit, kena_hit.getWidth()/1, kena_hit.getHeight(), 1, false, false);
        rightDamaged = new Animation<TextureRegion>(0.06f, frames);

        //kena hit dari kiri
        frames = MyGdxGame.loadFrames(kena_hit, kena_hit.getWidth()/1, kena_hit.getHeight(), 1, true, false);
        leftDamaged= new Animation<TextureRegion>(0.06f, frames);

        //jatuh ke kanan
        frames = MyGdxGame.loadFrames(fall, fall.getWidth()/3, fall.getHeight(), 3, false, false);
        rightFalling = new Animation<TextureRegion>(0.09f, frames);

        //jatuh ke kiri
        frames = MyGdxGame.loadFrames(fall, fall.getWidth()/3, fall.getHeight(), 3, true, false);
        leftFalling = new Animation<TextureRegion>(0.09f, frames);
    }

    public void draw(SpriteBatch batch) {
        gameScreen = parentGame.getGameScreen();
        TextureRegion currentFrame = null;

        switch (state) {
            case IDLE:
                currentFrame = handleIdleState();
                break;

            case RUNNING:
                currentFrame = handleRunningState();
                break;
            case JUMPING:
                currentFrame = handleJumpingState();
                break;
            case FALLING:
                currentFrame = handleFallingState();
                break;
        }
        if (this.getStateTime() >= Gdx.graphics.getDeltaTime() * 16) {
            if (gameScreen.getP2().userHIT(gameScreen.getP1())) {
                hitSound.play();
                gameScreen.getP1().userACT(User1.Action.DAMAGED);

            }
            userACT(User2.Action.SILENT);
        }

        if (action == Action.ATTACKING) {
            currentFrame = handleAttackingState();
        } else if (action == Action.DAMAGED) {
            currentFrame = handleDamagedState();
        }

        if (this.getHP() <= 0) {
            currentFrame = handleDeadState();
        }

        batch.draw(currentFrame, getX() - 20, getY() - 20);
    }

    private TextureRegion handleIdleState() {
        return animationDirection == Direction.LEFT ? leftIdle.getKeyFrame(this.getStateTime(), true) : rightIdle.getKeyFrame(this.getStateTime(), true);
    }

    private TextureRegion handleRunningState() {
        if (action == Action.ATTACKING) {
            StopChar();
        }
        return animationDirection == Direction.LEFT ? leftRunning.getKeyFrame(this.getStateTime(), true) : rightRunning.getKeyFrame(this.getStateTime(), true);
    }

    private TextureRegion handleJumpingState() {
        return animationDirection == Direction.LEFT ? leftJump.getKeyFrame(this.getStateTime(), true) : rightJump.getKeyFrame(this.getStateTime(), true);
    }

    private TextureRegion handleFallingState() {
        return animationDirection == Direction.LEFT ? leftFalling.getKeyFrame(this.getStateTime(), true) : rightFalling.getKeyFrame(this.getStateTime(), true);
    }

    private TextureRegion handleAttackingState() {
        if (this.getStateTime() >= Gdx.graphics.getDeltaTime() * 16) {
            userACT(Action.SILENT);
        }
        return animationDirection == Direction.LEFT ? leftATK.getKeyFrame(this.getStateTime(), true) : rightATK.getKeyFrame(this.getStateTime(), true);
    }

    private TextureRegion handleDamagedState() {
        if (this.getStateTime() >= Gdx.graphics.getDeltaTime() * 9) {
            userACT(Action.SILENT);
        }
        return animationDirection == Direction.LEFT ? leftDamaged.getKeyFrame(this.getStateTime(), true) : rightDamaged.getKeyFrame(this.getStateTime(), true);
    }

    private TextureRegion handleDeadState() {
        return animationDirection == Direction.LEFT ? leftDead.getKeyFrame(this.getStateTime(), false) : rightDead.getKeyFrame(this.getStateTime(), false);
    }
    public void update()
    {
        setRatioHP(getHP()/100);
        float elapsed = Gdx.graphics.getDeltaTime();
        this.setStateTime(getStateTime()+elapsed);
        if (getHP() > 0) {

            setX(getX() + getDx() * getSpeed() * elapsed);
            if (getX() > 1600) {
                setX(1600);
                StopChar();
            }
            else if (getX() < 0) {
                setX(0);
                StopChar();
            } else if (getX() > 1300) {
                setX(1300);
                StopChar();
            }



            setY(getY() + getDy() * getSpeed() * elapsed);
            if (getY() > 200 + 120) {
                setY(300);
                setDy(-1);
                this.userJump(State.FALLING);
            } else if (getY() < 140) {
                setY(140);
                this.StopChar();
            }

        }

    }



    public void userMove(Direction d) {
        if (getHP() <= 0) {
            return;
        }

        direction = d;
        if (animationDirection != d) {
            animationDirection = d;
            setStateTime(0);
        }

        setDx(d == Direction.RIGHT ? 1 : -1);
        setDy(0);

        if (getY() != 140) {
            setDy(state == State.FALLING ? -1 : 1);
        } else {
            state = State.RUNNING; // update state and animation direction
        }
    }

    void userACT(Action a) {
        float elapsed = Gdx.graphics.getDeltaTime();
        setStateTime(getStateTime() + elapsed);
        if (getHP() > 0) {
            if (a == Action.SILENT && (action == Action.DAMAGED || action == Action.ATTACKING)) {
                action = a;
                setStateTime(0);
            } else if (a == Action.ATTACKING && (action == Action.DAMAGED || action == Action.SILENT)) {
                action = a;
                setStateTime(0);
            }
            else if (a == Action.DAMAGED && (action == Action.SILENT || action == Action.ATTACKING)) {
                action = a;
                setStateTime(0);
            }
        }
    }

    void StopChar() {

        State currentState = state;


        float currentYPosition = getY();

        //kalau char di udara
        if(currentState != State.IDLE && currentYPosition == 140) {

            setDx(0);
            setDy(0);
            state = State.IDLE;
        }

        else if (currentYPosition > 140) {

            state = State.FALLING;
            setDy(-1);
        }
    }

    void userJump (State s){
        if (getHP() > 0) {
            if (s == State.JUMPING && state != State.JUMPING) {
                state = State.JUMPING;
                setDy(1);
            } else if (s == State.FALLING && state != State.FALLING) {
                state = State.FALLING;
                setDy(-1);
            }
        }
    }
    public void loadSound() {
        if (hitSound != null) {
            hitSound.stop();
            hitSound.dispose();
        }
        hitSound = assetManager.get("damaged.mp3", Music.class);
    }

    public boolean userHIT (Characters p2) {
    if (getHP() <= 0 || action == Action.SILENT) {
        return false;
    }
    if (action == Action.ATTACKING) {
        float jarak = animationDirection == Direction.RIGHT ? p2.getX() - getX() : getX() - p2.getX();
        if (jarak <= 140 && jarak > 10 && Math.abs(p2.getY() - getY()) <= 10) {
            //p1 mundur kalo ke hit
            if (animationDirection == Direction.RIGHT) {
                gameScreen.getP1().setX(gameScreen.getP1().getX() + 20);
            } else {
                gameScreen.getP1().setX(gameScreen.getP1().getX() - 20);
            }

            hitSound.play();
            return true;
        }
    }
    return false;
}

    //getter setter

    public MyGdxGame getApp() {
        return parentGame;
    }

    public void setApp(MyGdxGame app) {
        this.parentGame = app;
    }

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

    public Action getAct() {
        return action;
    }

    public void setAct(Action act) {
        this.action = act;
    }

    public Animation<TextureRegion> getIdleLeftAnimation() {
        return leftIdle;
    }

    public void setIdleLeftAnimation(Animation<TextureRegion> idleLeftAnimation) {
        this.leftIdle = idleLeftAnimation;
    }

    public Animation<TextureRegion> getRunLeftAnimation() {
        return leftRunning;
    }

    public void setRunLeftAnimation(Animation<TextureRegion> runLeftAnimation) {
        this.leftRunning = runLeftAnimation;
    }

    public Animation<TextureRegion> getIdleRightAnimation() {
        return rightIdle;
    }

    public void setIdleRightAnimation(Animation<TextureRegion> idleRightAnimation) {
        this.rightIdle = idleRightAnimation;
    }

    public Animation<TextureRegion> getRunRightAnimation() {
        return rightRunning;
    }

    public void setRunRightAnimation(Animation<TextureRegion> runRightAnimation) {
        this.rightRunning = runRightAnimation;
    }

    public Animation<TextureRegion> getRunRightJump() {
        return rightJump;
    }

    public void setRunRightJump(Animation<TextureRegion> runRightJump) {
        this.rightJump = runRightJump;
    }

    public Animation<TextureRegion> getRunLeftJump() {
        return leftJump;
    }

    public MyGdxGame getParentGame() {
        return parentGame;
    }

    public void setParentGame(MyGdxGame parentGame) {
        this.parentGame = parentGame;
    }

    public Music getHitSound() {
        return hitSound;
    }

    public void setHitSound(Music hitSound) {
        this.hitSound = hitSound;
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public void setAssetManager(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    public void setRunLeftJump(Animation<TextureRegion> runLeftJump) {
        this.leftJump = runLeftJump;
    }

    public Animation<TextureRegion> getRunRightAttack() {
        return rightATK;
    }

    public void setRunRightAttack(Animation<TextureRegion> runRightAttack) {
        this.rightATK = runRightAttack;
    }

    public Animation<TextureRegion> getRunLeftAttack() {
        return leftATK;
    }

    public void setRunLeftAttack(Animation<TextureRegion> runLeftAttack) {
        this.leftATK = runLeftAttack;
    }

    public Animation<TextureRegion> getRunRightDeath() {
        return rightDead;
    }

    public void setRunRightDeath(Animation<TextureRegion> runRightDeath) {
        this.rightDead = runRightDeath;
    }

    public Animation<TextureRegion> getRunLeftDeath() {
        return leftDead;
    }

    public void setRunLeftDeath(Animation<TextureRegion> runLeftDeath) {
        this.leftDead = runLeftDeath;
    }

    public Animation<TextureRegion> getRunRightHitted() {
        return rightDamaged;
    }

    public void setRunRightHitted(Animation<TextureRegion> runRightHitted) {
        this.rightDamaged = runRightHitted;
    }

    public Animation<TextureRegion> getRunLeftHitted() {
        return leftDamaged;
    }

    public void setRunLeftHitted(Animation<TextureRegion> runLeftHitted) {
        this.leftDamaged = runLeftHitted;
    }

    public Animation<TextureRegion> getRunLeftFall() {
        return leftFalling;
    }

    public void setRunLeftFall(Animation<TextureRegion> runLeftFall) {
        this.leftFalling = runLeftFall;
    }

    public Animation<TextureRegion> getRunRightFall() {
        return rightFalling;
    }

    public void setRunRightFall(Animation<TextureRegion> runRightFall) {
        this.rightFalling = runRightFall;
    }
}
