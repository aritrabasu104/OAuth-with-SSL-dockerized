package myapp.dto;

public class PlayerStatsDashboardDto {
	private String userName;
	private Integer kills;
	private Integer score;
	private Integer rank;
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
	/**
	 * @return the rank
	 */
	public Integer getRank() {
		return rank;
	}
	/**
	 * @param rank the rank to set
	 */
	public void setRank(Integer rank) {
		this.rank = rank;
	}
	
}
