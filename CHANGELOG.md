## 2.0.0!
2.0.0 is a total rewrite of the mod, both content-wise and datapacking-wise.

## Main Features
- Added a new Moobloom, the Lingholm Moobloom, a cyan flower themed after non-snowy Taiga biomes.
- Added a new cosmetic item, the Flower Crown. This is an item you may wear on your head and can craft from any combination of Moobloom flowers.
  - New Flower Crown Materials can be made through the `bovinesandbuttercups:flower_crown_material` datapack registry.
- Re-textured every flower and moobloom within the mod to give them a more modern appearance, as well as making them referential to non holstein cattle.
- Added a Chargelily Ranch to guide players that you may wish to strike a Moobloom with lightning.
  - Spawns within stony mountainous biomes.

## Bugfixes
- Fixed offspring particles having an incorrect origin point.
- Fixed data driven cow types always selecting the first type upon being struck by lightning when it should be randomised.

## Miscellaneous Changes
- Renamed Bowl of Nectar to Nectar Bowl for consistency with vanilla.
- Tweaked Ranch Loot Table to always include a Saddle, and to roll for seeds/crops twice.
- Swapped Buttercup and Pink Daisy Moobloom Ranch structures to have their respective Mooblooms instead of the opposite one.
  - This was done for slightly more cohesion with these structures.
  - You can still figure out that the breeding blocks are a thing, just look for the particles.
- Limelight Moobloom are now bred with Cave Vines (Glow Berry Vines) instead of Moss and Moss Carpets.
- Pink Daisy Moobloom now have Pink Petals and Cherry Wood as respective options for breeding.
- Nectar Bowls will now return bowls when used in crafting.
- Nectar Bowls no longer store the effects and the Moobloom it was obtained from, instead storing a nectar object containing the effects and model.
- Prefixed certain subtitle lang entries with mod id.

## Datapacking Changes
- Bovines and Buttercups now utilises datapack/dynamic registries.
  - A side effect of this means that you can no longer reload the registry through `/reload`.
  - This change has been made to better implement Bovines with vanilla systems.
- Configured Cow Types have been renamed to Cow Types, and now go inside the `data/<namespace>/bovinesandbuttercups/cow_type` directory.
- Nectar is now its own separate datapack registry, being found inside the `data/<namespace>/bovinesandbuttercups/nectar` directory.
- The Buttercup and Pink Daisy spawning biome tag is now `bovinesandbuttercups:has_moobloom/flower_forest`, instead of `bovinesandbuttercups:has_mooblooms`.
- Swapped Ranch Structure and spawning biome tags to use `c` tags where appropriate.
- `back_grass` field has been changed to `layers`. Layers are a more powerful system that allows you to overlay textures on top of Mooblooms.
  - Contains two fields. `texture_location` and `texture_modifications`.
  - Texture Modifications are a new registry that allows you to modify textures in certain ways.
    - By default it contains:
      - `bovinesandbuttercups:conditioned`, allows you to make a layer appear under a predicate.
      - `bovinesandbuttercups:emmissive`, allows you to make a texture emmissive.
      - `bovinesandbuttercups:grass_tint`, allows you to turn a texture's colour to the current biome's grass tint.
- Renamed `breeding_conditions` field to `offspring_conditions`.
- `offspring_conditions` has been rewritten from the ground up to utilise vanilla's predicate system.
  - [More information on this topic can be found here](https://minecraft.wiki/w/Predicate).
  - `offspring_conditions` may be a list of predicates that will apply to either parent; or you may specify `this_conditions` and `other_conditions` to specify conditions that must both be met by both parents with this applying to one and other applying to the other.
    - If no conditions are specified, it is automatically considered true.
  - This change has been made to better implement Bovines with vanilla systems.
- Removed `vanilla_spawning_hack` field. Regular Mooshroom spawn logic will now happen if there are no naturally spawning custom Mooshroom.
- Added `vanilla_type` field to Mooshroom Types, this will map a vanilla Mooshroom type to a Bovines and Buttercups Mooshroom type.