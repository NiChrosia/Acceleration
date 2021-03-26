# Unit Tree

## Reassemblers

### Additive Reassembler
An advanced reconstructor that fully reassembles units.  
Upgrades inputted units to the second tier.

#### `stats:`
|**`stat`**|**`value`**|
|:-:|:-:|
`size`|`3`
`health`|`480`
`powerUse`|`240`
`itemCapacity`|`90`
`productionTime`|`15`
`research`|`multiplicative-reconstructor`

|**`unit`**|**`upgrade`**|
|:-:|:-:|
`ion`|`spark`

### Multiplicative Reassembler
An advanced reconstructor that fully reassembles units.  
Upgrades inputted units to the third tier.

#### `stats:`
|**`stat`**|**`value`**|
|:-:|:-:|
`size`|`5`
`health`|`1400`
`powerUse`|`480`
`itemCapacity`|`270`
`productionTime`|`40`
`research`|`additive-reassembler`

|**`unit`**|**`upgrade`**|
|:-:|:-:|
`spark`|`plasma`

### Exponential Reassembler
An advanced reconstructor that fully reassembles units.  
Upgrades inputted units to the fourth tier.

#### `stats:`
|**`stat`**|**`value`**|
|:-:|:-:|
`size`|`7`
`health`|`2400`
`powerUse`|`1040`
`itemCapacity`|`775`
`productionTime`|`120`
`research`|`multiplicative-reassembler`

|**`unit`**|**`upgrade`**|
|:-:|:-:|
`plasma`|`discharge`

### Tetrative Reassembler
An advanced reconstructor that fully reassembles units.  
Upgrades inputted units to the fifth and final tier.

#### `stats:`
|**`stat`**|**`value`**|
|:-:|:-:|
`size`|`9`
`health`|`3800`
`powerUse`|`2000`
`itemCapacity`|`1550`
`productionTime`|`320`
`research`|`exponential-reassembler`

|**`unit`**|**`upgrade`**|
|:-:|:-:|
`discharge`|`aurora`

## Controller tree

### Ion
A nimble controller ship with light energy weapons.

#### `requirements:`
|`amount`|`material`|
|:-:|:-:|
`15`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-phase-fabric.png)
`25`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-titanium.png)
`30`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-silicon.png)

#### `stats:`
|**`stat`**|**`value`**|
|:-:|:-:|
`health`|`0`
`armor`|`0`
`flying`|`true`
`speed`|`0`
`buildSpeed`|`0`
`mineSpeed`|`0`
`mineTier`|`0`
`commandLimit`|`0`
`itemCapacity`|`0`
`range`|`0`

### Spark
A fast support ship, with an overclocking shield. Shoots piercing electric bullets.

#### `requirements:`
|`amount`|`material`|
|:-:|:-:|
`45`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-phase-fabric.png)
`45`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-silicon.png)

#### `stats:`
|**`stat`**|**`value`**|
|:-:|:-:|
`health`|`0`
`armor`|`0`
`flying`|`true`
`speed`|`0`
`buildSpeed`|`0`
`mineSpeed`|`0`
`mineTier`|`0`
`commandLimit`|`0`
`itemCapacity`|`0`
`range`|`0`

### Plasma
Agile controller ship with a damaging electric aura. Shoots overloading energy bullets that sharply decrease enemy fire rate.

#### `requirements:`
|`amount`|`material`|
|:-:|:-:|
`120`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-phase-fabric.png)
`90`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-silicon.png)
`60`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-plastanium.png)

#### `stats:`
|**`stat`**|**`value`**|
|:-:|:-:|
`health`|`0`
`armor`|`0`
`flying`|`true`
`speed`|`0`
`buildSpeed`|`0`
`mineSpeed`|`0`
`mineTier`|`0`
`commandLimit`|`0`
`itemCapacity`|`0`
`range`|`0`

### Discharge
Controller ship with a powerful compressed shield. Also regenerates shields of nearby units. 
Shoots dual energy machine guns.

#### `requirements:`
|`amount`|`material`|
|:-:|:-:|
`300`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-phase-fabric.png)
`225`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-silicon.png)
`150`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-plastanium.png)
`100`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-surge-alloy.png)
`60/s`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/liquid-cryofluid.png)

#### `stats:`
|**`stat`**|**`value`**|
|:-:|:-:|
`health`|`0`
`armor`|`0`
`flying`|`true`
`speed`|`0`
`buildSpeed`|`0`
`mineSpeed`|`0`
`mineTier`|`0`
`commandLimit`|`0`
`itemCapacity`|`0`
`range`|`0`

### Aurora
A lithe controller ship, with a ultracompressed shield, and a revitalizing field (Regenerating, Overdriving, and Shield Regeneration).  
Has a mounted railgun, as well as two cryo-energy HMGs.  
Shield has large amount of health, but regen and cooldown are very slow.

#### `requirements:`
|`amount`|`material`|
|:-:|:-:|
`600`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-phase-fabric.png)
`450`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-silicon.png)
`300`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-plastanium.png)
`200`|![](https://raw.githubusercontent.com/Anuken/Mindustry/master/core/assets-raw/sprites/items/item-surge-alloy.png)
`30/s`|![](https://raw.githubusercontent.com/nichrosia/Acceleration/master/sprites/liquids/arctifluid.png)

#### `stats:`
|**`stat`**|**`value`**|
|:-:|:-:|
`health`|`0`
`armor`|`0`
`flying`|`true`
`speed`|`0`
`buildSpeed`|`0`
`mineSpeed`|`0`
`mineTier`|`0`
`commandLimit`|`0`
`itemCapacity`|`0`
`range`|`0`

## Plutonic Tree
Color: `d44c08`

### Pit

An extremely hot spider-like mech. Fires lethally hot missiles.

### Shaft

A fast spider-like mech. Fires piercing lasers and close-range piercing blasts.

### Chasm

An extremely fast spider mech. Has two shock HMGs.

### Abyss

### Void