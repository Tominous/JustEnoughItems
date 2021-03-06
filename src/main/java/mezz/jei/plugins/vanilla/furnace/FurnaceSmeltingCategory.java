package mezz.jei.plugins.vanilla.furnace;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.util.ResourceLocation;

import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.config.Constants;
import mezz.jei.util.Translator;

public class FurnaceSmeltingCategory extends FurnaceRecipeCategory<FurnaceRecipe> {
	private final IDrawable background;
	private final IDrawable icon;
	private final String localizedName;

	public FurnaceSmeltingCategory(IGuiHelper guiHelper) {
		super(guiHelper);
		background = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 114, 82, 54);
		icon = guiHelper.createDrawableIngredient(new ItemStack(Blocks.FURNACE));
		localizedName = Translator.translateToLocal("gui.jei.category.smelting");
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public IDrawable getIcon() {
		return icon;
	}

	@Override
	public void setIngredients(FurnaceRecipe recipe, IIngredients ingredients) {
		ingredients.setInputIngredients(recipe.getIngredients());
		ingredients.setOutput(VanillaTypes.ITEM, recipe.getRecipeOutput());
	}

	@Override
	public void draw(FurnaceRecipe recipe, double mouseX, double mouseY) {
		animatedFlame.draw(1, 20);
		arrow.draw(24, 18);

		float experience = recipe.getExperience();
		if (experience > 0) {
			String experienceString = Translator.translateToLocalFormatted("gui.jei.category.smelting.experience", experience);
			Minecraft minecraft = Minecraft.getInstance();
			FontRenderer fontRenderer = minecraft.fontRenderer;
			int stringWidth = fontRenderer.getStringWidth(experienceString);
			fontRenderer.drawString(experienceString, background.getWidth() - stringWidth, 0, 0xFF808080);
		}
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public ResourceLocation getUid() {
		return VanillaRecipeCategoryUid.FURNACE;
	}

	@Override
	public Class<? extends FurnaceRecipe> getRecipeClass() {
		return FurnaceRecipe.class;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, FurnaceRecipe recipe, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 0, 0);
		guiItemStacks.init(outputSlot, false, 60, 18);

		guiItemStacks.set(ingredients);
	}
}
