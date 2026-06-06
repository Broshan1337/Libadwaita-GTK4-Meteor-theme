<h3 align="center">
  Adwaita for <a href="https://meteorclient.com">Meteor Client</a>
</h3>

<p align="center">
  <i>Brings the clean, modern look of GNOME's libadwaita design language to Meteor Client.</i>
</p>


Adwaita is a theme addon for [Meteor Client](https://meteorclient.com) that reshapes the in-game GUI
to *feel* like it was designed with GNOME's [libadwaita](https://developer.gnome.org/hig/) principles:
clean, neutral, and modern, with purposeful use of color and space. Think GNOME 42+ apps like
Nautilus, Files and Settings — rounded windows, soft shadows, subtle 1px borders, a single
purposeful accent, and calm neutral grays.

## 🖼️ Previews

<details>
<summary>☀️ Adwaita Light</summary>
<br/>
<img width="1248" height="1136" alt="image" src="https://github.com/user-attachments/assets/b2cb8b98-cb2d-414f-bf79-d18503e0ad4f" />
</details>

<details>
<summary>🌙 Adwaita Dark</summary>
<br/>
<img width="1248" height="1136" alt="image" src="https://github.com/user-attachments/assets/bd270ccf-bddd-4f06-be3d-75f1017546a0" />
</details>

> Font used in previews: [Cantarell](https://gitlab.gnome.org/GNOME/cantarell-fonts) (GNOME's default).

## 🧰 Installation

> Requires [Meteor Client](https://meteorclient.com).

1. 🡒 Go to the [**Releases tab**](../../releases) and download the latest `.jar` file.
2. 🡒 Move the file into your `.minecraft/mods` folder.
3. 🡒 Launch Minecraft.
4. 🡒 Open ClickGUI (`Right Shift`), go to the **"GUI"** tab.
5. 🡒 Select **"Adwaita"** from the **"Theme"** dropdown.
6. 🡒 Pick the **Light** or **Dark** variant under the theme's **Colors** group.
7. 🡒 **Recommended:** In the **"Config"** tab, set your custom font to **Cantarell**
   (falls back to **Arial**, or any font that supports bold/italic styles) for the full experience.

## ✨ Features

- 🎨 **Two libadwaita variants** — *Adwaita Light* and *Adwaita Dark*, using GNOME's named colors
  (accent `#3584E4`, neutral surfaces, secondary text, destructive/success/warning states).

- 🟦 **Custom accent color** — keep the canonical GNOME blue or pick any accent you like.

- 🪟 **libadwaita geometry** — layered corner radii (12px windows, 8px cards, 6px buttons),
  subtle 1px borders at ~40% opacity, and a soft drop shadow with adjustable intensity.

- 🌀 **Smooth animations** — GTK-style **ease-out** easing (`cubic-bezier(0, 0, 0.2, 1)`) by default,
  with configurable duration.

- 🔤 **Crisp font rendering** — supports bold, italic, and dynamic font-style switching. No forced
  ALL-CAPS labels, just like libadwaita.

- 🧲 **Snap-to-grid** for the Modules screen, plus an Adwaita-styled **search** experience.

## ⚙️ Settings

Configurable from the theme's tab in Meteor's GUI:

| Setting | Description |
|---|---|
| **Variant** | `Light` / `Dark` |
| **Accent color** | Color picker (defaults to `#3584E4`) |
| **Window radius** | `0–20`, default `12` |
| **Card radius** | `0–12`, default `8` |
| **Button radius** | `0–12`, default `6` |
| **Animation easing** | Easing curve (default ease-out) |
| **Animation duration** | Milliseconds (default `200`) |
| **Show borders** | Toggle subtle 1px surface borders (default on) |
| **Shadow intensity** | `0–100`, default `60` |

> The font is selected via Meteor's own **Config → Custom Font** picker (set it to **Cantarell**).

## 🛠️ For Developers

Want your addon's categories to match the Adwaita theme? Register custom category icons:

```java
// Check the javadocs for usage
AdwaitaIcons.registerCategoryIcon(categoryName, texture);
```

### Gradle Setup

```kotlin
repositories {
    maven {
      name = "jitpack"
      url = uri("https://jitpack.io")
    }
}
```

```kotlin
dependencies {
    // Use the ':api' classifier to not include the whole theme in your addon
    // Note: Make sure '${mc_version}' matches your target Minecraft version (e.g. "1.21.1"),
    //       also replace '${adwaita_version}' with the latest version (e.g. "1.0.0")
    include(modImplementation("com.github.X-C-0.adwaita-addon:${mc_version}:${adwaita_version}:api"))

    // Optional: Add the full theme to your runtime environment
    modLocalRuntime("com.github.X-C-0.adwaita-addon:${mc_version}:${adwaita_version}")
}
```

## 🙏 Credits

This addon is a rebrand and retheme of the excellent **Catppuccin Addon for Meteor Client**.
Huge thanks to the original developer **[Pindour](https://github.com/X-C-0)** — the rounded-corner
shader system, smooth animation framework, rich-text font rendering, search screen and the entire
theming architecture are all their work. Adwaita simply re-skins that foundation in GNOME's
libadwaita design language. Please go star the [original project](https://github.com/X-C-0/catppuccin-addon)
and support Pindour. 💙

## 🔗 Links

- GNOME Human Interface Guidelines: https://developer.gnome.org/hig/
- Meteor Client: https://meteorclient.com
