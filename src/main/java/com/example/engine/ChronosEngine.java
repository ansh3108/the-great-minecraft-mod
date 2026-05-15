package com.example.engine;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChronosEngine {
    private static final Map<ServerWorld, Map<BlockPos, ChronosAccelerator>> ACCELERATORS = new HashMap<>();

    public static void initialize() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            Map<BlockPos, ChronosAccelerator> worldAccelerators = ACCELERATORS.get(world);
            if (worldAccelerators == null || worldAccelerators.isEmpty()) return;

            Iterator<Map.Entry<BlockPos, ChronosAccelerator>> iterator = worldAccelerators.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<BlockPos, ChronosAccelerator> entry = iterator.next();
                BlockPos pos = entry.getKey();
                ChronosAccelerator accelerator = entry.getValue();

                tickBlock(world, pos, accelerator.getMultiplier());

                if (world.getTime() % 4 == 0) {
                    world.spawnParticles(ParticleTypes.ENCHANT, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, accelerator.getMultiplier(), 0.3, 0.3, 0.3, 0.1);
                }

                if (accelerator.tick()) {
                    iterator.remove();
                }
            }
        });
    }

    public static void addAccelerator(ServerWorld world, BlockPos pos) {
        ACCELERATORS.computeIfAbsent(world, k -> new HashMap<>());
        Map<BlockPos, ChronosAccelerator> worldMap = ACCELERATORS.get(world);

        if (worldMap.containsKey(pos)) {
            worldMap.get(pos).upgrade();
        } else {
            worldMap.put(pos, new ChronosAccelerator(pos, 600, 2));
        }
    }

    public static int getMultiplier(ServerWorld world, BlockPos pos) {
        Map<BlockPos, ChronosAccelerator> worldMap = ACCELERATORS.get(world);
        if (worldMap != null && worldMap.containsKey(pos)) {
            return worldMap.get(pos).getMultiplier();
        }
        return 1;
    }

    @SuppressWarnings("unchecked")
    private static void tickBlock(ServerWorld world, BlockPos pos, int extraTicks) {
        BlockState state = world.getBlockState(pos);
        BlockEntity entity = world.getBlockEntity(pos);

        for (int i = 0; i < extraTicks; i++) {
            if (entity != null && state.getBlock() instanceof BlockEntityProvider provider) {
                BlockEntityTicker<BlockEntity> ticker = (BlockEntityTicker<BlockEntity>) provider.getTicker(world, state, entity.getType());
                if (ticker != null) {
                    ticker.tick(world, pos, state, entity);
                }
            } else if (state.hasRandomTicks()) {
                if (world.random.nextInt(1365) == 0) {
                    state.randomTick(world, pos, world.random);
                }
            }
        }
    }
}


