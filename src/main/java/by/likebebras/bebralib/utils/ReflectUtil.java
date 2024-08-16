package by.likebebras.bebralib.utils;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;

@UtilityClass
public class ReflectUtil {
    public final String VERSION = Bukkit.getServer().getClass().getPackage().getName().split("//")[3];


    public static <T> T getFieldContent(Class<?> clazz, final String field, final Object o) {
        final String originalClassName = new String(clazz.getName());

        do
            for (final Field f : clazz.getDeclaredFields())
                if (f.getName().equals(field))
                    return (T) getFieldContent(f, o);
        while (!(clazz = clazz.getSuperclass()).isAssignableFrom(Object.class));

        LogUtil.warn("No such field " + field + " in " + originalClassName + " or its superclasses");
        return null;
    }

    public static Object getFieldContent(final Field field, final Object o) {
        try {
            field.setAccessible(true);

            return field.get(o);

        } catch (final ReflectiveOperationException e) {
            LogUtil.warn("no such field: " + field.getName() + " in " + o.getClass().getName());
            return null;
        }
    }

    public Class<?> getNMSClass(String name) {
        try {
            return Class.forName("net.minecraft.server." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            LogUtil.warn("cannot found class: " + name);

            return null;
        }
    }
    public Class<?> getCBClass(String name){
        try {
            return Class.forName("org.bukkit.craftbukkit." + VERSION + "." + name);
        } catch (ClassNotFoundException e) {
            LogUtil.warn("cannot found class: " + name);

            return null;
        }
    }
 }
