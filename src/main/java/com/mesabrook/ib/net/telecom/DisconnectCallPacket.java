package com.mesabrook.ib.net.telecom;

import com.mesabrook.ib.telecom.CallManager;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DisconnectCallPacket implements IMessage {

	public String fromNumber;
	@Override
	public void fromBytes(ByteBuf buf) {
		fromNumber = ByteBufUtils.readUTF8String(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, fromNumber);
	}
	
	public static class Handler implements IMessageHandler<DisconnectCallPacket, IMessage>
	{

		@Override
		public IMessage onMessage(DisconnectCallPacket message, MessageContext ctx) {
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> handle(message, ctx));
			return null;
		}

		private void handle(DisconnectCallPacket message, MessageContext ctx)
		{
			CallManager manager = CallManager.instance();
			CallManager.Call callToDisconnect = manager.getCall(message.fromNumber);
			
			if (callToDisconnect == null)
			{
				return;
			}
			
			callToDisconnect.disconnectDest(message.fromNumber);
		}
	}

}
