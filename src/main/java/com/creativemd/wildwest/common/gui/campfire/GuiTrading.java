package com.creativemd.wildwest.common.gui.campfire;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.common.gui.GuiContainerSub;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.wildwest.common.block.tileentity.TileEntityTrading;

public class GuiTrading extends SubGuiTileEntity{

	public GuiTrading(TileEntity tileEntity) {
		super(tileEntity);
	}

	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(GuiContainerSub.background);
		GL11.glPushMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		RenderHelper2D.drawTexturedModalRect(32, 10, 194, 0, 25, 13);
		GL11.glPopMatrix();
		
		
	}

	@Override
	public void createControls() {
		
	}

}
