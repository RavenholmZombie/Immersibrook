package rz.mesabrook.wbtc.blocks.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import rz.mesabrook.wbtc.util.Reference;

import java.net.URI;
import java.net.URISyntaxException;

public class GuiAboutImmersibrook extends GuiScreen
{
    private GuiButton closeButton;
    private GuiButton githubButton;
    private int backgroundWidth  = 250;
    private int backgroundHeight  = 260;

    @Override
    public void initGui()
    {
        // Close Button
        this.buttonList.add(this.closeButton = new GuiButton(0, (this.width - this.backgroundWidth + 365) / 2, (this.height + this.backgroundHeight - 155) / 2, this.backgroundWidth - 190, 20, "Close"));

        // GitHub Button
        this.buttonList.add(this.githubButton = new GuiButton(1, (this.width - this.backgroundWidth + 27) / 2, (this.height + this.backgroundHeight - 155) / 2, this.backgroundWidth - 185, 20, "GitHub"));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        drawDefaultBackground();
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2 - this.closeButton.height;

        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(new ResourceLocation(Reference.MODID, "textures/gui/about.png"));
        this.drawTexturedModalRect(x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        // Text
        drawCenteredString(fontRenderer, "Version " + Reference.VERSION, (this.width + 0) / 2, (this.height + this.backgroundHeight - 410) / 2, 111111);
        drawCenteredString(fontRenderer, Reference.UPDATE_NAME, (this.width + 0) / 2, (this.height + this.backgroundHeight - 330) / 2, 16777215);

        drawCenteredString(fontRenderer, "Developers:", (this.width + 0) / 2, (this.height + this.backgroundHeight - 280) / 2, 16777215);
        drawCenteredString(fontRenderer, "RavenholmZombie", (this.width + 0) / 2, (this.height + this.backgroundHeight - 250) / 2, 16777215);
        drawCenteredString(fontRenderer, "CSX8600", (this.width + 0) / 2, (this.height + this.backgroundHeight - 220) / 2, 16777215);

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if(button.id == 0)
        {
            this.mc.player.closeScreen();
        }

        if(button.id == 1)
        {
            try {
                openWebLink(new URI("https://github.com/RavenholmZombie/Immersibrook/wiki"));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    private void openWebLink(URI url)
    {
        try
        {
            Class<?> oclass = Class.forName("java.awt.Desktop");
            Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null);
            oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, url);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
