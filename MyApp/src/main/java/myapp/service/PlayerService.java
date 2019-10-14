package myapp.service;

import java.util.List;

import myapp.exceptions.InvalidMatchNameException;
import myapp.exceptions.InvalidPlayerException;
import myapp.model.PlayerStats;

public interface PlayerService {
	PlayerStats savePlayerStat(PlayerStats playerStats) throws InvalidMatchNameException;
	List<PlayerStats> getPlayerStats(Long playerId) throws InvalidPlayerException;
	List<PlayerStats> getPlayerStatsForMatch(String matchName,Long timeInMilis) throws InvalidMatchNameException;
}
