package com.fenix.balance;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {

        //Auto-generated stuff [Important]
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        //Set wanted config values
		config.useAccelerometer = false;
		config.useCompass = false;
        config.useImmersiveMode = true;

        //Initialize the main class
		initialize(new BalanceClass(), config);

	}
}
