package com.fenix.balance.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.fenix.balance.BalanceClass;

class SettingsScreen implements Screen{

    //Game Class
    private final BalanceClass game;

    //Accessing storage
    private Preferences storage;

    //Variable Definitions
    private Vector3 background_colour;
    private Vector3 newColour;

    //UI Variables
    private Stage stage;

    //Constructor Method
    SettingsScreen(final BalanceClass game_class) {

        //Define Game Class
        this.game = game_class;

        //Accessing storage
        storage = Gdx.app.getPreferences("Storage");

        //Disable back button from exiting the game
        Gdx.input.setCatchBackKey(true);

        //Get the background colour
        background_colour = game.colour;
        newColour = background_colour;

        //Setting background color button textures
        Texture bgAmber = new Texture(Gdx.files.internal("bgAmber.png"));
        Texture bgBlue = new Texture(Gdx.files.internal("bgBlue.png"));
        Texture bgBlueGray = new Texture(Gdx.files.internal("bgBlueGray.png"));
        Texture bgCyan = new Texture(Gdx.files.internal("bgCyan.png"));
        Texture bgDefault = new Texture(Gdx.files.internal("bgDefalt.png"));
        Texture bgGreen = new Texture(Gdx.files.internal("bgGreen.png"));
        Texture bgPurple = new Texture(Gdx.files.internal("bgPurple.png"));
        Texture bgRed = new Texture(Gdx.files.internal("bgRed.png"));

        //Setting background color Scene2D images
        Image Amber = new Image(bgAmber);
        Image Blue = new Image(bgBlue);
        Image BlueGray = new Image(bgBlueGray);
        Image Cyan = new Image(bgCyan);
        Image Default = new Image(bgDefault);
        Image Green = new Image(bgGreen);
        Image Purple = new Image(bgPurple);
        Image Red = new Image(bgRed);

        //Set bounds for every Image
        Amber.setBounds(0, 0, 220*game.scaling, 220*game.scaling);
        Blue.setBounds(0, 0, 220*game.scaling, 220*game.scaling);
        BlueGray.setBounds(0, 0, 220*game.scaling, 220*game.scaling);
        Cyan.setBounds(0, 0, 220*game.scaling, 220*game.scaling);
        Default.setBounds(0, 0, 220*game.scaling, 220*game.scaling);
        Green.setBounds(0, 0, 220*game.scaling, 220*game.scaling);
        Purple.setBounds(0, 0, 220*game.scaling, 220*game.scaling);
        Red.setBounds(0, 0, 220*game.scaling, 220*game.scaling);

        //Setup stage
        stage = new Stage(new ScreenViewport());

        //Setup table
        Table settingsTable = new Table();
        settingsTable.setFillParent(true);
        stage.addActor(settingsTable);

        //Setup skin
        Skin skin = new Skin();
        skin.add("blue_gray", new Color(Color.valueOf("#263238")));
        skin.add("roboto", game.font);

        //Setup table UI elements
        Label chooseColour = new Label("Choose a colour:", skin, "roboto", "blue_gray");
        Label confirmSettings = new Label("Done", skin, "roboto", "blue_gray");

        //Setting up table contents
        settingsTable.center();
        settingsTable.add(chooseColour).colspan(4);
        settingsTable.row();
        settingsTable.add(Amber).pad(20*game.scaling).height(220*game.scaling).width(220*game.scaling);
        settingsTable.add(Blue).pad(20*game.scaling).height(220*game.scaling).width(220*game.scaling);
        settingsTable.add(BlueGray).pad(20*game.scaling).height(220*game.scaling).width(220*game.scaling);
        settingsTable.add(Cyan).pad(20*game.scaling).height(220*game.scaling).width(220*game.scaling);
        settingsTable.row();
        settingsTable.add(Default).pad(20*game.scaling).height(220*game.scaling).width(220*game.scaling);
        settingsTable.add(Green).pad(20*game.scaling).height(220*game.scaling).width(220*game.scaling);
        settingsTable.add(Purple).pad(20*game.scaling).height(220*game.scaling).width(220*game.scaling);
        settingsTable.add(Red).pad(20*game.scaling).height(220*game.scaling).width(220*game.scaling);
        settingsTable.row();
        settingsTable.add(confirmSettings).colspan(4).padTop(300*game.scaling);

        //Set up colour button listeners
        Amber.addListener(new InputListener(){

            //Touch down event
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                newColour = new Vector3(1.00000f,0.83529f,0.30980f);

                background_colour = newColour;

                return true;
            }

        });

        Blue.addListener(new InputListener(){

            //Touch down event
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                newColour = new Vector3(0.39216f,0.70980f,0.96471f);

                background_colour = newColour;

                return true;

            }

        });

        BlueGray.addListener(new InputListener(){

            //Touch down event
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                newColour = new Vector3(0.56471f,0.64314f,0.68235f);

                background_colour = newColour;

                return true;

            }

        });

        Cyan.addListener(new InputListener(){

            //Touch down event
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                newColour = new Vector3(0.30196f,0.81569f,0.88235f);

                background_colour = newColour;

                return true;

            }

        });

        Default.addListener(new InputListener(){

            //Touch down event
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                newColour = new Vector3(0, 0.58823529411f, 0.53333333333f);

                background_colour = newColour;

                return true;

            }

        });

        Green.addListener(new InputListener(){

            //Touch down event
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                newColour = new Vector3(0.50588f,0.78039f,0.51765f);

                background_colour = newColour;

                return true;

            }

        });

        Purple.addListener(new InputListener(){

            //Touch down event
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                newColour = new Vector3(0.72941f,0.40784f,0.78431f);

                background_colour = newColour;

                return true;
            }

        });

        Red.addListener(new InputListener(){

            //Touch down event
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                newColour = new Vector3(0.89804f,0.45098f,0.45098f);

                background_colour = newColour;

                return true;

            }

        });

        //Setup confirmSettings label input
        confirmSettings.addListener(new InputListener(){

            //confirmSettings touchDown event method
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {

                //Update colours
                game.colour = newColour;
                storage.putFloat("R", newColour.x);
                storage.putFloat("G", newColour.y);
                storage.putFloat("B", newColour.z);
                storage.flush();

                //Set screen to GameScreen
                game.setScreen(new GameScreen(game));

                //Dispose of current screen
                dispose();

                return true;

            }

            //confirmSettings keyDown event method
            public boolean keyDown(InputEvent event, int keycode){

                if(keycode == Input.Keys.BACK){

                    //Set screen to GameScreen
                    game.setScreen(new GameScreen(game));

                    //Dispose of current screen
                    dispose();

                }

                return true;

            }


        });

        //Set keyboard focus to the confirmSettings actor
        stage.setKeyboardFocus(confirmSettings);

        //Set the stage as the input processor
        Gdx.input.setInputProcessor(stage);

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        //IMPORTANT
        Gdx.gl.glClearColor(background_colour.x, background_colour.y, background_colour.z, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Stage loop
        stage.act(delta);
        stage.draw();

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
