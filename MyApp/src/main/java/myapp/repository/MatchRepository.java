package myapp.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import myapp.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

	List<Match> findByMatchName(String matchName);
}
