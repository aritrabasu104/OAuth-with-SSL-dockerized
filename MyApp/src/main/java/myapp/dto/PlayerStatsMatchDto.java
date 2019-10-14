package myapp.dto;

public class PlayerStatsMatchDto {
	private String match;
	private Integer kills;
	private Integer score;
	/**
	 * @return the match
	 */
	public String getMatch() {
		return match;
	}
	/**
	 * @param match the match to set
	 */
	public void setMatch(String match) {
		this.match = match;
	}
	/**
	 * @return the kills
	 */
	public Integer getKills() {
		return kills;
	}
	/**
	 * @param kills the kills to set
	 */
	public void setKills(Integer kills) {
		this.kills = kills;
	}
	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		this.score = score;
	}
	
	
}
