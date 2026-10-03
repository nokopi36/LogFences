# Log Fences

[日本語](README.ja.md)

A Minecraft mod that adds log-textured fences and fence gates for every vanilla wood type.
Strip the bark off each part separately with an axe to customize the look.

> **Status:** in development. Not yet released.

## Features

- **Log fences and fence gates** for 13 wood types: oak, spruce, birch, jungle, acacia, dark oak, mangrove, cherry, pale oak, poplar, bamboo block, crimson stem and warped stem.
  They behave like vanilla wooden fences and gates, and connect to them.
- **Per-part bark stripping.** Right-click with any axe to strip only the part you clicked:
  - Fence: post / upper rail / lower rail (8 combinations)
  - Fence gate (closed): end posts / inner posts / upper rails / lower rails (16 combinations)
  - Right-clicking an already stripped part, or an open gate, works as usual (leash / open-close).
- **Stripped variants.** Craft from stripped logs to get fences and gates that are fully stripped when placed.
  Breaking a fully stripped fence or gate drops the stripped variant.
- Same stats as vanilla wooden fences: hardness, sounds, flammability (nether woods do not burn) and furnace fuel.
- Uses the vanilla log textures, so it also works with resource packs.

## Recipes

| Result | Recipe |
|---|---|
| Log Fence ×12 | `L S L` / `L S L` — L = log, S = stick |
| Log Fence Gate ×4 | `S L S` / `S L S` |
| Bamboo Block Fence ×6 / Gate ×2 | Same shape with blocks of bamboo |
| Stripped variants | Same shape with stripped logs |

The counts match the vanilla plank recipes (1 log = 4 planks, 1 block of bamboo = 2 planks).

## Requirements

- Minecraft 26.3
- NeoForge 26.3.0.42-beta or later

## Building

```sh
./gradlew build
```

The jar is written to `build/libs/`. Gradle downloads Java 25 automatically through its toolchain support.

Other useful tasks:

- `./gradlew runClient` — start a development client
- `./gradlew runData` — regenerate models, language files, tags, loot tables and recipes into `src/generated/`
- `./gradlew runGameTestServer` — run the GameTests

## License

[MIT](LICENSE)
