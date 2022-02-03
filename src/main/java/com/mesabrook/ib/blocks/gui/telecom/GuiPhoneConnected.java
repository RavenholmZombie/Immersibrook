package com.mesabrook.ib.blocks.gui.telecom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import com.mesabrook.ib.net.telecom.DisconnectCallPacket;
import com.mesabrook.ib.net.telecom.MergeCallPacket;
import com.mesabrook.ib.util.handlers.ClientSideHandlers.TelecomClientHandlers;
import com.mesabrook.ib.util.handlers.PacketHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GuiPhoneConnected extends GuiPhoneBase {

	private String toNumber;
	private boolean isConferenceSubCall = false; 
	private boolean mergeable = false;
	public GuiPhoneConnected(ItemStack phoneStack, EnumHand hand, String toNumber, boolean isConferenceSubCall, boolean mergeable) {
		super(phoneStack, hand);
		this.toNumber = toNumber;
		this.isConferenceSubCall = isConferenceSubCall;
		this.mergeable = mergeable;
	}

	@Override
	protected String getInnerTextureFileName() {
		return "app_screen.png";
	}
	
	public boolean isConferenceSubCall() {
		return isConferenceSubCall;
	}

	public boolean isMergeable() {
		return mergeable;
	}

	@Override
	public void initGui() {
		super.initGui();
		
		ImageButton endCall = new ImageButton(0, INNER_X + (INNER_TEX_WIDTH / 2) - 16, INNER_Y + 150, 32, 32, "numcallend.png", 32, 32);
		buttonList.add(endCall);
		
		ImageButton addCall = new ImageButton(1, INNER_X + INNER_TEX_WIDTH / 2 - 16 - 32 - 4, INNER_Y + 150, 32, 32, "numaddcall.png", 32, 32);
		addCall.visible = !isConferenceSubCall();
		buttonList.add(addCall);
		
		ImageButton mergeCall = new ImageButton(2, INNER_X+ INNER_TEX_WIDTH / 2 + 16 + 4, INNER_Y + 150, 32, 32, "btn_mergecall.png", 32, 32);
		mergeCall.visible = isMergeable();
		buttonList.add(mergeCall);
	}

	@Override
	protected void doDraw(int mouseX, int mouseY, float partialticks) {
		LocalDateTime dateTime = TelecomClientHandlers.callStartsByPhone.get(getCurrentPhoneNumber());
		if (dateTime == null)
		{
			dateTime = LocalDateTime.now();
			TelecomClientHandlers.callStartsByPhone.put(getCurrentPhoneNumber(), dateTime);
		}
		long currentTime = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
		long callStartTime = dateTime.toEpochSecond(ZoneOffset.UTC);
		long elapsedSeconds = currentTime - callStartTime;
		long hoursElapsed = elapsedSeconds / 3600;
		elapsedSeconds -= hoursElapsed * 3600;
		long minutesElapsed = elapsedSeconds / 60;
		elapsedSeconds -= minutesElapsed * 60;
		
		String callTime = "";
		if (hoursElapsed > 0)
		{
			callTime += Long.toString(hoursElapsed) + ":";
		}
		
		callTime += String.format("%02d:%02d", minutesElapsed, elapsedSeconds);
		
		GlStateManager.scale(dLittleFont, dLittleFont, dLittleFont);
		drawCenteredString(fontRenderer, callTime, scale(INNER_X + (INNER_TEX_WIDTH / 2), uLittleFont), scale(INNER_Y + 21, uLittleFont), 0xFFFFFF);
		GlStateManager.scale(uLittleFont, uLittleFont, uLittleFont);
		
		GlStateManager.scale(uBigFont, uBigFont, uBigFont);
		drawCenteredString(fontRenderer, getFormattedPhoneNumber(toNumber), scale(INNER_X + (INNER_TEX_WIDTH / 2), dBigFont), scale(INNER_Y + 50, dBigFont), 0xFFFFFF);
		GlStateManager.scale(dBigFont, dBigFont, dBigFont);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);
		
		if (button.id == 0)
		{
			DisconnectCallPacket disconnect = new DisconnectCallPacket();
			disconnect.fromNumber = getCurrentPhoneNumber();
			PacketHandler.INSTANCE.sendToServer(disconnect);
		}
		else if (button.id == 1)
		{
			GuiPhoneCall callScreen = new GuiPhoneCall(phoneStack, hand);
			Minecraft.getMinecraft().displayGuiScreen(callScreen);
		}
		else if (button.id == 2)
		{
			MergeCallPacket merge = new MergeCallPacket();
			merge.forNumber = getCurrentPhoneNumber();
			PacketHandler.INSTANCE.sendToServer(merge);
		}
	}
}