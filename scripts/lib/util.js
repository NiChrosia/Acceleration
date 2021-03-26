const ModName = this.modName + "-";

const getTextureName = (blockName, name) => ModName + blockName + name;
const getRegion = (blockName, name) => Core.atlas.find(ModName + blockName + (!name ? "" : name));
const cblock = name => Vars.content.getByName(ContentType.block, ModName + name);
const cliquid = name => Vars.content.getByName(ContentType.liquid, ModName + name);
const cstatus = name => Vars.content.getByName(ContentType.status, ModName + name);
const citem = name => Vars.content.getByName(ContentType.item, ModName + name);
const cunit = name => Vars.content.getByName(ContentType.unit, ModName + name);
const hexColor = hexValue => Color.valueOf(hexValue);

function dst(x1, y1, x2, y2, distance){
    let x = x2 - x1;
    let y = y2 - y1;
    return x * x + y * y < distance * distance;
}

function avg(/* number, number, number... */){
    let total = 0;
    for(let i in arguments){
        let e = arguments[i];
        if(!isFinite(e) || !(e instanceof Number)) continue; // filters out Infinity, negative infinity and anything that is not a number
        total += e;
		print(e);
    }
	print(total)
    return total / arguments.length;
}

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

function equalByAmount(x, y, amount) {
    let val = Math.abs((x - y));
    return val <= amount;
};

module.exports = {
    getTextureName: getTextureName,
    getRegion: getRegion,
    cliquid: cliquid,
    citem: citem,
    cblock: cblock,
    cstatus: cstatus,
    hexColor: hexColor,
    ModName: ModName,
    avg: avg,
    dst: dst,
    cunit: cunit,
    circleEffect: circleEffect,
    squareShieldEffect: squareShieldEffect,
    lineSquareEffect: lineSquareEffect,
    equalByAmount: equalByAmount
};
