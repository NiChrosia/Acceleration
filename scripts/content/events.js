// Imports

const mevents = require("lib/events");
const mfx = require("content/effects/effects");
const smfx = require("content/effects/status-effects");
const mliquids = require("content/liquids");

// Runnables for events

function glaciafreezeZone() {
	mevents.puddleStatusEffectZone(smfx.arctifreezing, mfx.arctifreezeSquare, mfx.arctifreezeLineSquare, 48, mliquids.arctifluid, false);
	mevents.puddleStatusEffectZone(smfx.liquefying, mfx.liquefyingSquare, mfx.liquefyingLineSquare, 48, mliquids.quarkPlasma, true);
};

// Adding events to Events

Events.run(Trigger.update, glaciafreezeZone);