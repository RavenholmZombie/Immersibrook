package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.net.telecom.PhoneQueryResponsePacket.ResponseTypes;
import com.mesabrook.ib.telecom.CallManager;
import com.mesabrook.ib.util.handlers.PacketHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.Tuple;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PhoneQueryPacket implements IMessage {

	public String forNumber;
	public int clientHandlerCode;
	@Override
	public void fromBytes(ByteBuf buf) {
		forNumber = ByteBufUtils.readUTF8String(buf);
		clientHandlerCode = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, forNumber);
		buf.writeInt(clientHandlerCode);
	}

	public static class Handler implements IMessageHandler<PhoneQueryPacket, IMessage>
	{

		@Override
		public IMessage onMessage(PhoneQueryPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(PhoneQueryPacket message, MessageContext ctx)
		{
			EntityPlayerMP player = ctx.getServerHandler().player;
			CallManager manager = CallManager.instance();
			Tuple<ResponseTypes, String> queryResponse = manager.phoneQuery(player, message.forNumber);
			
			boolean isConferenceSubCall = false;
			String callOrigin = "";
			if (queryResponse.getFirst() != ResponseTypes.idle)
			{
				CallManager.Call call = manager.getCall(message.forNumber);
				isConferenceSubCall = call.doesConferenceSubCallsContainOrigin(message.forNumber);
			}
			
			PhoneQueryResponsePacket responsePacket = new PhoneQueryResponsePacket();
			responsePacket.forNumber = message.forNumber;
			responsePacket.responseType = queryResponse.getFirst();
			responsePacket.otherNumber = queryResponse.getSecond();
			responsePacket.clientHandlerCode = message.clientHandlerCode;
			responsePacket.isConferenceSubCall = isConferenceSubCall;
			responsePacket.isMergeable = isConferenceSubCall;
			PacketHandler.INSTANCE.sendTo(responsePacket, player);
		}
	}
}
