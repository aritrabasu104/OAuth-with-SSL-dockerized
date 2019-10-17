package myapp.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myapp.exceptions.InvalidMatchNameException;
import myapp.exceptions.InvalidPlayerException;
import myapp.exceptions.InvalidPlayerStatException;
import myapp.model.Match;
import myapp.model.Player;
import myapp.model.PlayerStat;
import myapp.repository.MatchRepository;
import myapp.repository.PlayerRepository;
import myapp.repository.StatsRepository;
import myapp.service.LeaderboardService;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {

	@Autowired
	private PlayerRepository playerRepository;

	@Autowired
	private StatsRepository statsRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Override
	public List<PlayerStat> getLeaderBoard(String matchName, Long timeInMillis) throws InvalidMatchNameException {
		Match match;
		try {
			match = matchRepository.findByMatchName(matchName).get();
		} catch (NoSuchElementException e) {
			throw new InvalidMatchNameException(matchName);
		}

		return statsRepository.findByMatch(match).parallelStream()
				.filter(item -> item.getStatTime().getTime() > timeInMillis)
				.sorted((item1, item2) -> item2.getScore() - item1.getScore()).limit(100).collect(Collectors.toList());
	}

	@Override
	public List<PlayerStat> getAdjecentScoresForPlayer(Long playerId, String matchName)
			throws InvalidPlayerException, InvalidMatchNameException, InvalidPlayerStatException {
		Match match;
		Player player;
		PlayerStat playerStat;
		try {
			match = matchRepository.findByMatchName(matchName).get();
		} catch (NoSuchElementException e) {
			throw new InvalidMatchNameException(matchName);
		}
		try {
			player = playerRepository.findById(playerId).get();
		} catch (NoSuchElementException e) {
			throw new InvalidPlayerException(playerId);
		}
		try {
			playerStat = statsRepository.findByMatchAndPlayer(match, player).get();
		} catch (NoSuchElementException e) {
			throw new InvalidPlayerStatException(playerId);
		}
		return statsRepository.findByMatch(match).parallelStream().filter(item->{
			return Math.abs(item.getRank()-playerStat.getRank())<3;	
		}).collect(Collectors.toList());
		
	}

}
