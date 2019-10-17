package myapp.service;

import java.util.List;

import myapp.exceptions.InvalidMatchNameException;
import myapp.exceptions.InvalidPlayerException;
import myapp.exceptions.InvalidPlayerStatException;
import myapp.model.PlayerStat;

public interface LeaderboardService {
	List<PlayerStat> getLeaderBoard(String matchName,Long timeInMillis) throws InvalidMatchNameException;

	List<PlayerStat> getAdjecentScoresForPlayer(Long playerId, String matchName) throws InvalidPlayerException, InvalidMatchNameException, InvalidPlayerStatException;
	
}
