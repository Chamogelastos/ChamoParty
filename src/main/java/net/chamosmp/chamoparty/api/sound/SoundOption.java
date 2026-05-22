package net.chamosmp.chamoparty.api.sound;

import org.bukkit.entity.Entity;

import net.chamosmp.chamoparty.api.enums.XSound;

public interface SoundOption {

	/**
	 * 
	 * @return sound
	 */
	public XSound getSound();
	
	/**
	 * 
	 * @return pitch
	 */
	public float getPitch();
	
	/**
	 * 
	 * @return volume
	 */
	public float getVolume();
	
	/**
	 * 
	 * @param entity
	 */
	void play(Entity entity);
	
}
