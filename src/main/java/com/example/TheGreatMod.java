package com.example;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
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

    @Override
    public void onInitialize() {
    }
}