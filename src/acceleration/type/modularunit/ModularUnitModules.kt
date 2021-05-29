package acceleration.type.modularunit

import acceleration.Acceleration
import acceleration.content.AccelerationPal
import mindustry.content.Items
import mindustry.graphics.Pal
import mindustry.type.ItemStack

open class ModularUnitModules {
    companion object {
        val electricityAbsorber = ModularUnitModule().apply {
            internalName = "electricity-absorber"

            name = "Electricity Absorber"
            description = "Absorbs electricity into its plastanium shell."
            color = Pal.plastanium
            requirements = {
                ItemStack.with(
                    Items.plastanium,
                    (Acceleration.modularUnitProperties.weight.value + 4f) * 4f
                )
            }

            modifiers = ModularUnitProperties().apply {
                weight.value = 4f
                toughness.value = 1f
            }

            max = 5
        }

        val explosives = ModularUnitModule().apply {
            internalName = "explosives"

            name = "Explosives"
            description = "Explosives strapped onto the unit. Typically used for kamikaze strikes."
            color = Pal.redDust
            requirements = {
                ItemStack.with(Items.blastCompound, 4f)
            }

            modifiers = ModularUnitProperties().apply {
                volatility.value = 8f
                weight.value = 1f
            }

            max = 5
        }

        val radioactivityProduction = ModularUnitModule().apply {
            internalName = "radioactivity-production"

            name = "Radioactivity Production Module"
            description = "Produces radioactivity for destroying biological lifeforms."
            color = Pal.thoriumPink
            requirements = {
                ItemStack.with(Items.lead, 80, Items.silicon, 40, Items.thorium, 8f)
            }

            modifiers = ModularUnitProperties().apply {
                volatility.value = 5f
                weight.value = 2f
            }

            max = 5
        }

        val titaniumPlating = ModularUnitModule().apply {
            internalName = "titanium-plating"

            name = "Titanium Armor Plating"
            description = "Provides armor for protecting the unit."
            color = Items.titanium.color
            requirements = {
                ItemStack.with(Items.titanium, 8f)
            }

            modifiers = ModularUnitProperties().apply {
                toughness.value = 1f
                weight.value = 0.5f
            }

            max = 10
        }

        val surgePlating = ModularUnitModule().apply {
            internalName = "surge-plating"

            name = "Surge Armor Plating"
            description = "Provides armor for protecting the unit."
            color = Items.surgeAlloy.color
            requirements = {
                ItemStack.with(Items.surgeAlloy, 8f)
            }

            modifiers = ModularUnitProperties().apply {
                toughness.value = 3f
                weight.value = 0.35f
            }

            max = 10
        }

        val energyShield = ModularUnitModule().apply {
            internalName = "energy-shield"

            name = "Energy Shield"
            description = "Provides a shield made of energy for protecting the unit."
            color = AccelerationPal.energy
            requirements = {
                ItemStack.with(Items.silicon, 20, Items.phaseFabric, 10)
            }

            modifiers = ModularUnitProperties().apply {
                energyConsumption.value = 2f
                toughness.value = 2f
                weight.value = 1.5f
                complexity.value = 4f
            }

            max = 10
        }

        val forceField = ModularUnitModule().apply {
            internalName = "force-field"

            name = "Force Field"
            description = "Provides a large force field to protect nearby structures and itself."
            color = AccelerationPal.energy
            requirements = {
                ItemStack.with(Items.silicon, 160, Items.phaseFabric, 80, Items.surgeAlloy, 50)
            }

            modifiers = ModularUnitProperties().apply {
                energyConsumption.value = 8f
                toughness.value = 1f
                weight.value = 2.5f
                complexity.value = 6f
            }

            max = 1
        }

        val electricPort = ModularUnitModule().apply {
            internalName = "electric-port"

            name = "Electric Port"
            description = "Allows for connection to a power grid via the Electric Port block."
            color = Pal.surge
            requirements = {
                ItemStack.with(Items.silicon, 40, Items.surgeAlloy, 15)
            }

            modifiers = ModularUnitProperties().apply {
                weight.value = 0.5f
                complexity.value = 10f
            }

            max = 1
        }
        
        val thermiteProduction = ModularUnitModule().apply {
            internalName = "thermite-production"

            name = "Thermite Production Module"
            description = "Allows for production of thermite, for powerful flamethrower weaponry."
            color = Pal.lightPyraFlame
            requirements = {
                ItemStack.with(Items.copper, 200, Items.silicon, 150, Items.pyratite, 120)
            }

            modifiers = ModularUnitProperties().apply {
                volatility.value = 1.5f
                weight.value = 4f
                energyConsumption.value = 4f
            }
        }

        fun each(iterator: (ModularUnitModule) -> Unit) {
            arrayOf(
                electricityAbsorber,
                explosives,
                radioactivityProduction,
                titaniumPlating,
                surgePlating,
                energyShield,
                forceField,
                electricPort,
                thermiteProduction
            ).forEach(iterator)
        }
    }
}