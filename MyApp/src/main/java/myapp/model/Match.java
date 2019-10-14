package myapp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(indexes = { @Index(name = "ind_matchName", columnList = "matchName", unique = true) })
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "match")
	private List<PlayerStats> playerStats;
	private String matchName;
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
	 * @return the playerStats
	 */
	public List<PlayerStats> getPlayerStats() {
		return playerStats;
	}

	/**
	 * @param playerStats the playerStats to set
	 */
	public void setPlayerStats(List<PlayerStats> playerStats) {
		this.playerStats = playerStats;
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