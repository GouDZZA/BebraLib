package by.likebebras.bebralib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@UtilityClass
public class ReflectUtil {
    private final VersionChecker versionChecker = new VersionChecker();


    public <T> T getFieldContent(Class<?> clazz, final String field, final Object o) {
        final String originalClassName = new String(clazz.getName());

        do
            for (final Field f : clazz.getDeclaredFields())
                if (f.getName().equals(field))
                    return (T) getFieldContent(f, o);
        while (!(clazz = clazz.getSuperclass()).isAssignableFrom(Object.class));

        LogUtil.warn("No such field " + field + " in " + originalClassName + " or its superclasses");
        return null;
    }

    public Object getFieldContent(final Field field, final Object o) {
        try {
            field.setAccessible(true);

            return field.get(o);

        } catch (final ReflectiveOperationException e) {
            LogUtil.warn("no such field: " + field.getName() + " in " + o.getClass().getName());
            return null;
        }
    }

    private static Object createPacket(String packetClassName, Object... params) throws Exception {
        Class<?> packetClass = getNMSClass(packetClassName);
        Constructor<?> constructor = packetClass.getConstructor(getClasses(params));
        return constructor.newInstance(params);
    }

    private static void sendPacket(Player player, Object packet) throws Exception {
        Object entityPlayer = getHandle(player);
        Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
        Method sendPacketMethod = playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"));
        sendPacketMethod.invoke(playerConnection, packet);
    }

    private static Object getHandle(Player player) throws Exception {
        Method getHandleMethod = player.getClass().getMethod("getHandle");
        return getHandleMethod.invoke(player);
    }


    private static Class<?> getNMSClass(String name) throws ClassNotFoundException {
        String version = versionChecker.getVersion();
        String fullName = versionChecker.isVersionAtLeast("v1_17_R1") ? "net.minecraft." + name : "net.minecraft.server." + version + "." + name;
        return Class.forName(fullName);
    }

    private static Class<?>[] getClasses(Object... objects) {
        Class<?>[] classes = new Class[objects.length];
        for (int i = 0; i < objects.length; i++) {
            classes[i] = objects[i].getClass();
        }
        return classes;
    }

    public Class<?> getCraftBukkitClass(String name){
        try {
            return Class.forName("org.bukkit.craftbukkit." + versionChecker.getVersion() + "." + name);
        } catch (ClassNotFoundException e) {
            LogUtil.warn("cannot found CraftBukkit class: " + name);

            return null;
        }
    }
 }
