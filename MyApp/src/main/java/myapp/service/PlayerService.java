package myapp.service;

import java.util.List;

import javax.validation.constraints.NotBlank;

import myapp.exceptions.InvalidMatchNameException;
import myapp.exceptions.InvalidPlayerException;
import myapp.model.Match;
import myapp.model.Player;
import myapp.model.PlayerStat;

public interface PlayerService {
	Player createPlayer(String playerName);
	PlayerStat savePlayerStat(PlayerStat playerStats) throws InvalidMatchNameException, InvalidPlayerException;
	List<PlayerStat> getPlayerStats(Long playerId) throws InvalidPlayerException;
	List<PlayerStat> getPlayerStatsForMatch(String matchName,Long timeInMilis) throws InvalidMatchNameException;
	Match createMatch(@NotBlank String matchName);
}
