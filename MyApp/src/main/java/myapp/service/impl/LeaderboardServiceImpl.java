package myapp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myapp.model.Player;
import myapp.model.PlayerStats;
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
	public List<PlayerStats> getLeaderBoard(String matchName, Long timeInMillis) {
		Map<Player,PlayerStats> map = new HashMap<Player,PlayerStats>();
		matchRepository.findByMatchName(matchName).stream().filter(item -> item.getStartTime().getTime() > timeInMillis)
				.forEach(match -> {
					statsRepository.findByMatch(match).forEach(stat->{
						PlayerStats temp = map.get(stat.getPlayer());
						if(Optional.ofNullable(temp).isPresent() && temp.getScore()<stat.getScore()){
							map.put(stat.getPlayer(), stat);
						}else
							map.put(stat.getPlayer(), stat);
					});;
				});
		return map.values().stream().limit(100).collect(Collectors.toList());
	}

	@Override
	public List<PlayerStats> getLeaderBoardForUser(Long playerId, String matchName) {
		if(Optional.ofNullable(matchName).isPresent() && matchName.trim().length()>0) {
			return statsRepository.findByPlayer(playerRepository.findById(playerId).get())
			.stream().filter(item->item.getMatch().getMatchName().equals(matchName)).collect(Collectors.toList());
		}
		return statsRepository.findByPlayer(playerRepository.findById(playerId).get())
				.stream().collect(Collectors.toList());
	}

}
