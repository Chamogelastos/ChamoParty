package net.chamosmp.chamoparty.zcore.enums;

public enum Folder {

	PLAYERS,

	;
	

	public String toFolder(){
		return name().toLowerCase();
	}
	
}
