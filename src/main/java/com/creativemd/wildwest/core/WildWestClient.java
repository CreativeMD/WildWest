package com.creativemd.wildwest.core;

import com.creativemd.wildwest.client.RenderCustom;
import com.creativemd.wildwest.common.armor.model.CustomArmorModelCowboy;
import com.creativemd.wildwest.common.armor.model.CustomArmorModelSombrero;
import com.creativemd.wildwest.common.block.tileentity.TileEntityCampfire;
import com.creativemd.wildwest.common.block.tileentity.TileEntityTrading;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class WildWestClient extends WildWestServer{
	
	public static CustomArmorModelCowboy cowboyhatmodel;
	public static CustomArmorModelSombrero sombreromodel;
	public static int customRenderID;
	
	public static RenderCustom customRenderer;
	
	@Override
	public void loadSide()
	{
		customRenderer = new RenderCustom();
		customRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(customRenderID, customRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampfire.class, customRenderer);
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTrading.class, customRenderer);
		
		cowboyhatmodel = new CustomArmorModelCowboy(1.0F);
		sombreromodel = new CustomArmorModelSombrero(1.0F);
	}
}
