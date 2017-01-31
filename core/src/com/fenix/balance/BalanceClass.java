package com.fenix.balance;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;

public class BalanceClass extends Game {

    //Define required variables
    public SpriteBatch batch;
    public BitmapFont font;
    public Vector3 colour;
    public int highscore = 0;
    private Preferences storage;
    public float scaling;
    public float ratio;

    public void create() {

        //Define the sprite batch
        batch = new SpriteBatch();

        //Set the background color
        Vector3 default_colour = new Vector3(0, 0.58823529411f, 0.53333333333f);

        //Initialize storage
        storage = Gdx.app.getPreferences("Storage");
        colour = new Vector3(storage.getFloat("R", default_colour.x), storage.getFloat("G", default_colour.y), storage.getFloat("B", default_colour.z));
        highscore = storage.getInteger("highscore", 0);

        //Set default font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Roboto-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 128;
        font = generator.generateFont(parameter);
        generator.dispose();

        //Activate the main screen
        this.setScreen(new com.fenix.balance.screens.GameScreen(this));

    }

    public void render() {

        //IMPORTANT
        super.render();

    }

    public void dispose() {

        //Disposal
        batch.dispose();
        font.dispose();

    }

    public void updateHighscore(int new_score){
        highscore = new_score;
        storage.putInteger("highscore", new_score);
        storage.flush();
    }

}
