package org.systempro.project;

import com.badlogic.gdx.Game;
import org.systempro.project.cofeeGame.GameScreen;
import org.systempro.project.platformer.TestScreen;
import org.systempro.project.test3d.MengerSpongeTest;
import org.systempro.project.verlet2d.MeshSimulation;

public class Main extends Game {

	@Override
	public void create() {
		setScreen(new GameScreen());
	}

}
