# Turret tree

## Electric
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`arc`|`1x1`|Transistor|Shoots small electric bolts with a low power consumption, can be clicked to toggle enabledness.
`transistor`|`2x2`|Gate|Shoots homing electric bullets. Requires silicon & power as ammo.
`gate`|`3x3`|Capacitor|Shoots shocking bullets with a damaging electric field.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Transistor|`power`|15
Gate|`silicon`, `phase-fabric`|60
Capacitor|`silicon`, `phase-fabric`, `surge-alloy`|180

## Electric Piercing
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`capacitor`|`4x4`|Core|Shoots thin electric piercing bullets with extreme damage, slow rate of fire, requires a large amount of power to operate. Optimially uses phase fabric as ammo.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Core|`silicon`, `phase-fabric`|720

## Cryogenic
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`gate`|`2x2`|Frost|Utilizes cryocatalyst for its cryogenic properties. Fires clumps of damaging cryocatalyst at enemies. Slows enemy weapons down in a radius around the impact.
`frost`|`3x3`|Snowflake|Shoots razor sharp whirling cryocatalyst shards. Requires large amounts of power for sharpening and hyperrotation. Bullets temporarily disarm enemies.
`snowflake`|`4x4`|Sleet|Shoots insanely sharpened projectiles at enemies. Requires massive amounts of power for laser cutting and spinning the projectile to extreme speeds. Bullets slice through enemies, resulting in usually fatal damage.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Frost|`cryocatalyst`|`0`
Snowflake|`cryocatalyst`|`180`
Sleet|`cryocatalyst`|`720`

## Support
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`capacitor`|`3x3`|Restorer|Shoots a noncolliding bullet at enemies and friendly blocks. The bullet has a mending field around it.
`restorer`|`4x4`|Rejuvenator|Rapidly shoots small healing projectiles with a mend effect around the bullet on impact.  Also emits mending fields around the turret itself.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Restorer|`silicon`, `phase-fabric`|`60`
Rejuvenator|`silicon`, `phase-fabric`|`120`