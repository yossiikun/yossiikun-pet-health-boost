package com.yossiikun.pethealthboost;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(YossiikunPetHealthBoost.MODID)
@EventBusSubscriber(modid = YossiikunPetHealthBoost.MODID)
public class YossiikunPetHealthBoost {



    public static final String MODID = "yossiikunpethealthboost";
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation HEALTH_BOOST_ID =
            ResourceLocation.fromNamespaceAndPath(MODID, "health_boost");

    public YossiikunPetHealthBoost() {

    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event){}

    @SubscribeEvent
    public static void onEntityJoin(EntityJoinLevelEvent event){


        if (event.getLevel().isClientSide()) return;

        if (event.getEntity() instanceof TamableAnimal pet) {

            var attr = pet.getAttribute(Attributes.MAX_HEALTH);

            if (attr != null && attr.getModifier(HEALTH_BOOST_ID) == null) {

                attr.addPermanentModifier(new AttributeModifier(
                        HEALTH_BOOST_ID,
                        1000.0,
                        AttributeModifier.Operation.ADD_VALUE
                ));

                pet.setHealth(pet.getMaxHealth());

                LOGGER.info("Boost applied to pet: {}", pet.getName().getString());
            }
        }
        }

}
