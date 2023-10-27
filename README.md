Specialised Cells
=======

Specialised Cells is an addon mod for [Applied Energistics 2](https://github.com/AppliedEnergistics/Applied-Energistics-2), which adds some new storage cells to use for specific purposes.

The key benefit is that the types system works a bit differently in these cells.

There are some tradeoffs, however:
 
- Bytes per type starts at 16 (like a standard 4K Cell)
- Idle drain is slightly higher than a size-equivalent standard Cell
- Item count per byte is halved (4 items per byte)
- Item types are limited to 31 (unless otherwise noted)

## Armory Cell (1K, 4K, 16K)
This cell stores only Armors (player and horse), weapons (Swords, Bows, Crossbows, and Tridents), and Elytra. This can be customised via the `specialised_cells:armory_cell_storable` tag.

It can be partitioned further and will always act in Fuzzy Mode (ignoring damage).

This cell counts only the **item** for the types system - this means that you can store 10 different Iron Swords with different damage levels, and it will only count as one type!

## Tools Cell (1K, 4K, 16K)
This cell stores only "tools" - Axes, Pickaxes, Shovels, Hoes, Wrenches, Fishing Rods, Carrot/Warped Fungus on a Stick, Flint & Steel, AE2 Entropy Manipulator, AE2 Charged Staff, AE2 Quartz Knifes.  This can be customised via the `specialised_cells:tools_cell_storable` tag.

It can be partitioned further and will always act in Fuzzy Mode (ignoring damage).

This cell counts only the **item** for the types system - this means that you can store 10 different Iron Pickaxes with different damage levels, and it will only count as one type!

## Enchanted Book Cell (1K, 4K, 16K)
This cell only stores Enchanted Books. However, the types are counted by **Enchantment type**. This means that, for instance, Power I and Power II will only use a single type. 

It can store **63** different enchantment types. NB: compound enchantments count separately; a Book with Power and Unbreaking will use its own type and will not share a type with either part.


# License

* Specialised Cells
    - (c) Thiakil
    - [![License](https://img.shields.io/badge/License-MIT-red.svg?style=flat-square)](http://opensource.org/licenses/MIT)
* Textures and Models
    - (c) Thiakil
    - (c) Based on standard cell textures by [Ridanisaurus Rid](https://github.com/Ridanisaurus/)
    - [![License](https://img.shields.io/badge/License-CC%20BY--NC--SA%203.0-yellow.svg?style=flat-square)](https://creativecommons.org/licenses/by-nc-sa/3.0/)