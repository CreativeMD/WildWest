package com.creativemd.wildwest.core;


import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;

import com.creativemd.wildwest.common.block.BlockCampfire;
import com.creativemd.wildwest.common.block.BlockLogSeat;
import com.creativemd.wildwest.common.block.BlockTrading;
import com.creativemd.wildwest.common.block.tileentity.TileEntityCampfire;
import com.creativemd.wildwest.common.block.tileentity.TileEntityTrading;
import com.creativemd.wildwest.common.item.ItemCowboyHat;
import com.creativemd.wildwest.common.item.ItemSombrero;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = WildWest.modid, version = WildWest.version, name = "Wild West")
public class WildWest {
	
	public static final String modid = "wildwest";
	public static final String version = "0.2";
	
	@SidedProxy(clientSide = "com.creativemd.wildwest.core.WildWestClient", serverSide = "com.creativemd.wildwest.core.WildWestServer")
	public static WildWestServer proxy;
	
	public static Item cowboyhat = new ItemCowboyHat("cowboyhat");
	public static Item sombrero = new ItemSombrero("sombrero");
	
	public static Block campfire = new BlockCampfire("WWCampfire");
	public static Block logSeat = new BlockLogSeat("WWLogSeat");
	public static Block trading = new BlockTrading("WWTrading");
	
	@EventHandler
    public void init(FMLInitializationEvent event)
	{
		GameRegistry.registerBlock(campfire, "campfire");
		GameRegistry.registerTileEntity(TileEntityCampfire.class, "WWCampfire");
		
		GameRegistry.registerBlock(logSeat, "logSeat");
		
		GameRegistry.registerBlock(trading, "trading");
		GameRegistry.registerTileEntity(TileEntityTrading.class, "WWTrading");
		
		GameRegistry.registerItem(cowboyhat, "cowboyhat");
		GameRegistry.registerItem(sombrero, "sombrero");
		proxy.loadSide();
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(campfire), new Object[]
				{
				"PWP", "WFW", "CCC", Character.valueOf('C'), Blocks.cobblestone, Character.valueOf('F'), Items.flint_and_steel, Character.valueOf('W'), Blocks.log, Character.valueOf('P'), Blocks.planks
				}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(cowboyhat), new Object[]
				{
				"LLL", "LDL", Character.valueOf('L'), Items.leather, Character.valueOf('D'), Items.diamond_helmet
				}));
		
		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(logSeat, 8), new Object[]
				{
				"LLL", Character.valueOf('L'), Blocks.log
				}));
	}

}
