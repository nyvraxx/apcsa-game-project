package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Transform;

import util.ImageUtils;

public class Zombie extends Entity {
	public Zombie() {
		super();
	}

	@Override
	public BodyDef getBodyDef() {
		return defaultBodyDef;
	}

	@Override
	public void configureBody(Body body) {
		body.createFixture(createFixtureDef());
	}

	private static BodyDef defaultBodyDef;

	static {
		defaultBodyDef = new BodyDef();

		defaultBodyDef.type = BodyDef.BodyType.DynamicBody;
		defaultBodyDef.allowSleep = false;
		defaultBodyDef.active = true;
		defaultBodyDef.fixedRotation = true;
		defaultBodyDef.linearDamping = 8f;
	}

	private FixtureDef createFixtureDef() {
		FixtureDef defaultFixtureDef;
		defaultFixtureDef = new FixtureDef();
		defaultFixtureDef.friction = 0.1f;
		defaultFixtureDef.restitution = 0f;
		defaultFixtureDef.shape = new CircleShape();
		defaultFixtureDef.shape.setRadius(0.2f);
		defaultFixtureDef.density = 0.05f;
		return defaultFixtureDef;
	}

	@Override
	public void updateSprite(Sprite sprite) {
		Transform transform = getBody().getTransform();

		Vector2 pos = transform.getPosition();

		sprite.setTexture(ImageUtils.OhNoTexture);
		sprite.setOriginCenter();
		sprite.setPosition(pos.x - 0.2f, pos.y - 0.2f);
		sprite.setSize(0.4f, 0.4f);
		sprite.setRotation(MathUtils.radiansToDegrees * transform.getRotation());
	}
}
