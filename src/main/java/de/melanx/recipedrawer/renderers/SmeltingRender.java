package de.melanx.recipedrawer.renderers;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.melanx.recipedrawer.IRecipeRender;
import de.melanx.recipedrawer.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SmeltingRender implements IRecipeRender<FurnaceRecipe> {

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("minecraft", "textures/gui/container/furnace.png");

    @Override
    public Class<FurnaceRecipe> getRecipeClass() {
        return FurnaceRecipe.class;
    }

    @Nullable
    @Override
    public IRecipeType<? super FurnaceRecipe> getRecipeType() {
        return IRecipeType.SMELTING;
    }

    @Override
    public int getRecipeWidth() {
        return 90;
    }

    @Override
    public int getRecipeHeight() {
        return 62;
    }

    @Override
    public void render(FurnaceRecipe recipe, MatrixStack matrixStack, IRenderTypeBuffer buffer) {
        SmeltingRender.render((AbstractCookingRecipe) recipe, matrixStack, buffer);
        RenderHelper.renderItem(matrixStack, buffer, new ItemStack(Items.FURNACE), 5, 41);
    }

    public static void render(AbstractCookingRecipe recipe, MatrixStack matrixStack, IRenderTypeBuffer buffer) {
        RenderHelper.renderBackground(BACKGROUND_TEXTURE, matrixStack, buffer, 51, 12, 90, 62);
        matrixStack.push();
        matrixStack.translate(6, 25, 10);
        RenderHelper.renderBackground(BACKGROUND_TEXTURE, matrixStack, buffer, 176, 0, 14, 14);
        matrixStack.pop();
        RenderHelper.renderItem(matrixStack, buffer, recipe.getRecipeOutput(), 65, 23);
        RenderHelper.renderIngredient(matrixStack, buffer, recipe.getIngredients().get(0), 5, 5);
        String timeText = "Time: " + (BigDecimal.valueOf(recipe.getCookTime() / 20d).setScale(2, RoundingMode.HALF_UP).toPlainString()) + "s";
        Minecraft.getInstance().fontRenderer.drawString(matrixStack, timeText, 26, 48, 0x000000);
    }
}