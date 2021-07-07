package io.izzel.lightfall.client.mixin;

import net.minecraftforge.fml.network.NetworkInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = NetworkInstance.class, remap = false)
public class NetworkInstanceMixin {

    @Inject(method = "tryClientVersionOnServer", cancellable = true, at = @At(value = "INVOKE", target = "Ljava/util/function/Predicate;test(Ljava/lang/Object;)Z", shift = At.Shift.BEFORE))
    public void tryClientVersionOnServerTest(String clientVersion, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
