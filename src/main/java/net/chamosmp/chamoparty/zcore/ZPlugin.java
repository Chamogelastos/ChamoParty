package net.chamosmp.chamoparty.zcore;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.chamosmp.chamoparty.adapter.PlayerAdapter;
import net.chamosmp.chamoparty.adapter.RewardAdapter;
import net.chamosmp.chamoparty.adapter.VoteAdapter;
import net.chamosmp.chamoparty.api.PlayerVote;
import net.chamosmp.chamoparty.api.Reward;
import net.chamosmp.chamoparty.api.Vote;
import net.chamosmp.chamoparty.api.enums.InventoryName;
import net.chamosmp.chamoparty.api.storage.Script;
import net.chamosmp.chamoparty.command.CommandManager;
import net.chamosmp.chamoparty.command.VCommand;
import net.chamosmp.chamoparty.exceptions.ListenerNullException;
import net.chamosmp.chamoparty.inventory.VInventory;
import net.chamosmp.chamoparty.inventory.ZInventoryManager;
import net.chamosmp.chamoparty.listener.ListenerAdapter;
import net.chamosmp.chamoparty.zcore.enums.EnumInventory;
import net.chamosmp.chamoparty.zcore.enums.Folder;
import net.chamosmp.chamoparty.zcore.logger.Logger;
import net.chamosmp.chamoparty.zcore.logger.Logger.LogType;
import net.chamosmp.chamoparty.zcore.utils.gson.LocationAdapter;
import net.chamosmp.chamoparty.zcore.utils.gson.PotionEffectAdapter;
import net.chamosmp.chamoparty.zcore.utils.plugins.Plugins;
import net.chamosmp.chamoparty.zcore.utils.storage.Persist;
import net.chamosmp.chamoparty.zcore.utils.storage.Saveable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public abstract class ZPlugin extends JavaPlugin {

    private final Logger log = new Logger("<aqua>" + this.getPluginMeta().getName());
    private final List<Saveable> savers = new ArrayList<>();
    private final List<ListenerAdapter> listenerAdapters = new ArrayList<>();
    private final List<String> files = new ArrayList<>();
    protected CommandManager commandManager;
    protected ZInventoryManager zInventoryManager;
    private Gson gson;
    private Persist persist;
    private long enableTime;

    protected void preEnable() {

        this.enableTime = System.currentTimeMillis();

        this.log.log("Enabling");
        this.log.log("Plugin Version V" + this.getPluginMeta().getVersion(), LogType.INFO);

        this.getDataFolder().mkdirs();

        for (Folder folder : Folder.values()) {
            File currentFolder = new File(this.getDataFolder(), folder.toFolder());
            if (!currentFolder.exists()) currentFolder.mkdir();
        }

        this.gson = getGsonBuilder().create();
        this.persist = new Persist(this);

        for (String file : this.files) {
            if (!new File(getDataFolder() + "/inventories/" + file + ".yml").exists()) {
                saveResource("inventories/" + file + ".yml", false);
            }
        }

        for (Script script : Script.values()) {
            if (!new File(getDataFolder() + "/scripts/" + script.name().toLowerCase() + ".sql").exists()) {
                this.saveResource("scripts/" + script.name().toLowerCase() + ".sql", false);
            }
        }
    }

    protected void postEnable() {

        if (this.zInventoryManager != null) this.zInventoryManager.sendLog();

        if (this.commandManager != null) this.commandManager.validCommands();

        this.log.log("Done enabling (" + Math.abs(enableTime - System.currentTimeMillis()) + "ms)");

    }

    protected void preDisable() {
        this.enableTime = System.currentTimeMillis();
        this.log.log("Starting disabling");
    }

    protected void postDisable() {
        this.log.log("Done disabling (" + Math.abs(enableTime - System.currentTimeMillis()) + "ms)");

    }

    /**
     * Build gson
     *
     * @return
     */
    public GsonBuilder getGsonBuilder() {
        return new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().serializeNulls().excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE).registerTypeAdapter(PotionEffect.class, new PotionEffectAdapter(this)).registerTypeAdapter(PlayerVote.class, new PlayerAdapter(this)).registerTypeAdapter(Vote.class, new VoteAdapter(this)).registerTypeAdapter(Reward.class, new RewardAdapter(this)).registerTypeAdapter(Location.class, new LocationAdapter(this));
    }

    /**
     * Add a listener
     *
     * @param listener
     */
    public void addListener(Listener listener) {
        if (listener instanceof Saveable) this.addSave((Saveable) listener);
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    /**
     * Add a listener from ListenerAdapter
     *
     * @param adapter
     */
    public void addListener(ListenerAdapter adapter) {
        if (adapter == null) throw new ListenerNullException("Warning, your listener is null");
        if (adapter instanceof Saveable) this.addSave((Saveable) adapter);
        this.listenerAdapters.add(adapter);
    }

    /**
     * Add a Saveable
     *
     * @param saver
     */
    public void addSave(Saveable saver) {
        this.savers.add(saver);
    }

    /**
     * Get logger
     *
     * @return loggers
     */
    public Logger getLog() {
        return this.log;
    }

    /**
     * Get gson
     *
     * @return {@link Gson}
     */
    public Gson getGson() {
        return gson;
    }

    public Persist getPersist() {
        return persist;
    }

    /**
     * Get all saveables
     *
     * @return savers
     */
    public List<Saveable> getSavers() {
        return savers;
    }

    /**
     * @param classz
     * @return
     */
    public <T> T getProvider(Class<T> classz) {
        RegisteredServiceProvider<T> provider = getServer().getServicesManager().getRegistration(classz);
        if (provider == null) {
            log.log("Unable to retrieve the provider " + classz, LogType.WARNING);
            return null;
        }
        return provider.getProvider() != null ? provider.getProvider() : null;
    }

    /**
     * @return listenerAdapters
     */
    public List<ListenerAdapter> getListenerAdapters() {
        return listenerAdapters;
    }

    /**
     * @return the commandManager
     */
    public CommandManager getCommandManager() {
        return commandManager;
    }

    /**
     * @return the inventoryManager
     */
    public ZInventoryManager getZInventoryManager() {
        return zInventoryManager;
    }

    /**
     * Check if plugin is enable
     *
     * @param pluginName
     * @return
     */
    protected boolean isEnable(Plugins pl) {
        Plugin plugin = getPlugin(pl);
        return plugin != null && plugin.isEnabled();
    }

    /**
     * Get plugin for plugins enum
     *
     * @param pluginName
     * @return
     */
    protected Plugin getPlugin(Plugins plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin.getName());
    }

    /**
     * Register command
     *
     * @param command
     * @param vCommand
     * @param aliases
     */
    protected void registerCommand(String command, VCommand vCommand, String... aliases) {
        this.commandManager.registerCommand(command, vCommand, aliases);
    }

    /**
     * Register Inventory
     *
     * @param inventory
     * @param vInventory
     */
    protected void registerInventory(EnumInventory inventory, VInventory vInventory) {
        this.zInventoryManager.registerInventory(inventory, vInventory);
    }

    public List<String> getFiles() {
        return files;
    }

    /**
     * @param resourcePath
     * @param toPath
     * @param replace
     */
    protected void saveResource(String resourcePath, String toPath, boolean replace) {
        if (resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = this.getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + this.getFile());
            } else {
                File outFile = new File(getDataFolder(), toPath);
                int lastIndex = toPath.lastIndexOf(47);
                File outDir = new File(getDataFolder(), toPath.substring(0, lastIndex >= 0 ? lastIndex : 0));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (outFile.exists() && !replace) {
                        getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                    } else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while ((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException var10) {
                    getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
                }

            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }

    protected void registerFile(InventoryName file) {
        this.files.add(file.getName());
    }
}
