package myapp.resources;

import java.util.Date;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import myapp.dto.MatchDto;
import myapp.dto.PlayerDto;
import myapp.dto.PlayerStatsDashboardDto;
import myapp.dto.PlayerStatsMatchDto;
import myapp.exceptions.InvalidMatchNameException;
import myapp.exceptions.InvalidPlayerException;
import myapp.exceptions.InvalidPlayerStatException;
import myapp.model.Match;
import myapp.model.Player;
import myapp.model.PlayerStat;
import myapp.service.LeaderboardService;
import myapp.service.PlayerService;

@Validated
@RestController
public class PlayerResource {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PlayerService playerService;

	@Autowired
	private LeaderboardService leaderboardService;

	@ApiOperation(value = "creates player in Database with PlayerName")
	@PostMapping("/createPlayer")
	public ResponseEntity<?> createPlayer(@NotBlank @RequestParam String playerName) {
		try {
			return ResponseEntity.ok()
					.body(convertPlayerToPlayerDto(playerService.createPlayer(playerName)));
		} catch (DataAccessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} 
	}
	
	@ApiOperation(value = "creates match in Database")
	@PostMapping("/createMatch")
	public ResponseEntity<?> createMatch(@NotBlank @RequestParam String matchName) {
		try {
			return ResponseEntity.ok()
					.body(convertMatchToMatchDto(playerService.createMatch(matchName)));
		} catch (DataAccessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} 
	}
	
	@ApiOperation(value = "saves player statistics")
	@PostMapping("/playerStat")
	public ResponseEntity<?> savePlayeStat(@RequestParam Long id,
			@RequestBody PlayerStatsMatchDto playerStatsMatchDto) {
		try {
			return ResponseEntity.ok()
					.body(convertModelToMatchStatsDto(playerService.savePlayerStat(convertDtoToMatchStatsModel(playerStatsMatchDto, id))));
		} catch (DataAccessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InvalidMatchNameException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InvalidPlayerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ApiOperation(value = "Returns leaderboard for a specific matchName within the time limit")
	@GetMapping("/LeaderBoard")
	public ResponseEntity<?> getLeaderBoardForMatchName(@Valid @NotBlank @RequestParam String matchName,
			@Min(value = 0, message = "timeInMillis should be positive number") @RequestParam Long timeInMillis) {
		try {
			return ResponseEntity.ok().body(leaderboardService.getLeaderBoard(matchName, timeInMillis).parallelStream()
					.map(this::convertModelToDashboardStatsDto).collect(Collectors.toList()));
		} catch (DataAccessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InvalidMatchNameException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ApiOperation(value = "Returns leaderboard for a player filtering on the match")
	@GetMapping("/LeaderBoard/{id}")
	public ResponseEntity<?> getAdjecentScores(
			@NotNull @Min(value = 1, message = "id should be positive number") @PathVariable("id") Long playerId,
			@NotBlank @RequestParam(required = false) String matchName) {
		try {
			return ResponseEntity.ok().body(leaderboardService.getAdjecentScoresForPlayer(playerId, matchName)
					.parallelStream().map(this::convertModelToDashboardStatsDto).collect(Collectors.toList()));
		} catch (DataAccessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InvalidPlayerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InvalidMatchNameException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InvalidPlayerStatException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ApiOperation(value = "Returns stats for a logged in player")
	@GetMapping("/playersStats/{id}")
	public ResponseEntity<?> getPlayerStat(
			@NotNull @Min(value = 1, message = "id should be positive number") @PathVariable("id") Long playerId) {
		try {
			return ResponseEntity.ok().body(playerService.getPlayerStats(playerId).parallelStream()
					.map(this::convertModelToMatchStatsDto).collect(Collectors.toList()));
		} catch (DataAccessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InvalidPlayerException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@ApiOperation(value = "Returns player stats for a specific matchName within the time limit")
	@GetMapping("/playersStats")
	public ResponseEntity<?> getLPlayerStatForMatchName(@Valid @NotBlank @RequestParam String matchName,
			@Min(value = 0, message = "timeInMillis should be positive number") @RequestParam Long timeInMillis) {
		try {
			return ResponseEntity.ok().body(playerService.getPlayerStatsForMatch(matchName, timeInMillis)
					.parallelStream().map(this::convertModelToMatchStatsDto).collect(Collectors.toList()));
		} catch (DataAccessException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (InvalidMatchNameException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	private PlayerStatsDashboardDto convertModelToDashboardStatsDto(PlayerStat playerStat) {
		PlayerStatsDashboardDto playerStatsDashboardDto = modelMapper.map(playerStat, PlayerStatsDashboardDto.class);
		playerStatsDashboardDto.setUserName(playerStat.getPlayer().getUserName());
		return playerStatsDashboardDto;
	}

	private PlayerStatsMatchDto convertModelToMatchStatsDto(PlayerStat playerStats) {
		PlayerStatsMatchDto playerStatsMatchDto = modelMapper.map(playerStats, PlayerStatsMatchDto.class);
		playerStatsMatchDto.setMatch(playerStats.getMatch().getMatchName());
		return playerStatsMatchDto;
	}

	private PlayerStat convertDtoToMatchStatsModel(PlayerStatsMatchDto playerStatsMatchDto, Long id) {
		PlayerStat playerStats = modelMapper.map(playerStatsMatchDto, PlayerStat.class);
		Match match = new Match();
		match.setMatchName(playerStatsMatchDto.getMatch());
		playerStats.setStatTime(new Date());
		Player player = new Player();
		player.setId(id);
		playerStats.setMatch(match);
		playerStats.setPlayer(player);
		return playerStats;
	}

	private MatchDto convertMatchToMatchDto(Match match) {
		return modelMapper.map(match, MatchDto.class);
	}

	private PlayerDto convertPlayerToPlayerDto(Player player) {
		return modelMapper.map(player, PlayerDto.class);
	}

}
