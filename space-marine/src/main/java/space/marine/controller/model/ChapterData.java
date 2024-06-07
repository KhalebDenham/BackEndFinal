package space.marine.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import space.marine.entity.Chapter;
import space.marine.entity.Marine;
import space.marine.entity.Ship;

@Data
@NoArgsConstructor
public class ChapterData {

	private Long chapterId;

	private String chapterName;
	private String homeworld;
	private String tactics;
	private String leader;
	private String alignment;
	private String description;

	private Set<MarineData> marines = new HashSet<>();

	public ChapterData(Chapter chapter) {
		this.chapterId = chapter.getChapterId();
		this.alignment = chapter.getAlignment();
		this.chapterName = chapter.getChapterName();
		this.description = chapter.getDescription();
		this.leader = chapter.getLeader();
		this.homeworld = chapter.getHomeworld();
		this.tactics = chapter.getTactics();

		for (Marine marine : chapter.getMarines()) {
			this.marines.add(new MarineData(marine));
		}

	}

	public ChapterData(Long chapterId, String chapterName, String homeworld, String tactics, String leader,
			String alignment, String description) {

		this.chapterId = chapterId;

		this.chapterName = chapterName;
		this.homeworld = homeworld;
		this.tactics = tactics;
		this.leader = leader;
		this.alignment = alignment;
		this.description = description;

	}

	public Chapter toChapter() {
		Chapter chapter = new Chapter();

		chapter.setChapterId(chapterId);
		chapter.setAlignment(alignment);
		chapter.setChapterName(chapterName);
		chapter.setDescription(description);
		chapter.setHomeworld(homeworld);
		chapter.setLeader(leader);
		chapter.setTactics(tactics);

		for (MarineData marineData : marines) {
			chapter.getMarines().add(marineData.toMarine());
		}

		return chapter;
	}

	@Data
	@NoArgsConstructor
	public static class MarineData {
		private Long marineId;
		private String marineName;
		private String weapon;
		private Set<ShipData> ships = new HashSet<>();

		public MarineData(Marine marine) {
			this.marineId = marine.getMarineId();
			this.marineName = marine.getMarineName();
			this.weapon = marine.getWeapon();

			for (Ship ship : marine.getShips()) {
				this.ships.add(new ShipData(ship));
			}
		}

		public Marine toMarine() {
			Marine marine = new Marine();

			marine.setMarineId(marineId);
			marine.setMarineName(marineName);
			marine.setWeapon(weapon);

			for (ShipData shipData : ships) {
				marine.getShips().add(shipData.toShip());
			}

			return marine;
		}
	}

	@Data
	@NoArgsConstructor
	public static class ShipData {
		private Long shipId;
		private String name;

		public ShipData(Ship ship) {
			this.shipId = ship.getShipId();
			this.name = ship.getName();
		}

		public Ship toShip() {
			Ship ship = new Ship();

			ship.setShipId(shipId);
			ship.setName(name);

			return ship;
		}
	}

}
