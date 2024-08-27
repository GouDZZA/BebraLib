package by.likebebras.bebralib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@UtilityClass
public class ParticleUtil {
    public void spawnParticleCircle(Location loc, Particle particle, double velocity, int points, double offsetX, double offsetY, double offsetZ, double startRadius) {
        Location center = loc.clone().add(offsetX, offsetY, offsetZ);

        for (int i = 0; i < points; i++) {
            double angle = 2 * Math.PI * i / points;

            double x = Math.cos(angle) * startRadius;
            double z = Math.sin(angle) * startRadius;

            Location particleLocation = center.clone().add(new Vector(x, 0, z));
            Vector direction = new Vector(x, 0, z).normalize();

            center.getWorld().spawnParticle(particle, particleLocation, 0, direction.getX(), direction.getY(), direction.getZ(), velocity);
        }
    }
}
