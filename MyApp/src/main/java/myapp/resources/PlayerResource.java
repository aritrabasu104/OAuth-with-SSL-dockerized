package myapp.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import myapp.model.PlayerStats;
import myapp.service.LeaderboardService;
import myapp.service.PlayerService;

@RestController
public class PlayerResource {

	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private LeaderboardService leaderboardService;
	
	@PostMapping("/playerStat")
    public ResponseEntity<?> savePlayeStat(@RequestBody PlayerStats playerStats) {
       try {
		return ResponseEntity.ok().body(playerService.savePlayerStat(playerStats));
       }catch (DataAccessException e) {
    	   return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
	
	@GetMapping("/LeaderBoard")
    public ResponseEntity<?> savePlayeStat(@RequestParam String matchName, @RequestParam(required = false) Long timeInMillis) {
       try {
		return ResponseEntity.ok().body(leaderboardService.getLeaderBoard(matchName, timeInMillis));
       }catch (DataAccessException e) {
    	   return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
	@GetMapping("/LeaderBoard/{id}")
    public ResponseEntity<?> savePlayeStat(@RequestParam Long playerId, @RequestParam(required = false) String matchName) {
       try {
		return ResponseEntity.ok().body(leaderboardService.getLeaderBoardForUser(playerId,matchName));
       }catch (DataAccessException e) {
    	   return ResponseEntity.badRequest().body(e.getMessage());
       }
    }
}
