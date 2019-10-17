package myapp.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myapp.exceptions.InvalidMatchNameException;
import myapp.exceptions.InvalidPlayerException;
import myapp.model.Match;
import myapp.model.Player;
import myapp.model.PlayerStat;
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
	public Player createPlayer(String playerName) {
		Player player = new Player();
		player.setUserName(playerName);
		return playerRepository.save(player);
	}

	@Override
	public Match createMatch(@NotBlank String matchName) {
		Match match = new Match();
		match.setMatchName(matchName);
		return matchRepository.save(match);
	}

	@Override
	public PlayerStat savePlayerStat(PlayerStat playerStat)
			throws InvalidMatchNameException, InvalidPlayerException {
		Match match;
		Player player;
		try {
			match = matchRepository.findByMatchName(playerStat.getMatch().getMatchName()).get();

		} catch (NoSuchElementException e) {
			throw new InvalidMatchNameException(playerStat.getMatch().getMatchName());
		}
		try {
			player = playerRepository.findById(playerStat.getPlayer().getId()).get();
		} catch (NoSuchElementException e) {
			throw new InvalidPlayerException(playerStat.getPlayer().getId());
		}

		playerStat.setMatch(match);
		int rank;
		Optional<PlayerStat> playerStatExisting = statsRepository.findByMatchAndPlayer(match, player);
		if (playerStatExisting.isPresent() && playerStatExisting.get().getScore() < playerStat.getScore()) {
			statsRepository.delete(playerStatExisting.get());
			rank = updateRanksAndGet(match, playerStat);
			playerStat.setRank(rank);
			statsRepository.save(playerStat);
			return playerStat;
		} else if (!playerStatExisting.isPresent()) {
			rank=updateRanksAndGet(match,playerStat);
			playerStat.setRank(rank);
			statsRepository.save(playerStat);
			return playerStat;
		}
		return playerStatExisting.get();
	}
	
	private int updateRanksAndGet(Match match, PlayerStat playerStat) {
		int s = ((Long)match.getPlayerStats().parallelStream().filter(item->item.getScore()>playerStat.getScore()).count()).intValue();
		
		match.getPlayerStats().parallelStream().filter(item->item.getScore() <= playerStat.getScore()).sorted((item1, item2) -> item2.getScore() - item1.getScore()).forEach(item->{
			item.setRank(item.getRank()+1);
		});
		matchRepository.save(match);
		return s+1;
	}

	@Override
	public List<PlayerStat> getPlayerStats(Long playerId) throws InvalidPlayerException {
		Player player;
		try {
			player = playerRepository.findById(playerId).get();
		} catch (NoSuchElementException e) {
			throw new InvalidPlayerException(playerId);
		}
		return statsRepository.findByPlayer(player);
	}

	@Override
	public List<PlayerStat> getPlayerStatsForMatch(String matchName, Long timeInMilis)
			throws InvalidMatchNameException {
		Match match;
		try {
			match = matchRepository.findByMatchName(matchName).get();
		} catch (NoSuchElementException e) {
			throw new InvalidMatchNameException(matchName);
		}
		return statsRepository.findByMatch(match).stream().filter(stat -> stat.getStatTime().getTime() > timeInMilis)
				.collect(Collectors.toList());
	}
}
