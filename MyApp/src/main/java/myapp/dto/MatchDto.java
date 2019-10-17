package myapp.dto;

import java.util.List;
import java.util.Map;

import myapp.model.Player;
import myapp.model.PlayerStat;

public class MatchDto {

	private Long id;
	private List<PlayerStat> playerStat;
	private String matchName;
	private Map<Player,Integer> ranks;
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
	/**
	 * @return the matchName
	 */
	public String getMatchName() {
		return matchName;
	}
	/**
	 * @param matchName the matchName to set
	 */
	public void setMatchName(String matchName) {
		this.matchName = matchName;
	}
	/**
	 * @return the ranks
	 */
	public Map<Player, Integer> getRanks() {
		return ranks;
	}
	/**
	 * @param ranks the ranks to set
	 */
	public void setRanks(Map<Player, Integer> ranks) {
		this.ranks = ranks;
	}
	
	
}