package by.likebebras.bebralib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.entity.Player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@UtilityClass
public class ReflectUtil {

    public final VersionChecker versionChecker = new VersionChecker();

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

    public CommandMap getCommandMap(){
        try {
            Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            commandMapField.setAccessible(true);
            return  (CommandMap) commandMapField.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to get CommandMap", e);
        }
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

    public Object createPacket(String packetClassName, Object... params) throws Exception {
        Class<?> packetClass = getNMSClass(packetClassName);
        Constructor<?> constructor = packetClass.getConstructor(getClasses(params));
        return constructor.newInstance(params);
    }

    public void sendPacket(Player player, Object packet) throws Exception {
        Object entityPlayer = getHandle(player);
        Object playerConnection = entityPlayer.getClass().getField("playerConnection").get(entityPlayer);
        Method sendPacketMethod = playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"));
        sendPacketMethod.invoke(playerConnection, packet);
    }

    public Object getHandle(Object object) throws Exception {
        Method getHandleMethod = object.getClass().getMethod("getHandle");
        return getHandleMethod.invoke(object);
    }


    public Class<?> getNMSClass(String name) throws ClassNotFoundException {
        String version = versionChecker.getVersion();
        String fullName = versionChecker.isVersionAtLeast("v1_17_R1") ? "net.minecraft." + name : "net.minecraft.server." + version + "." + name;
        return Class.forName(fullName);
    }

    public Class<?>[] getClasses(Object... objects) {
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
