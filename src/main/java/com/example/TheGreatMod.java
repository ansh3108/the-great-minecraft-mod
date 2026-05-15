package com.example;

import com.example.engine.ChronosEngine;
import com.example.item.TimeInABottleItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import com.mojang.serialization.Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TheGreatMod implements ModInitializer {
    public static final String MOD_ID = "the-great-mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final AttachmentType<Long> PASSIVE_TIME_ATTACHMENT = AttachmentRegistry.createPersistent(
            Identifier.of(MOD_ID, "passive_time"),
            Codec.LONG
    );

    public static final RegistryKey<Item> BOTTLE_KEY = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, "time_in_a_bottle"));
    
    public static final Item TIME_IN_A_BOTTLE = new TimeInABottleItem(new Item.Settings().registryKey(BOTTLE_KEY).maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registries.ITEM, BOTTLE_KEY.getValue(), TIME_IN_A_BOTTLE);
        ChronosEngine.initialize();
        LOGGER.info("Chronos Engine booted up.");
    }
}

