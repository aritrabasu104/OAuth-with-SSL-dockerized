package myapp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Valid
@Entity
@Table(indexes = { @Index(name = "ind_match", columnList = "match_id", unique = false),
		@Index(name = "ind_player_id", columnList = "player_id", unique = false) })
public class PlayerStats {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Valid
	@NotBlank
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "match_id", nullable = false)
	private Match match;

	@Valid
	@NotBlank
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "player_id", nullable = false)
	private Player player;
	private Integer kills;
	private Integer score;
	private Date statTime;

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
	 * @return the statTime
	 */
	public Date getStatTime() {
		return statTime;
	}

	/**
	 * @param statTime the statTime to set
	 */
	public void setStatTime(Date statTime) {
		this.statTime = new Date();
	}

}
