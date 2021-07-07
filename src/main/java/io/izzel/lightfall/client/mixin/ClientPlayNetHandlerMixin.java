package io.izzel.lightfall.client.mixin;

import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.util.SearchTreeManager;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.STagsListPacket;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.util.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayNetHandler.class)
public class ClientPlayNetHandlerMixin {

    @Shadow private ITagCollectionSupplier networkTagManager;
    @Shadow private @Final NetworkManager netManager;
    @Shadow private Minecraft client;

    @Inject(method = "handleTags", cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION, at = @At(value = "INVOKE", target = "Lnet/minecraft/network/NetworkManager;closeChannel(Lnet/minecraft/util/text/ITextComponent;)V", shift = At.Shift.BEFORE))
    public void handleMissingTags(STagsListPacket packetIn, CallbackInfo ci, ITagCollectionSupplier itagcollectionsupplier, boolean vanillaConnection, Multimap<ResourceLocation, ResourceLocation> multimap) {
        net.minecraftforge.common.ForgeTagHandler.resetCachedTagCollections(true, vanillaConnection);
        itagcollectionsupplier = ITagCollectionSupplier.reinjectOptionalTags(itagcollectionsupplier);
        this.networkTagManager = itagcollectionsupplier;
        if (!this.netManager.isLocalChannel()) {
            itagcollectionsupplier.updateTags();
        }

        this.client.getSearchTree(SearchTreeManager.TAGS).recalculate();
        ci.cancel();
    }
}
