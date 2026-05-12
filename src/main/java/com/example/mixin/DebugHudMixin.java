package com.example.mixin;

import com.example.TheGreatMod;
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
        if (client.player == null) {
            return;
        }

        int cyan = 0xFF00E5FF;
        int xOffset = 25;
        int yOffset = 25;

        int panelBase = 0xBF101010;
        context.fill(xOffset - 8, yOffset - 8, xOffset + 200, yOffset + 75, panelBase);

        int panelBorderSheen = 0x30FFFFFF;
        context.fill(xOffset - 8, yOffset - 8, xOffset + 200, yOffset - 7, panelBorderSheen); 
        context.fill(xOffset - 8, yOffset - 8, xOffset - 7, yOffset + 75, panelBorderSheen); 
        context.fill(xOffset + 200, yOffset - 7, xOffset + 200 - 1, yOffset + 75, panelBorderSheen); 
        context.fill(xOffset - 8, yOffset + 75, xOffset + 200, yOffset + 75 - 1, panelBorderSheen); 

        int glassSheenDepth = 0x10FFFFFF;
        context.fill(xOffset - 8, yOffset - 8, xOffset + 200, yOffset + 75, glassSheenDepth);


        context.drawText(client.textRenderer, "Stats", xOffset, yOffset, cyan, false);
        context.drawText(client.textRenderer, "FPS: " + client.getCurrentFps(), xOffset, yOffset + 18, cyan, false);

        if (client.getCameraEntity() != null) {
            String coords = String.format("XYZ: %.3f / %.5f / %.3f", 
                client.getCameraEntity().getX(), 
                client.getCameraEntity().getY(), 
                client.getCameraEntity().getZ());
            context.drawText(client.textRenderer, coords, xOffset, yOffset + 32, cyan, false);
        }

        

        ci.cancel();
    }
}


