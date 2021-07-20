package com.ixnah.mc.protocol.util;

import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ValidateChannelsUtil {

    private ValidateChannelsUtil() {}

    private static final AtomicReference<List<Pair<ResourceLocation, Boolean>>> latestResults = new AtomicReference<>(Collections.emptyList());

    public static void saveLatestResults(List<Pair<ResourceLocation, Boolean>> results) {
        latestResults.set(results);
    }

    public static List<Pair<ResourceLocation, Boolean>> getLatestResults() {
        return latestResults.getAndSet(Collections.emptyList());
    }
}
