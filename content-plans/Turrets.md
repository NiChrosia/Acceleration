# Turret tree

## Electric Piercing
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`capacitor`|`4x4`|Core|Shoots thin electric piercing bullets with extreme damage, slow rate of fire, requires a large amount of power to operate. Optimally uses phase fabric as ammo.

### Ammo Types
|`name`|`ammoType`|`ammo-optional`|`powerUsage`|
|:-:|:-:|:-:|
Core|`silicon`, `phase-fabric`|true|720

## Cryogenic (Railgun)
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`gate`|`2x2`|Frost|Utilizes cryogem for its cold properties. Fires clumps of damaging cryocatalyst at enemies. Slows enemy weapons down in a radius around the impact.
`frost`|`3x3`|Snowflake|Shoots razor sharp whirling cryocatalyst shards. Requires large amounts of power for sharpening and hyperrotation. Bullets temporarily disarm enemies.
`snowflake`|`4x4`|Sleet|Shoots insanely sharpened projectiles at enemies. Requires massive amounts of power for laser cutting and spinning the projectile to extreme speeds. Bullets slice through enemies, resulting in usually fatal damage.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Frost|`cryogem`|`0`
Snowflake|`cryocatalyst`|`180`
Sleet|`cryocatalyst`|`720`

## Accelerated (Machine Gun)
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`gate`|`2x2`|Lepton|Rapidly fires magnetically accelerated projectiles.
`lepton`|`3x3`|Neutrino|Swiftly fires electromagnetically propulsed projectiles. Medium piece ability.
`neutrino`|`4x4`|Tachyon|Expeditiously fires ultra-high velocity projectiles. Approximately an eighth the speed of a railgun. High pierce ability.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Lepton|`graphite`, `titanium`, `silicon`|60
Neutrino|`titanium`, `thorium`, `silicon`, `plastanium`|180
Tachyon|`thorium`, `plastanium`, `surge-alloy`|720

## Support
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`capacitor`|`3x3`|Restorer|Shoots a noncolliding bullet at enemies and friendly blocks. The bullet has a mending field around it.
`restorer`|`4x4`|Rejuvenator|Rapidly shoots small healing projectiles with a mend effect around the bullet on impact.  Also emits mending fields around the turret itself.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Restorer|`silicon`|`60`
Rejuvenator|`silicon` (mandatory), `phase-fabric` (optional)|`120`

## Flame
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`scorch`|`2x2`|Singe|Shoots very high temperature flames at the enemy. Requires energy for igniting and sustaining the flames. Slightly larger flames than the Scorch.
`singe`|`3x3`|Char|Shoots white-hot flames at the enemy. Requires energy for igniting and sustaining the flames. Much larger flames than the Scorch.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Singe|`none`|`60`
Char|`none`|`240`

## Shrapnel

`fuse`|`4x4`|Alloy|Shoots four blasts at close range at enemies.
`alloy`|`5x5`|Compound|Fires six blasts at close range at enemies. Significantly more concentrated than the Fuse.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Alloy|`thorium`, `electric-velosium`, `surge-alloy`|`0`
Compound|`electric-velosium`, `surge-alloy`, `cryocatalyst`|`0`