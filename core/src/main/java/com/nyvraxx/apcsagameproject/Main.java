package com.nyvraxx.apcsagameproject;

import com.badlogic.gdx.Game;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
	@Override
	public void create() {
		setScreen(new MainMenu(this));
	}
}