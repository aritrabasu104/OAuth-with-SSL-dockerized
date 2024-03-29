package myapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import myapp.model.Player;
@Repository
public interface PlayerRepository extends CrudRepository<Player, Long> {

}
