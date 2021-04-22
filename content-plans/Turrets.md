# Turret tree

## Cryogenic (Railgun)
|`parent`|`size`|`name`|`description`|
|:-:|:-:|:-:|:-:|
`foreshadow`|`3x3`|Sleet|Slowly fires hypersonic projectiles. Enemies hit by it are heavily stunned, and cannot move. Sprite made by xjamiex.

### Ammo Types
|`name`|`ammoType`|`powerUsage`|
|:-:|:-:|:-:|
Sleet|`voltaic-velosium`|350

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