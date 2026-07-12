package com.example.streamdeck.service;

import com.example.streamdeck.action.app.OpenAppAction;
import com.example.streamdeck.action.audio.SelectAppVolumeAction;
import com.example.streamdeck.action.audio.SoundAction;
import com.example.streamdeck.action.spotify.SpotifyControlAction;
import com.example.streamdeck.model.*;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.FileReader;

public class MenuPersistence {

    private static final String FILE = "menu.json";
    private static final Gson gson = new Gson();

    // ===== SAVE =====
    public static void save(Menu rootMenu) {

        try (FileWriter writer = new FileWriter(FILE)) {

            SerializableMenu serializable = convertMenu(rootMenu);
            gson.toJson(serializable, writer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ===== LOAD =====
    public static Menu load() {

        try (FileReader reader = new FileReader(FILE)) {

            SerializableMenu data =
                    gson.fromJson(reader, SerializableMenu.class);

            return convertToMenu(data);

        } catch (Exception e) {
            return new Menu(); // falls Datei nicht existiert
        }
    }

    // ==============================
    // ===== CONVERTER METHODS =====
    // ==============================

    private static SerializableMenu convertMenu(Menu menu) {

        SerializableMenu sm = new SerializableMenu();

        for (int i = 0; i < 12; i++) {

            DeckItem item = menu.getItem(i);
            if (item == null) continue;

            SerializableItem si = new SerializableItem();

            if (item instanceof Folder folder) {
                si.type = "folder";
                si.label = folder.getLabel();
                si.submenu = convertMenu(folder.getSubmenu());
            }

            else if (item instanceof SoundAction sound) {
                si.type = "sound";
                si.filePath = sound.getFilePath();
                si.iconPath = sound.getIconPathRaw();
            }


            else if (item instanceof SelectAppVolumeAction volume) {
                si.type = "volume";
                si.processName = volume.getProcessName();
                si.iconPath = volume.getIconPathRaw();
            }
            else if (item instanceof OpenAppAction app) {
                si.type = "openapp";
                si.exePath = app.getExePath();
                si.iconPath = app.getIconPathRaw();
            }

            else if (item instanceof SpotifyControlAction spotify) {
                si.type = "spotify";
                si.spotifyCommand = spotify.getCommand().name();
                si.iconPath = spotify.getIconPath();
            }

            sm.items[i] = si;
        }

        return sm;
    }


    private static Menu convertToMenu(SerializableMenu sm) {

        Menu menu = new Menu();
        if (sm == null) return menu;

        for (int i = 0; i < 12; i++) {

            SerializableItem si = sm.items[i];
            if (si == null) continue;

            if ("folder".equals(si.type)) {

                Folder folder = new Folder(si.label);
                Menu sub = convertToMenu(si.submenu);

                for (int j = 0; j < 12; j++) {
                    folder.getSubmenu().setItem(j, sub.getItem(j));
                }

                menu.setItem(i, folder);
            }

            else if ("sound".equals(si.type)) {

                SoundAction sound =
                        new SoundAction(si.filePath, si.iconPath);

                menu.setItem(i, sound);
            }


            else if ("volume".equals(si.type)) {

                SelectAppVolumeAction volume =
                        new SelectAppVolumeAction(si.processName, si.iconPath);

                menu.setItem(i, volume);
            }
            else if ("openapp".equals(si.type)) {  // ✅ neu
                OpenAppAction app = new OpenAppAction(si.exePath, si.iconPath);
                menu.setItem(i, app);
            }

            else if ("spotify".equals(si.type)) {
                SpotifyControlAction.Command cmd =
                        SpotifyControlAction.Command.valueOf(si.spotifyCommand);
                menu.setItem(i, new SpotifyControlAction(cmd, si.iconPath));
            }


        }

        return menu;
    }

    public static boolean exists() {
        return new java.io.File(FILE).exists();
    }

}