
<p align="center">
  <img alt="Logo" src="./docs/images/logo.png?raw=true">
</p>


## Description and Features

Adds various dinnerware to the game (just plates for now).   
Place, display and eat.   
Maybe wash too.  

Plates can be made out of: 
- Can be made of:
  - vanilla wood types;
  - iron, gold, diamond and quartz;
  - terracotta of all colors (both by dyeing plain one and crafting directly out of colored blocks);
  - a few other materials ;)

<p align="center">
  <img alt="All the plates" width="750" src="./docs/images/all-the-plates-0.7.4.png?raw=true">
</p>

Plates act similar to carpets in regard to placing - can be placed on any block, but break without support.  
Can be oriented on four cardinal directions and are waterloggable.  
GUI can be opened by right-clicking on empty one or by shift-right-clicking on any. Each plate has three slots: **Main dish**, **Side dish** and **Extra dish**.   
Each slot can hold up to 16 items (can be changed in the config), but it doesn't allow overstacking if certain item's max stack is lower.  

The placed food is rendered on the plate depending on what slots are filled (4 configurations total).

<p align="center">
  <img alt="Plate GUI" width="750" src="./docs/images/plate-gui.png?raw=true">
  <img alt="Display options" width="750" src="./docs/images/slots-and-facing.png?raw=true">
</p>

Right-click the plate to eat the food off of it. The food is eaten in queue fashion: all the food in the Main dish slot, then in Side dish slot, then in Extra dish slot, whichever is first and whichever is not empty.  
Any effects it has will be applied to the player, good OR bad. In theory any custom logic from the mods should also work, provided it's encapsulated in the `finishUsingItem()` method.  
By default, the player can only eat until they're full, but over-eating can be enabled in the config.  

<p align="center">
  <img alt="Food Effects" width="750" src="./docs/images/food-effects.png?raw=true">
</p>

By default, only edible items can be places on the plates. This too can be disabled in the config, allowing to place anything on any plate.  
See [Customization](#customization) for details.  

<p align="center">
  <img alt="Gems on the Plate" width="750" src="./docs/images/plate-of-gems.png?raw=true">
</p>

Lastly, if enabled in the config, plates can become fragile. Don't walk on them.  

<details><summary>Shelved plans</summary>  

Shelved for now as it requires a big rework of how plate blocks are registered as rendered. I hope to return to this some time later.  

- ~~Tray items made of vanilla wood types as well as iron and gold;~~
  - ~~Custom animation _(or rather, lack thereof)_ when holding it;~~ 
  - ~~Act like bundles for plates and placeable food;~~
  - ~~Placed plates and food can be right-clicked to be picked up;~~
  - ~~If not empty, suitable blocks can be right-clicked to place plates with food or placeable food;~~
  - ~~Same can be done straight from the inventory, just like with a bundle;~~ 
  - ~~Empty plates can be stacked, but only one plate with food/placeable food is allowed and only on top _(you won't put another plate onto your food, will you?)_;~~
  - ~~Maximum stack height can be configured in the config;~~

</details>

### Recipes

All plates are crafted with the "bowl" recipe out of plate material.  
"Item-material" (ingots/gems/quartz/etc.) produce 1 plate while "Block-materials" produce 6 plates.  
Additionally, colored plates (like terracotta ones) can be crafted by coloring the base item.  

<table>
<tr>

<td>

![Item Material Craft](./docs/images/info/crafts-item.png?raw=true)</td>

<td>

![Block Material Craft](./docs/images/info/crafts-block.png?raw=true)</td>

<td>

![Colored Craft](./docs/images/info/crafts-block-color.png?raw=true)</td>

</tr>
</table>  

## Images

![Dining Room](./docs/images/dining-room.png?raw=true)  

## Resourcepack support

Blocks and items reference vanilla textures so whatever resourcepack you install will be applied to the plates as well.  

<details><summary>Barebones</summary>  

![Barebones](./docs/images/textures/textures-barebones.png?raw=true)

</details>

<details><summary>Ashen 16x</summary>  

![Ashen 16x](./docs/images/textures/textures-ashen.png?raw=true)

</details>

<details><summary>Default HD 128x</summary>  

![Default HD 128x](./docs/images/textures/textures-default-hd-128x.png?raw=true)

</details>


## Customization

By default, only edible items are allowed on plates. This can be disabled to allow any item/block on the plate.  
If you want to add a few items that are edible, but not compatible by default, they should be added to the `dinnerware:additional_food` tag provided by the mod.  
Note that adding items to this tag does not guarantee they'll be able to be eaten as that still relies on FoodProperties to be present in item.

## Loaders / Versions

Forge 47.4.16+ for Minecraft 1.20.1 for now.  

When the mod is feature-complete I will be looking into forward porting it to 1.21.X...dunno which one of the gorillion sub versions, probably .11  
As well as back-porting to Forge 1.18.2. Yes, really.  

Fabric port is uncertain, but not out of the picture. We'll see.

## Progress

<details><summary>Click to expand</summary>

- [X] Plate blocks:
  - [X] Different materials:
    - [X] vanilla wood types;
    - [X] iron, gold, diamond and quartz;
    - [X] terracotta of all colors;
    - [X] a few other materials;
  - [X] Can be placed on any block, but break without support;
  - [X] Has 3 slots: Main dish, Side dish and Extra dish;
  - [X] GUI can be opened by right-clicking on empty one or by shift-right-clicking on any;
  - [X] Food (or if configured, anything) can be placed on them and will be rendered on the plate:
    - [X] 4 possible configurations depending on which slots have items;
    - [X] Food can be eaten by right-click;
      - [X] Even if all items will be allowed, only food will be eaten;
      - [X] Food effects are applied, good and bad;
      - [X] Over-eating can be enabled, allowing to continue eating even with full hunger;
  - [X] Can be fragile if enabled in the config. Don't walk on them.

<details><summary>Shelved plans</summary>  

- [ ] ~~When crouch-broken, keep their content inside can be placed back.~~
  - [ ] ~~Keep content~~
  - [ ] ~~Restore when placed back~~
  - [X] Show in tooltip
  - [ ] ~~Render food in item~~


- [ ] ~~Tray items:~~ Shelved for now...
  - [ ] ~~Can be made of vanilla wood types as well as iron and gold~~
  - [ ] ~~Custom animation when holding it;~~
  - [X] ~~Act like bundles for plates and placeable food;~~
  - [ ] ~~Placed plates and food can be right-clicked to be picked up;~~
  - [ ] ~~If not empty, suitable blocks can be right-clicked to place plates with food or placeable food;~~
  - [X] ~~Same can be done straight from the inventory, just like with a bundle;~~
  - [ ] ~~Empty plates can be stacked, but only one plate with food/placeable food is allowed and only on top;~~
  - [ ] ~~Maximum stack height can be configured in the config;~~

</details>

</details>

## Credits

* v972 - Initial idea, coding;
* Queez_ - Motivation, assets and creative input;
* Kaupenjoe - Great free modding tutorial series;
* diesieben07 - That one method for shift-right-clicking an item in the GUI;
