package myapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import myapp.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

	Optional<Match> findByMatchName(String matchName);
}
