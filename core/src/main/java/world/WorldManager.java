package world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entities.Barrier;
import entities.Entity;
import entities.Platform;
import entities.Player;
import entities.Stair;
import entities.Zombie;

public class WorldManager {
	Stage stage;

	Player player;
	GameWorld gameWorld;
	OrthographicCamera camera;
	FitViewport viewport;

	private float viewWidth = 16f, viewHeight = 10f;

	public WorldManager() {
		gameWorld = new GameWorld();
		camera = new OrthographicCamera();
		camera.position.set(0, 0, 0);
		viewport = new FitViewport(viewWidth, viewHeight, camera);
		stage = new Stage(getViewport());

		player = new Player();
		player.getLevelTracker().level = 0;
		gameWorld.add(player);

		// TODO debug code
		for (int i = 0; i < 10; i++) {
			Entity entity = new Zombie();
			entity.getLevelTracker().level = 0;
			gameWorld.add(entity);
			entity.getBody().setTransform(MathUtils.random(5), MathUtils.random(5), MathUtils.random(MathUtils.PI));
		}
		{
			Entity entity = new Barrier(5f, 0.1f);
			entity.getLevelTracker().level = 1;
			gameWorld.add(entity);
			entity.getBody().setTransform(-3, -3f, 0);
		}
		{
			Platform platform = new Stair(0, 1f, 3f);
			gameWorld.add(platform);
			platform.getBody().setTransform(-1, 1, 0);
		}
		{
			Platform platform = new Stair(1, 1f, 3f);
			gameWorld.add(platform);
			platform.getBody().setTransform(3, 1, 0);
		}
	}

	public void update(float delta) {
		gameWorld.update(delta);

		Vector2 playerPos = player.getBody().getPosition();
		camera.position.lerp(new Vector3(playerPos.x, playerPos.y, 0), 18f * delta);
		camera.update();
	}

	public void handleInput(float delta) {
		float dx = 0;
		float dy = 0;

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			dy++;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			dy--;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			dx--;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			dx++;
		}

		if (dx == 0 && dy == 0)
			return;

		float invMag = 1.0f / (float) Math.sqrt(dx * dx + dy * dy);
		dx *= invMag;
		dy *= invMag;

		if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT)) {
			player.startRunning();
		} else if (!Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			player.stopRunning();
		}

		player.move(dx, dy, delta);
	}

	public Camera getCamera() {
		return camera;
	}

	public GameWorld getGameWorld() {
		return gameWorld;
	}

	public Stage getStage() {
		return stage;
	}

	public FitViewport getViewport() {
		return viewport;
	}

	public Player getPlayer() {
		return player;
	}
}
