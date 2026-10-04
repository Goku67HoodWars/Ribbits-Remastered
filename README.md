# Ribbits Remastered

A standalone, dependency-free port/remaster of the **Ribbits** mod for Minecraft **26.2**.

Ribbits fills the swamps with cozy little villages inhabited by *ribbits* — tiny
frog folk who live in the marsh, occasionally gather to play music (which you can
join), trade as merchants and fishermen, cast short buff spells (sorcerers), and
water crops (gardeners). They head home at night; right-click one with an amethyst
shard to set its home to its current spot.

This build runs entirely on its own — **no YUNG's API** or any other library required.
The structure/jigsaw system was moved onto vanilla worldgen and the registration layer
was reimplemented self-contained, so nothing external is needed.

## Loaders / versions

- Minecraft **26.2** — **Fabric, Forge, and NeoForge**.

## Credits

Original **Ribbits** team:

- **Josh / Joosh** — Visuals
- **yungnickyoung** — Programming
- **HellionGames** — Programming
- **scratchy_sd** — Sound Effects
- **wesleybenjamin** — Soundtrack
- **joakota** — Structures
- **joshesque** — Brand Design

Original project: <https://www.curseforge.com/minecraft/mc-mods/ribbits>

This remaster updates that work to 26.2 as a self-contained, multi-loader build —
removing the YUNG's API dependency, using cross-loader registration, and shipping a
dependency-free config screen.

## License

- **Code** is licensed under the **GNU LGPL v3** (see [`LICENSE`](LICENSE)).
- **Assets** (textures, sounds, models, and the Ribbit itself) remain
  **© 2025 Refresh Studios and Bonsai Studios, All Rights Reserved**, per the
  original project's license notice, which is preserved in [`LICENSE`](LICENSE).

## Building

Requires JDK 25.

```
./gradlew build
```

Builds all loaders; the distributable jars land in `fabric/build/libs/`,
`forge/build/libs/`, and `neoforge/build/libs/` (named
`RibbitsRemastered-26.2-<Loader>-<version>.jar`).
