# StreamDeck – JavaFX Kontrollpanel

Eine konfigurierbares **Kontrollpanel-Software** für Windows, geschrieben in **JavaFX**. Eigenes StreamDeck mit anpassbaren Buttons für Apps, Sounds, Lautstärke und Spotify-Integration.



## Features

&#x20;**Button-Grid Interface**

* 4x3 Grid von konfigurierbaren Buttons
* Custom Icons pro Button
* Verschachtelte Ordner-Navigation

&#x20;**Action Types**

* **App Launcher** – Startet beliebige Windows-Programme
* **Audio Control** – Steuert Lautstärke + App-Lautstärke
* **Soundboard** – Spielt Sound-Dateien ab
* **Spotify Integration** – Play/Pause, Next, Previous
* **Smart Toggle** – Öffnet oder schließt Apps intelligent

&#x20;**Persistierung**

* Automatisches Speichern in `menu.json`
* Schnelles Laden beim Start
* Vollständige Konfiguration serialisierbar

&#x20;**Anpassbar**

* Custom Themes \& Styling (CSS)
* Icon-Extraktion aus .exe Dateien

\---

## Projekt-Struktur (MVC Pattern)

Das Projekt folgt einer **klaren MVC-Architektur**

```
src/main/java/com/example/streamdeck/
├── HelloApplication.java          # Main Entry Point
├── HelloController.java           # FXML Controller
├── Launcher.java                  # Application Launcher
│
├── model/                         # Datenmodelle
│   ├── Menu.java                 # Menü-Struktur
│   ├── DeckButton.java           # Button-Daten
│   ├── Folder.java               # Ordner (Navigation)
│   └── ...
│
├── ui/                            # User Interface
│   ├── component/
│   │   ├── StreamButton.java     # Custom Button Component
│   │   └── Theme.java            # Styling/CSS
│   └── dialog/
│       ├── ActionConfigDialog.java
│       ├── ActionSelectionDialog.java
│       ├── AppSelectionDialog.java
│       └── ...
│
├── action/                        # Business Logic (Actions)
│   ├── ButtonAction.java         # Interface
│   ├── app/
│   │   ├── AppLaunchAction.java
│   │   └── SmartOpenAppAction.java
│   ├── audio/
│   │   ├── VolumeAction.java
│   │   └── SoundAction.java
│   ├── spotify/
│   │   └── SpotifyControlAction.java
│   └── ui/
│       └── BackAction.java
│
├── service/                       # Services \\\& Manager
│   ├── MenuManager.java          # Menü-Navigation
│   ├── MenuPersistence.java      # Speichern/Laden
│   ├── AudioManager.java         # Sound-Wiedergabe
│   └── ExeIconExtractor.java     # Icon-Extraktion
│
└── util/                          # Utilities (Windows-spezifisch)
    ├── ProcessUtils.java
    ├── WindowsProcessUtils.java
    └── WindowsWindowUtil.java
```

## Schnellstart

### Requirements

* **Java 21+** (JDK)
* **Maven 3.8+**
* **Windows 10/11**

## Verwendung

### Buttons erstellen

1. **Klick auf leeren Button** → "Button konfigurieren" Dialog öffnet sich
2. Wähle **"Aktion direkt binden"** → Action-Auswahl Dialog
3. Wähle Action-Typ:

   * **"App öffnen"** → Windows App starten
   * **"Soundboard Sound"** → .wav/.mp3 abspielen
   * **"Spotify Steuerung"** → Spotify steuern
   * etc.

### Ordner erstellen (Navigation)

1. Klick auf Button → "Ordner erstellen"
2. Ordner wird als Submenu erstellt
3. Klick auf Ordner-Button → navigiert in das Submenu
4. **"Zurück"-Button** um vorheriges Menü zu laden

### Konfiguration

Die gesamte Konfiguration wird in `menu.json` gespeichert:

```json
{
  "folders": \\\[...],
  "buttons": \\\[
    {
      "name": "Spotify",
      "action": "spotify",
      "iconPath": "path/to/icon.png"
    }
  ]
}
```

\---

## Code-Highlights

### Button Action erstellen (Custom)

```java
// 1. Extend ButtonAction Interface
public class MyCustomAction implements ButtonAction {
    
    @Override
    public void execute() {
        // Deine Logik hier
        System.out.println("Action executed!");
    }
    
    @Override
    public String getDescription() {
        return "My Custom Action";
    }
}

// 2. In ActionSelectionDialog registrieren
// 3. Neuer Button-Typ verfügbar!
```

### MenuManager verwenden

```java
// Aktuelle Menü-State abrufen
Menu currentMenu = menuManager.getCurrentMenu();

// UI refreshen
menuManager.refreshUI();

// Zu Ordner navigieren
menuManager.navigateToFolder(folder);
```

### Neue Action-Typ hinzufügen

1. Neue Klasse in `action/` erstellen
2. `ButtonAction` Interface implementieren
3. In `ActionSelectionDialog` Dialog-Button hinzufügen
4. Fertig! 🎉

\---

## Windows-Integration

Das Projekt nutzt **Windows APIs** über JNA für:

* **ProcessUtils** – Programme starten \& verwalten
* **WindowsWindowUtil** – Fenster fokussieren, Titel auslesen
* **ExeIconExtractor** – Icons aus .exe extrahieren

Alle Windows-spezifischen Klassen sind in `util/` isoliert.

\---

## Persistierung

Alle Konfigurationen werden als **JSON** in `menu.json` gespeichert:

```bash
# Zu finden in: {user.home}/.streamdeck/menu.json
```

**MenuPersistence** kümmert sich um:

* Laden beim Start
* Speichern bei Änderungen
* Serialisierung / Deserialisierung

\---

## Roadmap

* \[ ] Cross-Platform Support (macOS, Linux)
* \[ ] Plugin System für Custom Actions
* \[ ] Profile/Presets speichern
* \[ ] Hotkey Support
* \[ ] Streaming Integration (OBS, Twitch)

\---

## Troubleshooting

### "App nicht gefunden"

* Stelle sicher, dass der .exe Pfad korrekt ist
* Prüfe in `AppSelectionDialog` ob die App sichtbar ist

### "Sound wird nicht abgespielt"

* Nur `.wav` und `.mp3` werden unterstützt
* AudioManager checken: `service/AudioManager.java`

### "Icons laden nicht"

* ExeIconExtractor braucht valid .exe Dateien
* Fehlerlog in Console checken

\---

## Dokumentation

Siehe **`docs/StreamDeck\\\_Code\\\_Map.pdf`** für detaillierte UI → Code Mapping.

\---

## Lizenz

Dieses Projekt ist Open Source. Frei verwendbar für persönliche Projekte.

\---

## Autor

**Leandro** – Speedcuber \& Developer  
GitHub: [@LeandroBigga](https://github.com/LeandroBigga)

\---

## Kontribution

Pull Requests willkommen!

1. Fork das Repo
2. Feature Branch erstellen (`git checkout -b feature/MyFeature`)
3. Committen (`git commit -m 'Add MyFeature'`)
4. Pushen (`git push origin feature/MyFeature`)
5. Pull Request öffnen

\---

## Changelog

### v1.0 (Current)

* ✅ Basis Button-Grid UI
* ✅ App Launcher
* ✅ Audio Control
* ✅ Spotify Integration
* ✅ MVC Refactor abgeschlossen



