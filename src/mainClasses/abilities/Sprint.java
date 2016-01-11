package mainClasses.abilities;

import java.awt.Point;

import mainClasses.Ability;
import mainClasses.Environment;
import mainClasses.Person;
import mainClasses.Player;

public class Sprint extends Ability
{

	public Sprint(int p)
	{
		super("Sprint", p);
		costPerSecond = 3 - 0.3 * points;
	}

	public void use(Environment env, Person user, Point target)
	{
		if (cooldownLeft == 0 || on)
		{
			if (on)
				cooldownLeft = cooldown;
			on = !on;
			user.maintaining = !user.maintaining;
		}
	}

	public void maintain(Environment env, Person user, Point target, double deltaTime)
	{

	}

	public void updatePlayerTargeting(Environment env, Player player, Point target, double deltaTime)
	{

	}
}
