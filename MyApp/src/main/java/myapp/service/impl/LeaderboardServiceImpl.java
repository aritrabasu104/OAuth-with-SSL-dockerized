package myapp.service.impl;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myapp.exceptions.InvalidMatchNameException;
import myapp.exceptions.InvalidPlayerException;
import myapp.model.Match;
import myapp.model.Player;
import myapp.model.PlayerStats;
import myapp.repository.MatchRepository;
import myapp.repository.StatsRepository;
import myapp.service.LeaderboardService;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {

	@Autowired
	private StatsRepository statsRepository;

	@Autowired
	private MatchRepository matchRepository;

	@Override
	public List<PlayerStats> getLeaderBoard(String matchName, Long timeInMillis) throws InvalidMatchNameException {
		Map<Player, PlayerStats> map = new ConcurrentHashMap<>();
		Match match;
		try {
			// finding matches that started after a given date
			match = matchRepository.findByMatchName(matchName).parallelStream().findFirst().get();
		} catch (NoSuchElementException e) {
			throw new InvalidMatchNameException();
		}
		// for match find the best stat of the player
		statsRepository.findByMatch(match).parallelStream().filter(item -> item.getStatTime().getTime() > timeInMillis)
				.forEach(stat -> {
					PlayerStats temp = map.get(stat.getPlayer());
					if (Optional.ofNullable(temp).isPresent() && temp.getScore() < stat.getScore()) {
						map.put(stat.getPlayer(), stat);
					} else
						map.put(stat.getPlayer(), stat);
				});

		// return top 100 stats after sorting by score
		return map.values().stream().sorted((item1, item2) -> item2.getScore() - item1.getScore()).limit(100)
				.collect(Collectors.toList());
	}

	@Override
	public List<PlayerStats> getAdjecentScoresForPlayer(Long playerId, String matchName)
			throws InvalidPlayerException, InvalidMatchNameException {
		Map<Player, PlayerStats> map = new ConcurrentHashMap<>();
		Match match;
		try {
			// finding matches that started after a given date
			match = matchRepository.findByMatchName(matchName).parallelStream().findFirst().get();
		} catch (NoSuchElementException e) {
			throw new InvalidMatchNameException();
		}
		// for match find the best stat of the player
		statsRepository.findByMatch(match).parallelStream().forEach(stat -> {
			PlayerStats temp = map.get(stat.getPlayer());
			if (Optional.ofNullable(temp).isPresent() && temp.getScore() < stat.getScore()) {
				map.put(stat.getPlayer(), stat);
			} else
				map.put(stat.getPlayer(), stat);
		});
		List<PlayerStats> list = map.values().stream().sorted((item1, item2) -> item2.getScore() - item1.getScore())
				.collect(Collectors.toList());
		PlayerStats playerStats;
		try {
			playerStats = list.parallelStream().filter(item -> item.getPlayer().getId() == playerId).findFirst().get();
		} catch (NoSuchElementException e) {
			throw new InvalidPlayerException();
		}
		int index = list.indexOf(playerStats);
		int fromIndex = index > 2 ? index - 2 : 0;
		int toIndex = list.size() > (fromIndex + 5) ? fromIndex : list.size();
		return list.subList(fromIndex, toIndex);
	}

}
