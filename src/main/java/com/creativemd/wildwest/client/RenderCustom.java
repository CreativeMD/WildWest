package com.creativemd.wildwest.client;

import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import com.creativemd.creativecore.client.block.BlockRenderHelper;
import com.creativemd.creativecore.client.rendering.RenderHelper2D;
import com.creativemd.creativecore.client.rendering.RenderHelper3D;
import com.creativemd.creativecore.common.utils.CubeObject;
import com.creativemd.wildwest.common.block.BlockCustomRenderer;
import com.creativemd.wildwest.core.WildWestClient;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderCustom extends TileEntitySpecialRenderer implements ISimpleBlockRenderingHandler {
	
	public ArrayList<CubeObject> getCubes(Block block, IBlockAccess world, int x, int y, int z)
	{
		if(block instanceof BlockCustomRenderer)
		{
			ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
			cubes.addAll(((BlockCustomRenderer) block).getCubes(world, x, y, z));
			
			if(world != null)
				for (int i = 0; i < cubes.size(); i++)
					cubes.set(i, CubeObject.rotateCube(cubes.get(i), ((BlockCustomRenderer) block).getDirection(world.getBlockMetadata(x, y, z))));
			
			return cubes;
		}else{
			ArrayList<CubeObject> cubes = new ArrayList<CubeObject>();
			cubes.add(new CubeObject());
			return cubes;
		}
	}
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) {
		BlockRenderHelper.renderInventoryCubes(renderer, getCubes(block, null, 0, 0, 0), block, metadata);
		//RenderHelper2D.renderInventoryCubes(renderer, getCubes(block, null, 0, 0, 0), block, metadata);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		renderer.renderAllFaces = true;
		//renderer.partialRenderBounds = true;
		//RenderHelper3D.renderWorldCubes(renderer, block, world.getBlockMetadata(x, y, z), getCubes(block, world, x, y, z), x, y, z);
		BlockRenderHelper.renderCubes(world, getCubes(block, world, x, y, z), x, y, z, block, renderer, null);
		renderer.renderAllFaces = false;
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId) {
		return true;
	}

	@Override
	public int getRenderId() {
		return WildWestClient.customRenderID;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double x, double y, double z, float f1) {
		Block block = te.getBlockType();
		if(block instanceof BlockCustomRenderer)
			((BlockCustomRenderer) block).renderTileEntity(te, x, y, z, f1);
	}


}
