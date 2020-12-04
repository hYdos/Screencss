package me.hydos.screencss.mixin;

import me.hydos.screencss.misc.CSSScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.options.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {

	protected TitleScreenMixin(Text title) {
		super(title);
	}

	@Inject(method = "init", at = @At("TAIL"))
	private void addTestButton(CallbackInfo ci) {
		int j = this.height / 4 + 48;
		this.addButton(new ButtonWidget(
				this.width / 2 - 100,
				j + 72 + 40,
				200,
				20,
				new LiteralText("fuck"),
				(buttonWidget) -> this.client.openScreen(new CSSScreen(new Identifier("screencss", "screen/test_css_screen/test_css_screen"))))
		);
	}
}
