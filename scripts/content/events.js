// Imports
const mevents = require("lib/events");
const mfx = require("content/effects/effects");
const msfx = require("content/effects/status-effects");
const mliquids = require("content/liquids");
const mblocks = require("content/blocks");
const {surgeBullet} = require("content/blocks/turrets/electric/capacitor")

// Runnables for events
function statusZone() {
    mevents.puddleStatusEffectZone(msfx.arctifreezing, mfx.arctifreezeSquare, mfx.arctifreezeLineSquare, 48, mliquids.arctifluid, false, false, null);
    mevents.puddleStatusEffectZone(msfx.liquefying, mfx.liquefyingSquare, mfx.liquefyingLineSquare, 48, mliquids.quarkPlasma, true, true, Seq.with(
        Blocks.platedConduit, Blocks.liquidSource, Blocks.liquidVoid
    ));
	mevents.bulletStatusEffectZone(msfx.shocked, mfx.electricSquare, mfx.electricLineSquare, 48, surgeBullet, false, true, Seq.with(
		Blocks.itemSource, Blocks.itemVoid, Blocks.liquidSource, Blocks.liquidVoid, Blocks.plastaniumWall, Blocks.plastaniumWallLarge
	))
};

// Adding events to Events
Events.run(Trigger.update, statusZone);
