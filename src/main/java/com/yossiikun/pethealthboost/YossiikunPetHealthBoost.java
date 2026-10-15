
package com.yossiikun.pethealthboost;

import java.util.UUID;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(YossiikunPetHealthBoost.MODID)
public class YossiikunPetHealthBoost {
    public static final String MODID = "yossiikun_pet_health_boost";

    private static final UUID HEALTH_BOOST_ID =
            UUID.fromString("4a9c94e3-0a64-4546-98f9-c644913c5bac");

    public YossiikunPetHealthBoost() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;

        if (event.getEntity() instanceof TamableAnimal pet) {
            var attr = pet.getAttribute(Attributes.MAX_HEALTH);

            if (attr != null && attr.getModifier(HEALTH_BOOST_ID) == null) {
                attr.addPermanentModifier(new AttributeModifier(
                        HEALTH_BOOST_ID,
                        "Pet Health Boost",
                        1000.0,
                        AttributeModifier.Operation.ADDITION
                ));
            }
        }
    }
}
