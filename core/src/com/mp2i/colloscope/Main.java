package com.mp2i.colloscope;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mp2i.colloscope.graphic.UserInterface;

import java.io.IOException;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	UserInterface userInterface;

	OrthographicCamera camera;
	ScreenViewport viewport;
	// preference object to make group number persistent
	Preferences preference;


	@Override
	public void create () {
		batch = new SpriteBatch();
		userInterface = new UserInterface();
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		viewport.apply();

		userInterface.setMessage("Chargement du fichier excel...");

		//load group number
		preference = Gdx.app.getPreferences("main");
		//userInterface.setGroupNumber(preference.getInteger("groupNumber", 1));
		userInterface.setGroupNumber(5);



	}


	public void setColles() {

		// Check if file is loaded
		if (!excelFileReader.isLoaded()) {
			try {
				excelFileReader.LoadSheet();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}


		//save group number
		preference.putInteger("groupNumber", userInterface.getGroupNumber());
		preference.flush();

		try {
			this.userInterface.setColles(excelFileReader.getColles(userInterface.getGroupNumber()));
			this.userInterface.setGroupMembers(excelFileReader.getGroupNames(userInterface.getGroupNumber()));
			userInterface.setMessage("");
		} catch (Exception e) {
			e.printStackTrace();
			this.userInterface.setMessage("Il semblerait qu'il n'y ait pas de colles prévues cette semaine");
		}
	}



	@Override
	public void render () {



		Gdx.gl.glClearColor(0.156f, 0.08f, 0.211f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		batch.enableBlending();
		userInterface.update(batch);
		batch.end();

		if (userInterface.needsToBeRefreshed()) {
			this.setColles();
		}

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.position.set(Gdx.graphics.getWidth()/2f, Gdx.graphics.getHeight()/2f, 0);
		userInterface.resize();

	}
	
	@Override
	public void dispose () {


		batch.dispose();
		userInterface.disposeMembers();


	}
}
