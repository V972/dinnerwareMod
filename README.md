# Dinnerware Mod
### for Forge 1.20.1

Adds various dinnerware to the game.  
Place, display and eat.  
Maybe wash too.  

## Features

- Plate blocks made of vanilla wood types as well as iron and gold;
  - Can be placed on any block, but break w/o support;
  - Has 3 slots: Main dish, Side dish and Extra dish; 
  - GUI can be opened by right-clicking on empty one or by shift-right-clicking on any;
  - Food (or if configured, anything) can be placed on them and will be rendered on the plate:
    - 4 possible configurations depending on which slots have items;
    - Food can be eaten by right-click;
      - Even if all items will be allowed, only food will be eaten;
      - Food effects are applied, good and bad;
      - Over-eating can be enabled, allowing to continue eating even with full hunger;
  - Additional food items can be added to the `dinnerware:additional_food`;
  - Can be fragile if enabled in the config. Don't walk on them.
- Tray items made of vanilla wood types as well as iron and gold;
  - Custom animation _(or rather, lack thereof)_ when holding it; 
  - Act like bundles for plates and placeable food;
  - Placed plates and food can be right-clicked to be picked up;
  - If not empty, suitable blocks can be right-clicked to place plates w/ food or placeable food;
  - Same can be done straight from the inventory, just like w/ a bundle; 
  - Empty plates can be stacked, but only one plate w/ food/placeable food is allowed and only on top _(you won't put another plate onto your food, will you?)_;
  - Maximum stack height can be configured in the config;

### Recipes

Pictures WIP

* bowl recipe out of quartz for plate;
* iron ingots and iron pressure plate for tray

## Customization

By default, only edible items are allowed on plates. This can be disabled to allow any item/block on the plate.  
If you want to add a few items that are edible, but not compatible by default, they should be added to the `dinnerware:additional_food` tag provided by the mod.  
Note that adding items to this tag does not guarantee they'll be able to be eaten as that still relies on FoodProperties to be present in item.

## Loaders / Versions

Forge 1.20.1 for now.  
When the mod is feature-complete I will be looking into forward porting it to 1.21.X...dunno which one of the gorillion sub versions, probably .11  
As well as back-poring to Forge 1.18.2. Yes, really.  
Fabric port is uncertain, but not of the picture. We'll see.

## Progress

- [ ] Plate blocks:
  - [ ] Can be made out of vanilla wood types, quartz as well as iron and gold;  
  - [X] Can be placed on any block, but break w/o support;
  - [X] Has 3 slots: Main dish, Side dish and Extra dish;
  - [X] GUI can be opened by right-clicking on empty one or by shift-right-clicking on any;
  - [X] Food (or if configured, anything) can be placed on them and will be rendered on the plate:
      - [X] 4 possible configurations depending on which slots have items;
      - [X] Food can be eaten by right-click;
          - [X] Even if all items will be allowed, only food will be eaten;
          - [X] Food effects are applied, good and bad;
          - [X] Over-eating can be enabled, allowing to continue eating even with full hunger;
    - [ ] Can be fragile if enabled in the config. Don't walk on them.
- [ ] Tray items:
  - [ ] Can be made of vanilla wood types as well as iron and gold
  - [ ] Custom animation when holding it;
  - [X] Act like bundles for plates and placeable food;
  - [ ] Placed plates and food can be right-clicked to be picked up;
  - [ ] If not empty, suitable blocks can be right-clicked to place plates w/ food or placeable food;
  - [X] Same can be done straight from the inventory, just like w/ a bundle;
  - [ ] Empty plates can be stacked, but only one plate w/ food/placeable food is allowed and only on top _(you won't put another plate onto your food, will you?)_;
  - [ ] Maximum stack height can be configured in the config;

## Credits

* v972 - Initial idea, coding;
* Queez_ - Motivation, assets and creative input;
* Kaupenjoe - Great free modding tutorial series;
* diesieben07 - That one method for shift-right-clicking an item in the GUI;