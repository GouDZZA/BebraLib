package by.likebebras.bebralib.utils;

import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ColorUtil {
    private final Pattern hexPattern = Pattern.compile("&?(#[a-f0-9]{6})", 2);


    public String hex(String message){
        if (message == null) return null;

        for(Matcher m = hexPattern.matcher(message); m.find(); message = message.replace(m.group(), ChatColor.of(m.group(1)).toString())){}

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public List<String> hex(List<String> list){
        list.replaceAll(ColorUtil::hex);
        return list;
    }
}
