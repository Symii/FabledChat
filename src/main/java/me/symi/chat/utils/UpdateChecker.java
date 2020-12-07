package me.symi.chat.utils;

import me.symi.chat.OwnChat;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;

public class UpdateChecker {

    private OwnChat plugin;
    private int resourceId;

    public UpdateChecker(OwnChat plugin, int resourceId)
    {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getVersion(final Consumer<String> consumer)
    {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () ->
        {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream(); Scanner scanner = new Scanner(inputStream))
            {
                if (scanner.hasNext())
                {
                    consumer.accept(scanner.next());
                }
            }
            catch (IOException exception)
            {
                this.plugin.getLogger().info("Cannot look for updates: " + exception.getMessage());
            }
        });
    }

}