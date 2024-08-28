package by.likebebras.bebralib.ez;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class EzSound {

    public static final EzSound DEFAULT = new EzSound(Sound.BLOCK_BAMBOO_HIT);

    private final Sound sound;
    private final float volume, pitch;

    public EzSound(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public EzSound(Sound sound){
        this.sound = sound;
        pitch = 1;
        volume = 1;
    }

    public void playTo(Player p){
        p.playSound(p.getLocation(), sound, volume, pitch);
    }

    public void play(Location loc){
        loc.getWorld().playSound(loc, sound, volume, pitch);
    }

    public void playInRadius(Location loc, double radius){
        loc.getNearbyEntities(radius, radius, radius).stream()
                .filter(e -> e instanceof Player)
                .forEach(p -> ((Player) p).playSound(loc, sound, volume, pitch));
    }
}
