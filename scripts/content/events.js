// Imports

const mevents = require("lib/events");
const mfx = require("content/effects/effects");
const smfx = require("content/effects/status-effects");
const mliquids = require("content/liquids");

// Runnables for events

function glaciafreezeZone() {
	mevents.puddleStatusEffectZone(smfx.permafrost, mfx.glaciafreezeSquare, mfx.glaciafreezeLineSquare, 48, mliquids.glaciafluid);
	mevents.puddleStatusEffectZone(smfx.liquefying, mfx.liquefyingSquare, mfx.liquefyingLineSquare, 48, mliquids.quarkPlasma);
};

// Adding events to Events

Events.run(Trigger.update, glaciafreezeZone);