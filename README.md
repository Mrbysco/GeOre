[![build](https://github.com/Mrbysco/GeOre/actions/workflows/build.yml/badge.svg)](https://github.com/Mrbysco/GeOre/actions/workflows/build.yml) 
[![](http://cf.way2muchnoise.eu/versions/530544.svg)](https://www.curseforge.com/minecraft/mc-mods/geore)

# GeOre #

## About ##
This mod takes heavy inspiration of the vanilla amethyst geode feature and applies it to other valuables to.

## License ##
* GeOre is licensed under the MIT License
  - (c) 2024 ShyNieke
  - [![License](https://img.shields.io/badge/License-MIT-red.svg?style=flat)](http://opensource.org/licenses/MIT)

## Downloads ##
Downloads will be located on [CurseForge](https://www.curseforge.com/minecraft/mc-mods/geore)

## Balancing configuration ##

Server owners can tune each ore in `config/geore-common.toml` under `Balancing`:

* `Growth`: rate factor for budding GeOre growth. `1` keeps the default rate; `0.2` makes growth five times slower; `5` makes it five times faster.
* `GeodeSpawnRate`: rate factor for geode generation during chunk generation. `1` keeps the default rate; `0.2` makes that ore's geodes five times rarer; `5` makes them five times more common.

Both settings are available for every GeOre, including Ancient Debris and ores supplied by supported mods.
