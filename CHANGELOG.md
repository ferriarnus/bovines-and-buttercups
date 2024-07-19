## 2.0.0!
2.0.0 is a total rewrite of the mod, both content-wise and datapacking-wise.

## Main Features
- Re-textured every flower and moobloom within the mod to give them a more modern appearance.

## Bugfixes
- Fixed offspring particles having an incorrect origin point.

## Miscellaneous Changes
- Limelight Moobloom are now bred with Cave Vines (Glow Berry Vines) instead of Moss and Moss Carpets.
- Pink Daisy Moobloom now have Pink Petals and Cherry Wood as respective options for breeding.
- Nectar Bowls will now return bowls when used in crafting.

## Datapacking Changes
- Bovines and Buttercups now utilises datapack/dynamic registries.
  - A side effect of this means that you can no longer reload the registry through `/reload`.
  - This change has been made to better implement Bovines with vanilla systems.
- Configured Cow Types have been renamed to Cow Types, and now go inside the `data/<namespace>/bovinesandbuttercups/cow_type` directory.
- `back_grass` has been changed to `layers`. Layers are a more powerful system that allows you to overlay textures on top of Mooblooms.
  - Contains two fields. `texture_location` and `texture_modifications`.
  - Texture Modifications are a new registry that allows you to modify textures in certian ways.
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