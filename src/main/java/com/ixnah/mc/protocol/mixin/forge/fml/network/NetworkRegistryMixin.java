package com.ixnah.mc.protocol.mixin.forge.fml.network;

import com.ixnah.mc.protocol.util.ValidateChannelsUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkInstance;
import net.minecraftforge.fml.network.NetworkRegistry;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Mixin(value = NetworkRegistry.class,remap = false)
public class NetworkRegistryMixin {

    @Inject(method = "validateChannels", locals = LocalCapture.CAPTURE_FAILEXCEPTION, at = @At(value = "RETURN", ordinal = 0))
    private static void saveInvalidChannels(Map<ResourceLocation, String> incoming, String originName,
                                            BiFunction<NetworkInstance, String, Boolean> testFunction,
                                            CallbackInfoReturnable<Boolean> cir,
                                            List<Pair<ResourceLocation, Boolean>> results) {
        ValidateChannelsUtil.saveLatestResults(results);
    }
}
