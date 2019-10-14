package myapp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Formula;

@Entity
public class PlayerStats {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_id")
	private Match match;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id")
	private Player player;
	private Long kills;
	private Long score;
	@Formula("(SELECT RANK () OVER (  ORDER BY score desc ) rank_no FROM PlayerStats where match_id=id)")
	private Integer rank;
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
	 * @return the match
	 */
	public Match getMatch() {
		return match;
	}
	/**
	 * @param match the match to set
	 */
	public void setMatch(Match match) {
		this.match = match;
	}
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}
	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	/**
	 * @return the kills
	 */
	public Long getKills() {
		return kills;
	}
	/**
	 * @param kills the kills to set
	 */
	public void setKills(Long kills) {
		this.kills = kills;
	}
	/**
	 * @return the score
	 */
	public Long getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Long score) {
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
