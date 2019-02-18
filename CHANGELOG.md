## Changelog

### 1.5.18
* Fix certain crashes with invalid pipe items and pipe placement in MP. Closes #576. Closes #573.
* Make biome_dictionary raise error on invalid tags and upper case them automatically. Closes #575.
* Fix frame recipes. Closes #571.
* Fixed wrong output in UI of turbines. Closes #574.
* Fixed large turbines not loosing rotor speed while working disabled.
* Disabled electrolyzing of uranium compound ores.
* Fixed high pressure machines not overclocking recipes.

### 1.5.15
* Change amount of crushed ore obtained from different ores
* Nerfed smelting of compound dust to nuggets (yet by smelting all dust from 1 ore block you will get 1 or 2 ingots for forge hammer/macerator)
* Removed smelting of crushed ore
* Jackhammer no longer behaves like hammer on blocks
* Buffed hammer durability for block mining and dig speed

### 1.5.14
* Hotfix for crash with coal boilers

### 1.5.13
* Added foam blocks and reinforced stone (colorable)
* Rework frames so they act like scaffolds and are paintable now
* Added foam sprayer for placing them
* Fixed some minor bugs and issues

### 1.5.12
* Fixed server side crash. Closes #510.
* Refactored meta tile entity sync to use less data and be faster.
* Changed size of input * output fluid tanks to 64/32 buckets. Closes #510.
* Fixed wrong sound type of gravel ores.
* Disable steel decomposition.

### 1.5.11
* Updated pipes throughput values
* Fixed damage values back. Closes #411
* fix cobalt missing screws (#407)
* Fixed server-side crash with redefined storage. Closes #397.
* Fixed some graphical glitches. Closes #399. Closes #408.
* Fixed crash when painting pipes. Closes #402.
* Fixed wrong sphere generator behavior. Closes #422.
* Fixed wrong min. amount of outputs for ore washer. Closes #425.
* Removed blaze blocks. Closes #417.
* Disallow automation of primitive blast furnace. Closes #423.
* Fixed diesel engine not using oxygen. Closes #401.
* Added wooden frames. Made wood plates usable in place of planks. Closes #418.
* Fixed wrong speed on high pressure steam machines. Disallow overclocking for bronze steam machines. Closes #400.
* Fixed wrong information about items for chargers and battery buffers. Closes #409.
* Fixed wrong generation of invalid frames.
* Changed wood color to feel more vanilla-alike.
* Fixed crashes when loading multiblock controllers. Closes #410.
* Fix steam output update on steam machines not causing an chunk update
* Add quantum tank fluid handlers. Closes #436
* Fix quantum chest/tank tooltips. Closes #435
* Fix door recipes
* Updated Butchery knife. Changed the enchantment from sharpness II to Looting III
* fix #454 use isAir instead of Blocks.AIR comparison (#458)
* fix nulls passed to isAir in MetaTileEntityDieselEngine
* Do not accept energy with zero voltage or amperage. Closes #465
* do not trigger forge events when using setBlockState in dummy world
* Fixed quantum chest exploit. Closes #463.
* Fixed lag when attempting to fill/empty full/empty fluid tanks. Closes #460.
* Made dynamite usable in implosion compressor. Closes #457.
* Made cutter recipes using lubricant 4 times faster than water. Closes #450.
* Implemented comparator support for chests & tanks. Closes #447.
* Fixed wrong comparator output on battery buffer. Closes #467.
* Rebalanced turbine rotor holder speed multipliers and logic in general. Closes #452.
* Fixed turbine rotor not getting damage. Closes #451.
* Fixed PBF not updating texture. Closes #446.
* Fixed arc smelting recipes not generating for some ore prefixes. Closes #437.
* Increased capacity of transformers to 2x to allow constant transform rate.
* Fixed cyclic reference to OrePrefix when loading Materials class to early.
* Implemented a proper place to register materials from the code to implement.
* Added option to disable crashing behavior of invalid or error recipes.
* Removed useless and random information from tools. Should fix issues with other mods supplying tools into recipes. Closes #453.
* fixed jei multiblock info error
* Sort out multiblock parts for persistent order. Closes #474.
* Fixed harvest level for DustMaterial ores (#471)
* Some energy API improvements, battery fixes. Closes #483.
* Fixed battery buffer implementation bug. Closes #488.
* Fixed color not applying during initial machine placement. Closes #502.
* Fixed some machines have no painting color applied to their casings.
* Made fluid tanks usable as fluid containers in item-form.
* Made tungstensteel tank capacity 64 buckets.
* Fixed large cells. Increased their capacity to 64/256 buckets (for steel/tungstensteel).- Fixed wrong tools attack speed.
* Fixed creature spawning on machines. Closes #477.
* Implemented big rubber trees.
* Made electrolyzer and centrifuge output slots tabbed.
* Fixed some recipes.
* Fire tree grow event. Closes #509.
* #### Implemented covers: 
- Robot Arm (most advanced cover- filtering, putting in specific slots, buffering and many more) 
- Conveyor Belt (transporting items, not filtered) 
- Pump (Fluid transportation, filtered)
- Filter (item/fluid/ore dictionary) (allows you to filter items/fluids passing through machines)

All covers have GUIs that help you set them up. They can be placed on machines and on pipes. They're tiered

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