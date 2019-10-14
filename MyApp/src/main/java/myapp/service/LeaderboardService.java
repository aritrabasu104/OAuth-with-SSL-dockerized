package myapp.service;

import java.util.List;

import myapp.exceptions.InvalidMatchNameException;
import myapp.exceptions.InvalidPlayerException;
import myapp.model.PlayerStats;

public interface LeaderboardService {
	List<PlayerStats> getLeaderBoard(String matchName,Long timeInMillis) throws InvalidMatchNameException;

	List<PlayerStats> getAdjecentScoresForPlayer(Long playerId, String matchName) throws InvalidPlayerException, InvalidMatchNameException;
	
}
