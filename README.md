
# ZanyArtifacts

A PaperMC plugin for Minecraft **1.21.11+** that adds a collection of custom items, pets, and enchantments to your server.

> **Authors:** Scyye, Dark_Joe

## For issues, don't hesitate to reach out: [https://scyye.dev/discord](https://scyye.dev/discord)
---

## 📋 Table of Contents

- [Installation](`#-installation`)
- [Custom Items](#️-custom-items)
  - [Explosive Stick](`#explosive-stick`)
  - [Technoblade Sword](`#technoblade-sword`)
  - [Gun](`#gun`)
  - [Teleport Bow](`#teleport-bow`)
  - [Kamikaze Stick](`#kamikaze-stick`)
  - [Flash Bang](`#flash-bang`)
  - [Time Crystal](`#time-crystal`)
- [Pets](`#-pets`)
  - [Chicken Pet](`#chicken-pet`)
  - [Ocelot Pet](`#ocelot-pet`)
  - [Ender Chest Pet](`#ender-chest-pet`)
  - [Notch Pet](`#notch-pet`)
  - [Feeding Bag](`#feeding-bag`)
- [Custom Enchantments](`#-custom-enchantments`)
  - [Explosive Touch](`#explosive-touch`)
  - [Magnetic](`#magnetic`)
  - [Faster Falling](`#faster-falling`)
  - [Murder](`#murder`)
- [Commands](`#-commands`)
- [Permissions](`#-permissions`)
- [Recipe System](`#-recipe-system`)

---

## 📦 Installation

1. Download the latest `ZanyArtifacts.jar`.
2. Place it in your server's `plugins/` folder.
3. Restart (or start) your server.
4. The plugin will generate a `config.yml` on first run.

**Requirements:**
- PaperMC 1.21.11 or newer

---

## 🗡️ Custom Items

### Explosive Stick
**ID:** `explosive_stick`

A torch that packs a serious punch.

| Action | Effect |
|---|---|
| Left-click a block | Creates an explosion (size 6) at the block |
| Right-click a block | Creates an explosion and **consumes one use** of the item |

---

### Technoblade Sword
**ID:** `technoblade_sword`

*"Technoblade Never Dies."* A golden sword wielding the power of THE BLADE.

| Action | Effect |
|---|---|
| Left-click | Sets time to night and summons an invulnerable Zombified Piglin |
| Right-click | Removes all summoned piglins and sets time to day |

---

### Gun
**ID:** `gun`

A bow repurposed as a firearm with limited ammo.

| Action | Effect |
|---|---|
| Shoot (hold right-click + release) | Fires an arrow; consumes 1 ammo |
| Left-click | Reloads to full (6 ammo) |

- Maximum ammo: **6**
- Current ammo is displayed in the item lore
- If you try to shoot with 0 ammo, the arrow is returned and you are reminded to reload

---

### Teleport Bow
**ID:** `teleport_bow`

Shoot an arrow and teleport to wherever it lands.

| Action | Effect |
|---|---|
| Shoot an arrow | Teleports you to the arrow's landing location |

---

### Kamikaze Stick
**ID:** `kamikaze_stick`

The ultimate sacrifice.

| Action | Effect |
|---|---|
| Left-click a block | Creates a **massive** explosion (power 200) at your location and kills you |

> ⚠️ **Warning:** This will destroy everything around you and kill your character instantly.

---

### Flash Bang
**ID:** `flash_bang`
**Stack size:** 6

Blind everyone nearby in an instant.

| Action | Effect |
|---|---|
| Left-click | Applies **Blindness** to all players within 5 blocks for a short duration |

---

### Time Crystal
**ID:** `time_crystal`

A mysterious crystal that manipulates time itself.

| Action | Effect |
|---|---|
| Click (any) | Rewinds time by **30 seconds** — restores your location, inventory, and reverses block changes |

- The item lore shows the number of saved time states.
- **Cooldown:** 10 minutes
- Requires at least 30 seconds of play history to use.

---

## 🐾 Pets

Pets are items that go in your **inventory**. Their passive effects activate automatically when carried. Some pets also have left-click abilities.

> Pets are displayed as player heads and are identified by the `🐾 Pet Ability` tag in their lore.

---

### Chicken Pet
**ID:** `chicken`

| Effect | Description |
|---|---|
| Passive (every 2 min) | Gives you **1 Egg** |
| On equip | Permanently boosts your **movement speed by 20%** |

---

### Ocelot Pet
**ID:** `ocelot`

| Effect | Description |
|---|---|
| Passive (constant) | **Scares away Creepers** within 6 blocks, making them flee |
| On equip | Grants permanent **Night Vision** |

---

### Ender Chest Pet
**ID:** `ender_chest`

| Action | Effect |
|---|---|
| Left-click | Opens your **Ender Chest** from anywhere |

---

### Notch Pet
**ID:** `notch`

*Negates all damage... at a hefty price.*

| Effect | Description |
|---|---|
| Passive (10s cooldown) | **Negates all incoming damage** and restores your health |

- The pet's lore tracks the total amount of damage negated.

---

### Feeding Bag
**ID:** `feeding_bag`

A portable bag with **27 slots** for storing items.

| Action | Effect |
|---|---|
| Left-click | Opens the Feeding Bag inventory |

- The contents of each Feeding Bag are saved **per-item** and persist across sessions.

---

## ✨ Custom Enchantments

These enchantments can be applied via commands or anvils.

### Explosive Touch
- **Max Level:** 7
- **Applies to:** Swords, Axes, Pickaxes, Hoes, Shovels
- **Effect:** Creates an explosion when you **break a block** or **hit an entity**. Explosion size scales with enchantment level (`2 × level`).

---

### Magnetic
- **Max Level:** 5
- **Applies to:** Boots
- **Effect:** Constantly **pulls nearby dropped items** toward you. Pull range = `level × 5` blocks.

---

### Faster Falling
- **Max Level:** 5
- **Applies to:** Boots
- **Effect:** Increases your **fall speed** while descending.

---

### Murder
- **Max Level:** 1
- **Applies to:** Armor
- **Effect:** When an attacker hits you, they are immediately blinded, levitated, launched with a firework, **killed**, and **kicked** from the server.

> ⚠️ This enchantment is classified as a **treasure** enchantment and cannot appear in the standard enchantment table.

---

## 💬 Commands

| Command | Description | Permission |
|---|---|---|
| `/zanygive <item_id>` | Gives you the specified item | `zany.give.command` |
| `/menu item_gui` | Opens the Item GUI (browse & take any item) | `zany.menu.command` |
| `/createrecipe` | Opens the recipe creator GUI | `zany.create.recipe.command` |

> `/zanygive` supports **tab-completion** — start typing an item ID and press Tab to see suggestions.

### Item IDs Reference

| Item | ID |
|---|---|
| Explosive Stick | `explosive_stick` |
| Technoblade Sword | `technoblade_sword` |
| Gun | `gun` |
| Teleport Bow | `teleport_bow` |
| Kamikaze Stick | `kamikaze_stick` |
| Flash Bang | `flash_bang` |
| Time Crystal | `time_crystal` |
| Chicken Pet | `chicken` |
| Ocelot Pet | `ocelot` |
| Ender Chest Pet | `ender_chest` |
| Feeding Bag | `feeding_bag` |
| Notch Pet | `notch` |

---

## 🔑 Permissions

| Permission | Description | Default |
|---|---|---|
| `zany.give.command` | Use `/zanygive` | OP |
| `zany.give.gui` | Use the Item GUI via command | OP |
| `zany.menu.command` | Use `/menu` | OP |
| `zany.create.recipe.command` | Use `/createrecipe` | OP |

---

## 📝 Recipe System

Admins can create and delete custom crafting recipes in-game using the `/createrecipe` command.

1. Run `/createrecipe` to open the **Crafting Menu** GUI.
2. Place items in the **3×3 crafting grid** (left section).
3. Place the **result item** in the center slot.
4. Click **Add Recipe** (green block) to register the recipe, or **Delete Recipe** (red block) to remove an existing one.

Recipes are saved to `config.yml` and persist across restarts.

---

*Plugin by Scyye & Dark_Joe*

---

