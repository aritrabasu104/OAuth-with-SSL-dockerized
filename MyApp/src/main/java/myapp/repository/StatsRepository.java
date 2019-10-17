package myapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import myapp.model.Match;
import myapp.model.Player;
import myapp.model.PlayerStat;

@Repository
public interface StatsRepository extends CrudRepository<PlayerStat, Long> {

	List<PlayerStat> findByMatch(Match match);

	List<PlayerStat> findByPlayer(Player player);
	
	Optional<PlayerStat> findByMatchAndPlayer(Match match, Player player);
}
