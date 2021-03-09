// Imports

const mevents = require("lib/events");
const mfx = require("content/effects/effects");
const smfx = require("content/effects/status-effects");
const mliquids = require("content/liquids");
const mblocks = require("content/blocks");

// Runnables for events

function puddleStatusZone() {
	mevents.puddleStatusEffectZone(smfx.arctifreezing, mfx.arctifreezeSquare, mfx.arctifreezeLineSquare, 48, mliquids.arctifluid, false, null);
	mevents.puddleStatusEffectZone(smfx.liquefying, mfx.liquefyingSquare, mfx.liquefyingLineSquare, 48, mliquids.quarkPlasma, true, Seq.with([
		mblocks.fortifiedConduit, Blocks.liquidSource, Blocks.liquidVoid
	]));
};

// Adding events to Events

Events.run(Trigger.update, puddleStatusZone);