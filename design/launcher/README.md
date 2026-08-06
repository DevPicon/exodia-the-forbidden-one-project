# Launcher icon source

The launcher identity is based on three overlapping canvas layers: a coral arc, a cyan stroke, a mint circle, and a violet intersection. The selected direction came from an exploratory generated concept, but the shipped artwork was reconstructed as deterministic project-owned vectors; no generated bitmap is packaged in the app.

## Source files

- `app-icon-source.svg`: legacy square icon source.
- `app-icon-round-source.svg`: legacy round icon source.
- `app-icon-monochrome-source.svg`: themed-icon design preview.
- `app/src/main/res/drawable/ic_launcher_foreground.xml`: adaptive color foreground.
- `app/src/main/res/drawable/ic_launcher_monochrome.xml`: Android 13 themed foreground.

## Palette

| Role | Color |
|---|---|
| Background and primitive cutouts | `#071A4A` |
| Arc layer | `#FF6B5E` |
| Stroke layer | `#22D3EE` |
| Circle layer | `#5EE6B8` |
| Layer intersection | `#9B5CFF` |

## Legacy outputs

Render the square or round SVG at the Android launcher size for each density, then encode it losslessly with `cwebp`.

| Density | Size |
|---|---:|
| mdpi | 48 px |
| hdpi | 72 px |
| xhdpi | 96 px |
| xxhdpi | 144 px |
| xxxhdpi | 192 px |

Example:

```shell
rsvg-convert --width 192 --height 192 --output icon.png design/launcher/app-icon-source.svg
cwebp -lossless icon.png -o app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp
```
