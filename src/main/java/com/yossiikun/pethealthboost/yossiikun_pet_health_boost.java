package com.yossiikun.pethealthboost;

import com.mojang.logging.LogUtils;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(yossiikun_pet_health_boost.MODID)
public class yossiikun_pet_health_boost {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "yossiikun_pet_health_boost";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final UUID HEALTH_BOOST_ID = UUID.fromString("4a9c94e3-0a64-4546-98f9-c644913c5bac");

    public yossiikun_pet_health_boost() {
        // 【修正1】重要：Forgeのイベントバスにこのクラスを登録する
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("yossiikun pet health boost : DONT DIE PET!!");
    }
    @SubscribeEvent
    public void onEntityJoin(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide()) return;
        if (event.getEntity() instanceof TamableAnimal pet) {
            var attr = pet.getAttribute(Attributes.MAX_HEALTH);
            if (attr != null && attr.getModifier(HEALTH_BOOST_ID) == null) {
                attr.addPermanentModifier(new AttributeModifier(
                        HEALTH_BOOST_ID, "Pet Health Boost", 1000.0, AttributeModifier.Operation.ADDITION));
                pet.setHealth(pet.getMaxHealth());

                LOGGER.info("Global Boost applied to: " + pet.getName().getString());
            }
    }
}
}
