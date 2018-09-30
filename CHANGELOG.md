## Changelog

### 0.5.10
* Fixed not colored wrenches and wrong crowbar overlay color
* Rebalanced damage and durability values for tools
* Fixed too long burn time for large boiler
* Fixed worldgen replacing bedrock
* Fixed crafted tools having 0 durability
* Added emerald and diamond GT forms of gems
* Removed rubber tools except soft hammer
* Hoes now till coarse dirt too
* Removed ambiguous gem + hammer -> gem recipes
* Added 50% hammer tooltip

### 0.5.9
* Added Stones and Oils to Generation in Overworld/Nether (Rongm[a]rio)
* Added Chinese Translation (Timozer)
* Rename worldgen extract lock file to be twitch-import friendly (pyure)
* Fixed pipe networks not updating connections on world load
* Fixed potion effect from food being applied on both sides
* Fixed pumps not accepting power from cables
* Removed unnecessary concrete recipe
* Fixed hard hammer not dropping 1 extra crushed ore
* Fixed crafting recipes for gems that do not have plate variants
* Other bugfixes

### 0.5.8
* **Optimized cables and reworked pipes due to performance impact they had. Unfortunately these are not backwards compatible with previous ones, and it is better to remove all existing pipes and cables from the world to avoid possible world corruption**
* Fixed multiblock info tooltip rendering
* Fixes an issue where BuildCraft oil cannot be refined in a Distillery, among others *(codewarrior0)* 
* Removed glow effect from metatools
* Added turbine rotor efficiency description
* Added option to reset recipe progress when energy supply is insufficient (default is slowly going down like in vanilla)
* Added byproduct multiplier for higher tiers of the machines
* Fixed hoes taking too much damage on use
* Fixed memory leaks in packet system
* Fixed various issues with lighting
* Fixed a lot of other bugs

### 0.5.7
* Added Item Pipes
* Added Fluid Pipes
* Added Dynamite
* Fix duplicated MetaItem IDs
* Fix duplicated Distillery recipes
* Fix lens colors
* Remove placeholder pipes
* Added method for pbf recipe removal
* Rewritten cable code