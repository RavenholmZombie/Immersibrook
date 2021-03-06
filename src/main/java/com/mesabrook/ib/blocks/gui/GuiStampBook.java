package com.mesabrook.ib.blocks.gui;

import com.mesabrook.ib.blocks.container.ContainerStampBook;
import com.mesabrook.ib.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;

public class GuiStampBook extends GuiContainer {

	ContainerStampBook stampBookContainer;
	public GuiStampBook(ContainerStampBook container)
	{
		super(container);
		stampBookContainer = container;
		
		xSize = 220;
		ySize = 235;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		// Inventory Text
		this.fontRenderer.drawString("Extra Stamps", 131, 8, 10813440, false);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		drawDefaultBackground();
		Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(Reference.MODID + ":textures/gui/stamp_book.png"));
		drawModalRectWithCustomSizedTexture(guiLeft, guiTop, 0, 0, xSize, ySize, xSize, ySize);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		renderHoveredToolTip(mouseX, mouseY);
	}
}
