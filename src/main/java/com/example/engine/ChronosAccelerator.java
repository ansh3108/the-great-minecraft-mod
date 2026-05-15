package com.example.engine;

import net.minecraft.util.math.BlockPos;

public class ChronosAccelerator {
    private int durationTicks;
    private int multiplier;
    private final BlockPos pos;

    public ChronosAccelerator(BlockPos pos, int durationTicks, int multiplier) {
        this.pos = pos;
        this.durationTicks = durationTicks;
        this.multiplier = multiplier;
    }

    public void upgrade() {
        if (this.multiplier < 32) {
            this.multiplier *= 2;
            this.durationTicks = 600; 
        }
    }

    public boolean tick() {
        this.durationTicks--;
        return this.durationTicks <= 0;
    }

    public int getMultiplier() {
        return this.multiplier;
    }
}

