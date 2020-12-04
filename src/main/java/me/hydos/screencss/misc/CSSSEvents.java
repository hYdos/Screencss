package me.hydos.screencss.misc;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public class CSSSEvents {
	public static final Event<ClientLifecycleEvents.ClientStarted> WINDOW_RESIZED = EventFactory.createArrayBacked(ClientLifecycleEvents.ClientStarted.class, (callbacks) -> (client) -> {
		for (ClientLifecycleEvents.ClientStarted callback : callbacks) {
			callback.onClientStarted(client);
		}
	});
}
