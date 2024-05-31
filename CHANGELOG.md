## 2.0.0!
2.0.0 is a total rewrite of the mod, both content-wise and datapacking-wise.

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
- Renamed `breeding_conditions` field to `offspring_conditions`.
- `offspring_conditions` has been rewritten from the ground up to utilise vanilla's predicate system.
  - [More information on this topic can be found here](https://minecraft.wiki/w/Predicate).
  - This change has been made to better implement Bovines with vanilla systems.