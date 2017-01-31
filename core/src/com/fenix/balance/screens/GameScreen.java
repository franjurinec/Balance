package com.fenix.balance.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fenix.balance.BalanceClass;

public class GameScreen implements Screen {

    //Game Class
    private final BalanceClass game;


    //Variable Definitions
    private OrthographicCamera camera;
    private Vector3 background_colour;
    private Vector2 resolution;
    private Stage stage;
    private Label final_score_label;
    private Label game_over_label;
    private Label retry_label;
    private Label highscore_label;
    private Box2DDebugRenderer Box2D_debug = new Box2DDebugRenderer();
    private float score = 0f;
    private int final_score = 0;
    private float gravity = -20f;
    private World world = new World(new Vector2(0, gravity), true);
    private Body stickBody;
    private Body circleBody;
    private MouseJoint hinge;
    private boolean controlling = false;
    private boolean first_touch = true;

    //Sprites
    private Sprite stickSprite;

    //Constructor Method
    public GameScreen(final BalanceClass game_class) {

        //Define Game Class
        this.game = game_class;

        //Enable back button to exit the game
        Gdx.input.setCatchBackKey(false);

        //Get the background colour
        background_colour = game.colour;

        //Store the resolution
        resolution = new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Set scale and ratio
        game.scaling = resolution.x / 1080;
        game.ratio = resolution.x / resolution.y;

        //Set font scale
        game.font.getData().setScale(game.scaling);

        //Camera Setup
        camera = new OrthographicCamera(1080/100f, (1080 / game.ratio)/100f);
        camera.setToOrtho(false, 1080/100.000f, (1080 / game.ratio)/100.000f);
        camera.position.set(0, 0, 0);

        //Initializing Box2D
        Box2D.init();

        //Stick sprite setup
        Texture stick_texture = new Texture(Gdx.files.internal("Stick.png"));
        stickSprite = new Sprite(stick_texture);
        stickSprite.setOrigin(37.5f, 37.5f);
        stickSprite.setScale(0.01f);

        //Stage Setup
        stage = new Stage(new ScreenViewport());

        //Skin setup
        Skin skin = new Skin();
        skin.add("blue_gray", new Color(Color.valueOf("#263238")));
        skin.add("roboto", game.font);

        //Setting up table stack
        Stack tablestack = new Stack();
        tablestack.setFillParent(true);
        stage.addActor(tablestack);

        //Settings button table Setup
        Table settingstable = new Table();
        settingstable.setFillParent(true);
        tablestack.addActor(settingstable);

        //End table setup
        Table endtable = new Table();
        endtable.setFillParent(true);
        tablestack.addActor(endtable);

        //Adding end-game Screen2D labels
        final_score_label = new Label(String.valueOf(final_score), skin, "roboto", "blue_gray");
        highscore_label = new Label(String.valueOf(game.highscore), skin, "roboto", "blue_gray");
        game_over_label = new Label("Game Over", skin, "roboto", "blue_gray");
        retry_label = new Label("Retry?", skin, "roboto", "blue_gray");
        endtable.center().add(game_over_label).spaceBottom(100*game.scaling);
        endtable.row();
        endtable.add(final_score_label).padBottom(50*game.scaling);
        endtable.row();
        endtable.add(highscore_label).padBottom(175*game.scaling);
        endtable.row();
        endtable.add(retry_label).padBottom(100*game.scaling);
        highscore_label.setVisible(false);
        final_score_label.setVisible(false);
        game_over_label.setVisible(false);
        retry_label.setVisible(false);

        //Setting up retry button listener
        retry_label.addListener(new InputListener() {

            //Retry touchDown event method
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                //Check if the retry label is visible
                if(retry_label.isVisible()){

                    //Reset screen
                    game.setScreen(new GameScreen(game));

                }

                return true;

            }

        });

        //Settings Icon Setup
        Texture cog_texture = new Texture(Gdx.files.internal("SettingsIcon.png"));
        Image cog_image = new Image(cog_texture);
        cog_image.setBounds(0, 0, 150*game.scaling, 150*game.scaling);
        settingstable.top().right().add(cog_image).pad(50*game.scaling).height(150*game.scaling).width(150*game.scaling);

        //Setting up setting button listener
        cog_image.addListener(new InputListener() {

            //Settings touchDown event method
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                //Set screen to Settings
                game.setScreen(new SettingsScreen(game));

                //Dispose of current screen
                dispose();

                return true;

            }

        });

        //Beginning of Box2D Setup

        //Create body definition for the stick
        BodyDef stickDef = new BodyDef();
        stickDef.type = BodyDef.BodyType.DynamicBody;
        stickDef.position.set(0, -4.15f);

        //Add the body for the stick to the world using the body definition
        stickBody = world.createBody(stickDef);

        //Defining the shape of the body using a polygon shape
        Vector2[] vertices = new Vector2[4];
        vertices[0] = new Vector2(0.375f  , 11.625f);
        vertices[1] = new Vector2(-0.375f , 11.625f);
        vertices[2] = new Vector2(-0.375f , -0.375f);
        vertices[3] = new Vector2(0.375f , -0.375f);
        PolygonShape stickCollisionShape = new PolygonShape();
        stickCollisionShape.set(vertices);

        //Defining the stick body fixture definition
        FixtureDef stickFixtureDef = new FixtureDef();
        stickFixtureDef.shape = stickCollisionShape;
        stickFixtureDef.density = 1.0f;
        stickFixtureDef.friction = 1.0f;
        stickFixtureDef.restitution = 0.2f;

        //Adding the fixture to the body
        Fixture stickFixture = stickBody.createFixture(stickFixtureDef);

        //Creating the control body definition
        BodyDef circleDef = new BodyDef();
        circleDef.type = BodyDef.BodyType.StaticBody;
        circleDef.position.set(0, -4.15f);

        //Add the control body to the world using the body definition
        circleBody = world.createBody(circleDef);

        //Create the shape for for the control body
        CircleShape circle = new CircleShape();
        circle.setRadius(0.5f);

        //Defining the fixture definition for the control body
        FixtureDef circleFixtureDef = new FixtureDef();
        circleFixtureDef.shape = circle;
        circleFixtureDef.isSensor = true;

        //Adding the fixture to the control body
        Fixture circleFixture = circleBody.createFixture(circleFixtureDef);

        //Setting damping values for the control body
        circleBody.setAngularDamping(0.3f);
        circleBody.setLinearDamping(0.3f);

        //Setting the damping values for the stick body
        stickBody.setAngularDamping(1f);
        stickBody.setLinearDamping(0.3f);

        //Defining the control hinge joint definition
        MouseJointDef hingeDef = new MouseJointDef();
        hingeDef.bodyA = circleBody;
        hingeDef.bodyB = stickBody;
        hingeDef.target.set(circleBody.getPosition());
        hingeDef.collideConnected = false;
        hingeDef.frequencyHz = 60;

        //Create the control hinge using the hinge joint definition
        hinge = (MouseJoint) world.createJoint(hingeDef);

        //Setting stick and control bodies to inactive at start
        stickBody.setActive(false);
        circleBody.setActive(false);

        //Disposing of unnecessary shapes
        stickCollisionShape.dispose();
        circle.dispose();

        //End of Box2D Setup

        //Setting up the input multiplexer
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);
        multiplexer.addProcessor(new InputAdapter() {

            //Game Input Methods
            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {

                //Calculate distance from input to stick starting point
                double distance = Math.hypot(x - resolution.x / 2, y - stickBody.getPosition().y - resolution.y / 2 - 4.15f * 100 * game.scaling);

                //Update variables if valid touch input
                if (distance < 150 && first_touch) {

                    controlling = true;
                    stickBody.setActive(true);
                    circleBody.setActive(true);
                    first_touch = false;

                }

                return true;
            }

            @Override
            public boolean touchUp (int x, int y, int pointer, int button) {

                //End Game
                end_game();

                return true;
            }

        });
        Gdx.input.setInputProcessor(multiplexer);

        //Timer Setup
        Timer timer = new Timer();
        timer.scheduleTask(new Timer.Task() {

            //Periodical Update
            @Override
            public void run() {

                if(controlling) {

                    //Increase Gravity
                    gravity = gravity - 0.25f;

                    //Update world gravity
                    world.setGravity(new Vector2(0, gravity));

                    //Add score
                    score = score + 17.5f;

                }

            }

        }, 0.5f, 0.5f);

    }

    //Main Loop
    @Override
    public void render(float delta) {

        //IMPORTANT
        Gdx.gl.glClearColor(background_colour.x, background_colour.y, background_colour.z, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Physics update
        world.step(1/60f, 6, 2);

        //Move stick joint target to touch position
        if(controlling) {

            circleBody.setTransform((Gdx.input.getX() - resolution.x / 2)/100.00f/game.scaling, (-Gdx.input.getY() + resolution.y / 2)/100.00f/game.scaling, 0);

            Vector2 target = new Vector2();
            hinge.setMaxForce(1000f * stickBody.getMass());
            hinge.setTarget(target.set(circleBody.getPosition()));

        }

        //Camera Update
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        //Rendering the stick sprite
        game.batch.begin();
        stickSprite.setPosition(stickBody.getPosition().x - stickSprite.getOriginX(), stickBody.getPosition().y - stickSprite.getOriginY());
        stickSprite.setRotation(stickBody.getTransform().getRotation()*57.3f);
        stickSprite.draw(game.batch);
        game.batch.end();

        //Stage loop
        stage.act(delta);
        stage.draw();

        //Box2D Debug Render
        //Box2D_debug.render(world, camera.combined);

        //Rotation break check
        if(Math.abs(stickBody.getTransform().getRotation()*57.3f) > 60f){

            //End Game
            end_game();

        }

    }

    @Override
    public void resize(int width, int height) {

        //Update stage on resize
        stage.getViewport().update(width, height, true);

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
    public void show() {

    }

    @Override
    public void dispose() {

        //Disposal
        stage.dispose();
        world.dispose();

    }

    //End Game Function
    private void end_game(){

        //Check if game is in progress
        if(controlling) {

            //Set controlling to false
            controlling = false;

            //Check for existing Box2D joints
            if (world.getJointCount() > 0) {

                //Destroy any existing joint
                world.destroyJoint(hinge);

            }

            //Set the final score
            final_score = Math.round(score);

            if(final_score > game.highscore){
                game.updateHighscore(final_score);
            }

            //Update the final score and set the labels to visible
            game_over_label.setVisible(true);
            retry_label.setVisible(true);
            final_score_label.setText("Score: " + String.valueOf(final_score));
            final_score_label.setVisible(true);
            highscore_label.setText("Highscore: " + String.valueOf(game.highscore));
            highscore_label.setVisible(true);

        }

    }

}
