package abilities;

import java.awt.Point;

import mainClasses.Ability;
import mainClasses.ArcForceField;
import mainClasses.Environment;
import mainClasses.Methods;
import mainClasses.Person;
import mainClasses.Player;
import mainResourcesPackage.SoundEffect;

public class Protective_Bubble_I extends Ability
{
	public ArcForceField bubble;

	public Protective_Bubble_I(int p)
	{
		super("Protective Bubble I", p);
		cooldown = 1;
		costType = "mana";
		cost = 3;
		instant = true;

		bubble = null;

		sounds.add(new SoundEffect("Protective Bubble_appear.wav"));
		sounds.add(new SoundEffect("Protective Bubble_pop.wav"));
	}

	public void use(Environment env, Person user, Point target)
	{
		setSounds(user.Point());
		// deactivating the bubble
		if (on && cooldownLeft == 0)
		{
			for (int i = 0; i < env.AFFs.size(); i++)
				if (env.AFFs.get(i).equals(bubble))
				{
					env.shieldDebris(bubble, "bubble");
					cooldownLeft = 0.5;
					on = false;
					env.AFFs.remove(i);
					i--;
					sounds.get(1).play();
				}
		} else if (!user.maintaining && !user.prone) // activating bubble
		{
			if (cost > user.mana || cooldownLeft > 0)
				return;

			// Remove any current protective bubble
			for (int i = 0; i < env.AFFs.size(); i++)
				if (env.AFFs.get(i).target.equals(user) && env.AFFs.get(i).type.equals("Protective Bubble"))
				{
					env.shieldDebris(env.AFFs.get(i), "bubble");
					env.AFFs.remove(i);
					i--;
				}

			// Add a new protective bubble
			bubble = new ArcForceField(user, 0, 2 * Math.PI, 60, 107, 10 * level, 12, "Protective Bubble");
			env.AFFs.add(bubble);
			user.mana -= this.cost;
			this.cooldownLeft = this.cooldown;
			this.on = true;
			sounds.get(0).play();
		}
	}

	public void maintain(Environment env, Person user, Point target, double deltaTime)
	{
		bubble.life -= bubble.life * 0.1 * deltaTime;
		bubble.rotation = Methods.lerpAngle(bubble.rotation, user.rotation, deltaTime);
	}

	public void updatePlayerTargeting(Environment env, Player player, Point target, double deltaTime)
	{
	}
}
