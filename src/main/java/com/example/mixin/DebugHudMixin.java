package com.example.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.DebugHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugHud.class)
public abstract class DebugHudMixin {

    @Shadow protected abstract boolean shouldShowDebugHud();

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    public void renderBlueprintHud(DrawContext context, CallbackInfo ci) {
        if (!this.shouldShowDebugHud()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

        context.fill(0, 0, width, height, 0xDD000022); 

        String fpsData = client.getCurrentFps() + " FPS";
        context.drawText(client.textRenderer, fpsData, 15, 15, 0x00FFFF, false);

        if (client.getCameraEntity() != null) {
            String coords = String.format("XYZ: %.3f / %.5f / %.3f", 
                client.getCameraEntity().getX(), 
                client.getCameraEntity().getY(), 
                client.getCameraEntity().getZ());
            
            context.drawText(client.textRenderer, coords, 15, 30, 0x00FFFF, false);
        }

        ci.cancel();
    }
}