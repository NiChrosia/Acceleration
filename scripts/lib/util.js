Events.on(ClientLoadEvent, () => {
	const addCheck = key => Core.settings.getBool(key) ? null : Vars.ui.settings.graphics.checkPref(key, false);
	addCheck("puddle-status-zone");
});

const ModName = "acceleration-";

function getTextureName(blockName, name) {
	let fullName = ModName + blockName;
	return fullName + name;
};

function getRegion(blockName, name) {
	if (name == null) {
		return Core.atlas.find(ModName + blockName.substr(0, blockName.length-1))
	}
	return Core.atlas.find(ModName + blockName + name)
};

const cblock = name => Vars.content.getByName(ContentType.block, ModName + name);
const cliquid = name => Vars.content.getByName(ContentType.liquid, ModName + name);
const cstatus = name => Vars.content.getByName(ContentType.status, ModName + name);
const citem = name => Vars.content.getByName(ContentType.item, ModName + name);
const cunit = name => Vars.content.getByName(ContentType.unit, ModName + name);
const percent = (partial, total) => partial / total;
const hexColor = hexValue => Color.valueOf(hexValue);
const avg = (x, y) => (x + y) / 2;
// const dst = (ax, ay, bx, by, distance) => ((ax * ay) + (bx * by)) < Mathf.pow(distance, 2);
const dst = (ax, ay, bx, by, distance) => Mathf.dst(ax, ay, bx, by) < distance

function circleEffect(color, time) {
	return Effect(time, e => {
		Draw.color(color, Color.white, e.fout() / 5 + Mathf.randomSeedRange(e.id, 0.12));

		Angles.randLenVectors(e.id, 2, 1 + e.fin() * 3, (x, y) => {
			Fill.circle(e.x + x, e.y + y, .2 + e.fout() * 1.2);
		});
	});
};

function squareShieldEffect(size, color, time) {
	size *= 0.75
	return Effect(time, e => {
		Draw.color(color);
		Draw.z(Layer.shields);
		Fill.poly(e.x, e.y, 4, size, 45);
	});
};

function lineSquareEffect(size, color, time) {
	size *= 0.75
	return Effect(time, e => {
		Draw.color(color);
		Draw.z(Layer.shields + 1);
		Lines.poly(e.x, e.y, 4, size, 45);
	});
};

const autoUpdate = (name, repo, branch, hjson) => {
    
    let modLocal = Vars.mods.locateMod(name).meta;
    
    Events.on(ClientLoadEvent, () => {
        if(!Core.settings.getBool("mod." + name + ".autoupdate-disabled")){
            Core.net.httpGet(
                "https://raw.githubusercontent.com/" + repo + "/" + branch + "/mod." + (hjson ? "h" : "") + "json",
                (result) => {
                    let modJson = JSON.parse(result.getResultAsString());
                    let comparable = (number) => Number(number.toString().replace(".", ""));
                    if(comparable(modLocal.version) < comparable(modJson.version)){
                        Vars.ui.showCustomConfirm(
                            "Auto Update",
                            "Do you want to update the mod [accent]" + name + "[]?\n[red]v" + modLocal.version + "[] -> [green]v" + modJson.version + "[]",
                            "Yes",
                            "No",
                            () => {
                                Vars.ui.loadfrag.show();
                                Vars.ui.mods.show();
                                Vars.ui.mods.hide();
                                Vars.ui.mods.children.get(1).children.get(2).children.get(0).children.each(e => {
                                    if(e.toString().includes(modLocal.displayName)){
                                        e.children.get(1).children.get(1).fireClick();
                                        Core.scene.dialog.children.get(2).children.get(1).fireClick();
                                        if(Core.scene.dialog != null){
                                            Core.scene.dialog.hide();
                                        };
                                    };
                                });
                                Vars.ui.mods.show();
                                Vars.ui.mods.hide();
                                let isMobile = Vars.mobile;
                                Vars.mobile = false;
                                Vars.ui.mods.children.get(1).children.get(1).children.get(0).fireClick();
                                Core.scene.dialog.children.get(1).children.get(0).children.get(1).fireClick();
                                Core.scene.dialog.children.get(1).children.get(1).text = repo;
                                Core.scene.dialog.children.get(2).children.get(1).fireClick();
                                Vars.mobile = isMobile;
                                Events.on(Trigger.update.class, () => {
                                    Vars.ui.mods.children.get(1).children.get(2).children.get(0).children.each(e => {
                                        if(e.toString().includes(modJson.displayName + "\nv" + modJson.version)){
                                            Vars.ui.loadfrag.hide();
                                        };
                                    });
                                });
                            },
                            () => {
                                Log.warn("Auto Update for the mod [accent]" + name + "[] cancelled by the user.");
                            }
                        );
                    } else {
                        Log.info("Mod [accent]" + name + "[] is up to date (installed: [accent]" + modLocal.version + "[], latest: [accent]" + modJson.version + "[]), skipping Auto Update dialog.");
                    };
                },
                (error) => {
                    Log.err("Error while trying to get version info of mod [accent]" + name + "[]: \n" + error);
                }
            );
        };
    });
    
    Events.on(BlockInfoEvent, () => {
        if(Core.scene.dialog.toString().includes(modLocal.displayName)){
            if(Core.settings.getBool("mod." + name + ".autoupdate-disabled")){
                Vars.ui.content.children.get(2).button("Enable Auto Update", () => {
                    Core.settings.put("mod." + name + ".autoupdate-disabled", false);
                    Log.info("Auto Update for mod [accent]" + name + "[] is enabled");
                });
            }else{
                Vars.ui.content.children.get(2).button("Disable Auto Update", () => {
                    Core.settings.put("mod." + name + ".autoupdate-disabled", true);
                    Log.info("Auto Update for mod [accent]" + name + "[] is disabled");
                });
            };
            Core.scene.dialog.hidden(() => {
                Vars.ui.content.children.get(2).clearChildren();
                Vars.ui.content.addCloseButton();
            });
        };
    });
};

module.exports = {
	getTextureName: getTextureName,
	getRegion: getRegion,
	cliquid: cliquid,
	citem: citem,
	cblock: cblock,
	cstatus: cstatus,
	percent: percent,
	hexColor: hexColor,
	ModName: ModName,
	avg: avg,
	dst: dst,
	cunit: cunit,
	circleEffect: circleEffect,
	squareShieldEffect: squareShieldEffect,
	lineSquareEffect: lineSquareEffect,
	autoUpdate: autoUpdate
};