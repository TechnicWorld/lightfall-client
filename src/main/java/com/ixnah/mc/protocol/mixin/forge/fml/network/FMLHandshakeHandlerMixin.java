package com.ixnah.mc.protocol.mixin.forge.fml.network;

import com.ixnah.mc.protocol.util.ValidateChannelsUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.FMLHandshakeHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = FMLHandshakeHandler.class, remap = false)
public class FMLHandshakeHandlerMixin {

    @Redirect(method = "handleServerModListOnClient", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkManager;closeChannel(Lnet/minecraft/util/text/ITextComponent;)V"))
    private void printInvalidChannels(NetworkManager networkManager, ITextComponent message) {
        StringBuilder builder = new StringBuilder("Connection closed - mismatched mod channel list: ");
        for (Pair<ResourceLocation, Boolean> result : ValidateChannelsUtil.getLatestResults()) {
            builder.append("\n").append(result.getKey().toString());
        }
        networkManager.closeChannel(new StringTextComponent(builder.toString()));
    }
}
