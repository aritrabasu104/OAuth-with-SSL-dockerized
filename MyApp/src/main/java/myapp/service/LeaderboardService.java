package myapp.service;

import java.util.List;

import myapp.model.PlayerStats;

public interface LeaderboardService {
	List<PlayerStats> getLeaderBoard(String matchName,Long timeInMillis);

	List<PlayerStats> getLeaderBoardForUser(Long playerId, String matchName);
	
}
