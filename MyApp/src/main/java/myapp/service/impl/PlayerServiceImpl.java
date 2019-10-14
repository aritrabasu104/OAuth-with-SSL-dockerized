package myapp.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myapp.exceptions.InvalidMatchNameException;
import myapp.exceptions.InvalidPlayerException;
import myapp.model.Match;
import myapp.model.Player;
import myapp.model.PlayerStats;
import myapp.repository.MatchRepository;
import myapp.repository.PlayerRepository;
import myapp.repository.StatsRepository;
import myapp.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private StatsRepository statsRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Override
	public PlayerStats savePlayerStat(PlayerStats playerStats) {
		return statsRepository.save(playerStats);
	}

	@Override
	public List<PlayerStats> getPlayerStats(Long id) throws InvalidPlayerException {
		Player player;
		try {
		 player = playerRepository.findById(id).get();
		}catch (NoSuchElementException e) {
			throw new InvalidPlayerException();
		}
		return statsRepository.findByPlayer(player);
	}

	@Override
	public List<PlayerStats> getPlayerStatsForMatch(String matchName, Long timeInMilis)
			throws InvalidMatchNameException {
		Match match;
		try {
		match = matchRepository.findByMatchName(matchName).get(0);
		}catch (ArrayIndexOutOfBoundsException e) {
			throw new InvalidMatchNameException();
		}
		return null;
	}

}
