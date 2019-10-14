package myapp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myapp.model.PlayerStats;
import myapp.repository.StatsRepository;
import myapp.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService{

	@Autowired
	private StatsRepository statsRepository;

	@Override
	public PlayerStats savePlayerStat(PlayerStats playerStats) {
		return statsRepository.save(playerStats);
	}

}
