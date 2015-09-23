package com.creativemd.wildwest.common.gui.campfire;

import java.util.ArrayList;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.tileentity.TileEntity;

import com.creativemd.creativecore.common.gui.SubGui;
import com.creativemd.creativecore.common.gui.SubGuiTileEntity;
import com.creativemd.creativecore.common.gui.controls.GuiControl;
import com.creativemd.creativecore.common.gui.controls.GuiTextfield;
import com.creativemd.wildwest.common.block.tileentity.TileEntityCampfire;

public class GuiCampfire extends SubGuiTileEntity{

	public GuiCampfire(TileEntityCampfire tileEntity) {
		super(tileEntity);
	}

	@Override
	public void drawOverlay(FontRenderer fontRenderer) {
		
	}

	@Override
	public void createControls() {
		
	}

}
