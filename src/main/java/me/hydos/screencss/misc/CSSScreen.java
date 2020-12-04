package me.hydos.screencss.misc;

import com.steadystate.css.parser.CSSOMParser;
import me.hydos.screencss.css.RealCssParser;
import me.hydos.screencss.xml.XmlScreenParser;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSStyleSheet;

import java.io.IOException;
import java.io.InputStreamReader;

public class CSSScreen extends Screen {
	public static final Logger LOGGER = LogManager.getLogger("Css Screen");
	public CSSStyleSheet stylesheet;
	public RealCssParser cssParser;
	public XmlScreenParser xmlParser;

	public CSSScreen(Identifier location) {
		super(new LiteralText("Css Screen"));
		try {
			InputSource cssInput = toSource("/assets/" + location.getNamespace() + "/" + location.getPath() + ".css");
			CSSOMParser cssParser = new CSSOMParser();
			this.xmlParser = new XmlScreenParser("/assets/" + location.getNamespace() + "/" + location.getPath() + ".xml");
			this.stylesheet = cssParser.parseStyleSheet(cssInput, null, null);
		} catch (IOException | CSSException e) {
			LOGGER.fatal("An Exception has occurred while trying to read the style sheet!");
			e.printStackTrace();
		}

	}

	private InputSource toSource(String path) {
		return new InputSource(new InputStreamReader(CSSScreen.class.getResourceAsStream(path)));
	}

	@Override
	public void init(MinecraftClient client, int width, int height) {
		super.init(client, width, height);
		xmlParser.parse(width);
		this.buttons = xmlParser.getButtons();
		cssParser = new RealCssParser(this.width, this.height, stylesheet);
		CSSSEvents.WINDOW_RESIZED.register(client2 -> cssParser.onScreenResize(client.currentScreen.width, client.currentScreen.height));
		cssParser.calculatePositions();
	}

	@Override
	public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
		renderBackground(matrices);
		super.render(matrices, mouseX, mouseY, delta);
		//TODO: mixin into all widgets and give them widget interface so i can put them in a list and render them
	}
}
