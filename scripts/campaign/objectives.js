// Planetary Terminal Activated, copied from abomb4/dimension-shard

const objectivePlanetaryTerminalActivated = (() => {
    const key = 'planetary-terminal-activated';
    const objective = new Objectives.Objective({
        complete() { return Core.settings.getBool(key, false); },
        display() { return exports.getMessage("msg", "planetaryTerminalActivated"); }
    });
    function contentUnlocked(content) {
        if (typeof content.unlocked === "boolean") {
            return content.unlocked;
        } else {
            return content.unlocked();
        }
    }
    Events.run(Trigger.acceleratorUse, run(() => {
        if (Vars.net.client()) return;
        Core.settings.put(key, true)
        TechTree.all.each(cons(node => {
            if (!contentUnlocked(node.content) && node.requirements.length == 0 && !node.objectives.contains(boolf(o => !o.complete()))) {
                node.content.unlock();
            }
        }));
    }));
    return objective;
})();

module.exports = {
	objectivePlanetaryTerminalActivated: objectivePlanetaryTerminalActivated
};