package net.chamosmp.chamoparty.api.storage;

import net.chamosmp.chamoparty.zcore.utils.storage.Saveable;

public interface StorageManager extends Saveable {

	Storage getStorage();
	
	IStorage getIStorage();
	
}
