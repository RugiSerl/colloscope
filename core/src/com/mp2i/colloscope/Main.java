package com.mp2i.colloscope;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mp2i.colloscope.graphic.UserInterface;
import com.mp2i.colloscope.graphic.components.Colors;

import java.io.IOException;
import java.util.Calendar;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	UserInterface userInterface;

	OrthographicCamera camera;
	ScreenViewport viewport;
	// preference object to make group number persistent
	Preferences preference;


	@Override
	public void create () {

		persistent.init();


		batch = new SpriteBatch();
		userInterface = new UserInterface();
		camera = new OrthographicCamera();
		viewport = new ScreenViewport(camera);
		viewport.apply();
		userInterface.setGroupNumber(persistent.preference.getInteger("groupNumber", 1));

		this.setColles();




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





		try {

			Calendar date = Calendar.getInstance();
			date.add(Calendar.DATE, 7*(this.userInterface.getWeekOffset()-1));

			Colles[] c = {null, null, null};
			for (int i = 0; i < 3; i ++) {
				c[i] = excelFileReader.getColles(userInterface.getGroupNumber(), date);
				date.add(Calendar.DATE, 7);
			}
			this.userInterface.setColles(c);



			//set the group members names
			this.userInterface.setGroupMembers(excelFileReader.getGroupNames(userInterface.getGroupNumber()));
			userInterface.setMessage("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	public void render () {



		Gdx.gl.glClearColor(Colors.backgroundColor.r, Colors.backgroundColor.g, Colors.backgroundColor.b, Colors.backgroundColor.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		batch.enableBlending();
		userInterface.update(batch);
		batch.end();
		if (userInterface.needsToBeRefreshed()) {
			this.setColles();
			//save group number
			persistent.preference.putInteger("groupNumber", userInterface.getGroupNumber());
			persistent.save();
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
