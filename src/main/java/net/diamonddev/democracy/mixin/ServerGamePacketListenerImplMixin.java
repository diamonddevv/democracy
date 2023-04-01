package net.diamonddev.democracy.mixin;

import net.minecraft.network.protocol.game.ServerboundVoteCastPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.voting.votes.ServerVoteStorage;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow public ServerPlayer player;
    @Shadow @Final private MinecraftServer server;

    @Inject(method = "handleVoteCast", at = @At(value = "INVOKE", target = "Lnet/minecraft/voting/votes/ServerVoteStorage$OptionAccess;addVotes(Lnet/minecraft/world/entity/Entity;I)V"))
    private void democracy$bribery(ServerboundVoteCastPacket serverboundVoteCastPacket, CallbackInfo ci) {
        if (player.getOffhandItem().getItem() == Items.GOLD_INGOT) {
            int i = player.getOffhandItem().getCount();
            ServerVoteStorage.OptionAccess optionAccess = this.server.getVoteStorage().getOptionAccess(serverboundVoteCastPacket.optionId());
            if (optionAccess != null) {
                optionAccess.addVotes(player, i);
                player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            }
        }
    }
}
