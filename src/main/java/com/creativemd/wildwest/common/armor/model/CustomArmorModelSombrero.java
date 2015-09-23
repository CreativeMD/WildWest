package com.creativemd.wildwest.common.armor.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class CustomArmorModelSombrero extends ModelBiped
{
	ModelRenderer box;
	ModelRenderer headbox;
	
	public CustomArmorModelSombrero(float expand)
	{
		super(expand, 0, 64*8, 64*8);
		//textureWidth = 64;
		//textureHeight = 32;
		
		
		box = new ModelRenderer(this, 0, 47*8);
		//box.addBox(0F, 0F, 0F, 16, 1, 16, expand/3);
		box.cubeList.add(new ModelCustomBox(box, 0, 47*8, 0F, 0F, 0F, 16, 1, 16, expand/3, 8));
		box.setRotationPoint(-8F, -7F, -8F);
		box.setTextureSize(64*8, 32*8);
		box.mirror = true;
		setRotation(box, 0F, 0F, 0F);
		
		headbox = new ModelRenderer(this, 32*8, 32*8);
		headbox.cubeList.add(new ModelCustomBox(headbox, 32*8, 32*8, 0F, 0F, 0F, 8, 3, 8, expand/3, 8));
		//headbox.addBox(0F, 0F, 0F, 8, 3, 8, expand/3);
		headbox.setRotationPoint(-4F, -10F, -4F);
		headbox.setTextureSize(64*8, 32*8);
		headbox.mirror = true;
		setRotation(headbox, 0F, 0F, 0F);
		
		this.bipedHead.addChild(box);
		this.bipedHead.addChild(headbox);
	}
	
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		//super.render(entity, f, f1, f2, f3, f4, f5);
	    setRotationAngles(f, f1, f2, f3, f4, f5, entity);
	    bipedHead.render(f5);
	}

	
	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

}
