// Imports
const mevents = require("lib/events");
const mfx = require("content/effects/effects");
const msfx = require("content/effects/status-effects");
const mliquids = require("content/liquids");
const mblocks = require("content/blocks");

// Runnables for events
function statusZone() {
    if (!Vars.state.paused) {
        mevents.puddleStatusEffectZone(msfx.arctifreezing, mfx.arctifreezeSquare, mfx.arctifreezeLineSquare, mfx.arctifrozen, 48, mliquids.arctifluid, false, null);
        mevents.puddleStatusEffectZone(msfx.liquefying, mfx.liquefyingSquare, mfx.liquefyingLineSquare, mfx.liquefied, 48, mliquids.quarkPlasma, true, Seq.with(
            Blocks.platedConduit, Blocks.liquidSource, Blocks.liquidVoid
        ));
    };
};

// Adding events to Events
Events.run(Trigger.update, statusZone);