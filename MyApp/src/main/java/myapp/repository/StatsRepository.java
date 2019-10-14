package myapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import myapp.model.Match;
import myapp.model.Player;
import myapp.model.PlayerStats;

@Repository
public interface StatsRepository extends CrudRepository<PlayerStats, Long> {

	List<PlayerStats> findByMatch(Match match);

	List<PlayerStats> findByPlayer(Player player);
}
