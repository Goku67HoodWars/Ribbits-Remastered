package com.yungnickyoung.minecraft.ribbits.util;

import net.minecraft.core.BlockPos;

import java.util.Optional;
import java.util.function.Predicate;

public final class BlockPosUtil {
    private BlockPosUtil() {}

    /**
     * Returns the closest {@link BlockPos} to {@code center} within the given horizontal/vertical ranges
     * that satisfies {@code predicate}, or empty if none match.
     *
     * <p>Reimplements the {@code BlockPos.findClosestMatch} helper removed in 26.3. The predicate is given
     * a shared mutable position for the scan, so it must read (not retain) it.
     */
    public static Optional<BlockPos> findClosestMatch(BlockPos center, int xzRange, int yRange, Predicate<BlockPos> predicate) {
        // ponytail: O(xz^2 * y) cuboid scan — fine for the small ranges these AI goals use; the goals throttle their calls.
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        BlockPos best = null;
        double bestDistSqr = Double.MAX_VALUE;
        for (int dy = -yRange; dy <= yRange; dy++) {
            for (int dx = -xzRange; dx <= xzRange; dx++) {
                for (int dz = -xzRange; dz <= xzRange; dz++) {
                    mutable.setWithOffset(center, dx, dy, dz);
                    if (predicate.test(mutable)) {
                        double distSqr = center.distSqr(mutable);
                        if (distSqr < bestDistSqr) {
                            bestDistSqr = distSqr;
                            best = mutable.immutable();
                        }
                    }
                }
            }
        }
        return Optional.ofNullable(best);
    }
}
