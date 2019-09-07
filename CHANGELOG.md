## Changelog

### 1.8.6
* Fixed inability to make dark concrete bricks.
* Fixed Air Collector consuming EU when tank is full.
* Disabled decomposition of more organic compounds.
* Removed max voltage transformer.
* Prevent invalid models on facades from crashing game.
* Fixed JEI multiblock preview not showing machines.
* Fixed robotic arm modes not working.
* Allow controlling battery buffers output via machine controller.

### 1.8.4
* Added Energy Field Projector.
* Fix server side crash with covers on pipes.
* Tree capitation now only cuts logs connected via at most 1 leaves block.
* Play destroy sound and spawn particles when tree capitating wood blocks too.

### 1.8.3-hotfix
* Removed debug log
* Fixed exception while badly written mods attempt to retrieve info from invalid stacks.

### 1.8.3
* Added Nano Saber.
* Fixed severe e-net bug where battery buffers voided accepted EU.
* Fixed cables burning on too many amps even if they're not consumed.
* Allow using GTCE gems and ingots as beacon payments.
* Changed some high-tier circuit textures.

### 1.8.2
* Show full inventory in most covers UI instead of only hotbar.
* Added recipe for electric screwdriver. Closes #530.
* Added alternative recipes for tools using power units.
* Electric tools now leave power units when their tips are broken. Closes #605.
* Remove Quartz Block cutting recipe. Closes #848.
* Temporary revert pipe rendering to previous version. Closes #846.
* Fix fisher-related compile issues.
* Added discharge mode for batteries in player inventory.
* Don't connect wires on sides of mach ines that can't neither input nor output energy.
* Increased overclock efficiency a bit for high voltage recipes.
* Reduce damage taken by tools in certain actions a bit. 
* Added a Fisher.
* Show only stone variants of ore in ores tab, show all variants in search tab.
* Use voltage names instead of tier numbers in electric items tooltip.
* Fixed incorrect pipes color after using spray on them.
* Particle textures of machines, cables and pipes are now properly colored.
* Added two new chest variants: wooden chest (27 slots) and small wooden chest (1 slot).
* Fixed incorrect lighting of pipes in world.
* Disabled cover placement grid on pipes - they now use sub areas for cover placement.
* Fixed covers not blocking flow between pipes.
* Allow blocking connections between pipes via wrench clicking on sides.
* Fixed battery buffer not updating comparator value.
* Disabled shift clicking for phantom item slots.
* Added blacklist option for filters and conveyors.
* Show actual meta tile entity creator mod ID in JEI.
* Enhanced placement grid for covers on machines and pipes, show it only when it's used now.
* Robotic Arm now isn't limited in the transfer rate in transfer exact & keep exact modes.
* Fix few tools-related issues. 

### 1.8.1
Added a config option to specify bonus EU output per turbine type.

### 1.8.0
* Bug fixes and tool improvements
* Nerfed large turbine to the ground so you will never use it again.
* Some other improvements.

### 1.7.1
* Added block breakers with several tiers.
* Refactor pump. Increase range and pump speed, made visuals better.
* Refactored byproduct boost mechanic to fit better into game.
* Adjust overclocking efficiency from 50% to 70%.
* Remove output tank index filter mechanic (replaced by fluid filters for more precise control)
* Add configuration circuit for NaOH production chain.
* Allow using sodalite and lazurite for lapotron crystals.
* Allow using sodalite and lazurite as blue dyes.
* Remove amperage requirements for thermal centrifuge.
* Fix wrong solar panel behavior on battery buffer.
* Remove duplicate TiCl4 recipe.
* Remove Radon requirements for some processing chains.
* Allow recycling pipes.
* Fix iron bars iron recycling loop.
* Fix charge overrides not working.
* Fix some redstone-related issues.
* Disable electrolyzing of some materials.
* Disallow input from output side. Can be configured with screwdriver.
* Minor recipe searching and buffering refactor, fixes some issues related to fluid canner.
* Allow toggling overclocking via GUI button for electrical machines.
* Increase duration of some ore recycling chain processes because of increased overclock efficiency.
* Made Lathe work as fast as extruder for rod production.
* Add forge hammer recipe for small gears.
* Made JackHammer use HV tier for charging.
* Fix certain ore deposits not generating.
* Fixed generators not showing in JEI as fuel recipe catalysts.
* Added bismuth as cassiterite byproduct.
* Fix JEI ingredient hovering not working on machine's fluid tanks.
* Fixed fluid filter upgrade in pump not working properly.
* Fixed rubber leaves and wood not burning.
* Fixed certain issues with charger.
* Change model in hand of rubber wood sapling to look like vanilla saplings.

### 1.7.0
* JEI now recognizes fluids in tanks as ingredients and can show their recipes.
* You can now drag ghost items and fluids from JEI to item filters and fluid filters.
* Allow inserting more items to "Keep Exact" mode than transfer rate of cover.
* Rework "Transfer Exact" mode of robotic arm to make more sense - now it only transfers items of same type in one operation, allowing to use it for example to combine dusts in packager.
* Added machine controller cover to control covers and machines with redstone.
* Added recipes for gravel and sand in forge hammer.
* Added validate/invalidate hooks for meta tile entities.
* Added turbine blade Recipes in the Assembling Machine.
* Fixed weird item movement when affected by >1 item collectors.
* Fixed pipes not being synced to other players on placement.
* Fixed crash with wrench overlay.
* Fixed rubber trees not producing rubber after server restart.
* Fixed dark cover lighting.
* Fixed wrong macerator byproduct outputs.
* Fixed error for slab recipe generation.
* Fixed inability to use plunger when there is little fluid left in machine.
* Fixed brewery consuming all items.
* Added wooden pipe recipes.
* Fix batteries not recharging in machines.
* Fix covers not dropping their inventory contents when removed (e.g filter upgrades)
* Fix weird pump behavior on deep fluid sources.

### 1.6.9
* Fix large boiler not using solid fuel.
* Fix collectors collecting items without energy.

### 1.6.8
* Fix silicon consuming integrated circuits + some other recipes.
* Allow taking fluids from inputs via containers.
* Changed lathe again to give 2 rods instead of long one.
* Re-textured pipes to look better.
* Re-design side hit calculation so covers are accessible in the similar to wrench manner.
* Fixed wooden tanks being able to hold hot fluids as items.
* Added wooden soft hammer.
* Increased stats of flint & added Fire Aspect II to flint tools.
* Fixed some cable duplication issues.
* Fix duplicate decomposition recipe for liquid air.

### 1.6.5
* Buff large boilers (a bit).
* Fix boilers burning container-dependent fuels like firestone from Railcraft.
* Fix ray tracing null exception.
* Fixed indium gallium phosphate crafting.
* Removed monazite -> helium recipe.
* Made autoclave a bit faster in crystallization.
* Reworked pipe texture locations a bit.
* Fixed fluid references in oregen.
* Fixed pipe burning crash.
* InventoryTweaks compatibility.

### 1.6.3
* Added item collectors. They collect items in configurable range.
* Fixed boule recipes consuming integrated circuits.
* Fixed NanoCPU wafer recipe wrong voltage.
* Fixed power wafer duplication.
* Fixed sodium sulfide recipes.
* Added smoke to overvoltage.
* Added rubber recipes for low-voltage cables.
* Added nugget/dust recipes to packer/unpacker.
* Fixed lumber axe not working.
* Made Primitive Blast Furnace properly handle all types of fuel and use remainder.
* Bump version & update changelog.

### 1.6.2
* Reworked chests. Re-implemented visuals, increased capacities.
* Added machine recipes for metal frames.
* Added surface block populator for custom indicators support for ores.
* Buff rotor efficiencies a bit more. Reworked rotor items.
* Implemented fortune support for hard hammers.
* Implemented anvil tool repairing.
* Fix many issues related to previous major beta version release.

### 1.6.0
* Merge GA chemistry into master.
* New circuits inspired by GA.
* Fix machine's auto outputting ignoring filtering covers (and covers installed in general)
* Tweak electrolyzing voltages a bit.
* Make Ilmenite a bit more valuable source of rutile.
* Change amount of noble gases compounds a bit and add radon.
* Increased rotor durability and efficiency for all rotors. Increased rotor cost a bit.
* Increased base pump speed a bit.
* Increased chests capacities.
* Made Tesla Coil drop player-kill requiring loot.
* Fix some machines not stacking after being broken down.
* Fix turbine blades dupe.
* Remove granites spheres from nether.
* Fix noble gases not using centrifuge for decomposition.
* Fixed disjunction crash.
* Fixed wrong overclocking on multi-amperage input machines.
* Fixed wrong light matrix usage on machine covers.

### 1.5.21
* Fix paper crafting recipes not showing in JEI.
* Fix wrong container item return with wooden form crafting.
* Fixed wiremill fine wire recipe missing.
* Fix tools harvesting not respecting block's harvest tool type.
* Made filter recipes simpler and more accessible in early game.
* Probably fix pipe crash without FMP installed.
* Fallback to StoneTypes.STONE for ore generation replaceable blocks.
* Rework StoneTypes to better fit above change. Cleanup OrePrefix.
* Fix OrePrefix maxStackSize not applying to material items.
* Do not apply incompatible material enchantments to tools.
* Allow to use sodalite and lapis in enchantment table.
* Refactor tool properties of most materials.
* Added vinteum as a dungeon loot to some places.
* Added unbreaking/mending enchantments support for tools.
* Fixed enchantment glint not being rendered on tools.

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