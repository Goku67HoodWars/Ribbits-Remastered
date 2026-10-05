# Ribbits Remastered 26.3 — 1.0.0

## 🐸 Minecraft 26.3 — Fabric, Forge & NeoForge

- 🌿 Multi-loader release for **Minecraft 26.3** on **Fabric, Forge, and NeoForge**, built from one
  Architectury codebase.
- 🧩 Still **standalone and dependency-free** (no YUNG's API): swamp villages, ribbit professions
  (merchants, fishermen, sorcerers, gardeners, musicians), the band/music sessions, fishing,
  gardening, buffs, blocks, decorations, and spawn eggs all intact. Requires only **GeckoLib** (plus
  **Fabric API** on the Fabric build).

## 🔧 What the 26.2 → 26.3 port touched

- 🗺️ **Worldgen Feature system rewrite** — 26.3 made `Feature` codec-dispatched and removed
  `ConfiguredFeature`/`FeatureConfiguration`; the swamp-vegetation feature was rebuilt onto the new
  model and its data moved to the new `worldgen/feature` datapack format.
- 🧱 **Block API** — `BonemealableBlock` + `BonemealSource`, the removed per-block codec system, and
  `PushReaction` renames (swamp plants / giant lily pad).
- 🎨 **Rendering** — `PoseStack.mulPose → rotate`, the model-submit signature change, and the
  first-person hand renderer rework.
- 🧾 **Datapack format** — block-state keys `Name`→`id`, state-provider type renames, advancement
  recipe-unlock `recipe`→`recipes`, and loot condition/function discriminators unified to `type`.

## 🧪 Tested

- ✅ Runtime-verified on **Fabric, Forge, and NeoForge** for Minecraft 26.3 — mod loads, ribbits
  spawn and animate, and swamp villages generate (`/locate structure ribbits:ribbit_village`).

## 💚 Credits

- 🌱 Original **Ribbits** mod, concept, art, assets, and design by the original team: **Joosh**,
  **yungnickyoung**, **HellionGames**, **Refresh Studios**, and the original contributors.
- 🐸 Original project: https://www.curseforge.com/minecraft/mc-mods/ribbits

An unofficial community continuation to keep Ribbits playable on newer Minecraft versions, preserving
the spirit of the original. Code under LGPL-3.0; assets remain © Refresh Studios & Bonsai Studios.
