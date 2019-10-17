package myapp.dto;

import java.util.List;

import myapp.model.PlayerStat;

public class PlayerDto {

    private Long id;
    private String userName;
    private List<PlayerStat> playerStat;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the playerStats
	 */
	public List<PlayerStat> getPlayerStat() {
		return playerStat;
	}

	/**
	 * @param playerStats the playerStats to set
	 */
	public void setPlayerStats(List<PlayerStat> playerStat) {
		this.playerStat = playerStat;
	}
	
}