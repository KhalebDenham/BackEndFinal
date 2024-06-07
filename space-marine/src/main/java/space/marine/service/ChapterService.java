package space.marine.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import space.marine.controller.model.ChapterData;
import space.marine.controller.model.ChapterData.MarineData;
import space.marine.controller.model.ChapterData.ShipData;
import space.marine.dao.ChapterDao;
import space.marine.dao.MarineDao;
import space.marine.dao.ShipDao;
import space.marine.entity.Chapter;
import space.marine.entity.Marine;
import space.marine.entity.Ship;

@Service
public class ChapterService {

	@Autowired
	private ChapterDao chapterDao;

	@Autowired
	private MarineDao marineDao;

	@Autowired
	private ShipDao shipDao;

	@Transactional(readOnly = false)
	public ChapterData saveChapter(ChapterData chapterData) {
		Long chapterId = chapterData.getChapterId();
		Chapter dbChapter = findOrCreateChapter(chapterId); // grants a primary key to Chapter
		copyChapterFields(dbChapter, chapterData);

		return new ChapterData(chapterDao.save(dbChapter));
	}

	@Transactional(readOnly = false)
	public MarineData saveMarine(Long chapterId, MarineData marineData) {
		Chapter chapter = findChapterById(chapterId);
		Long marineId = marineData.getMarineId();
		Marine marine = findOrCreateMarine(chapterId, marineId);

		copyMarineFields(marine, marineData);

		marine.setChapter(chapter);
		chapter.getMarines().add(marine);
		Marine mDb = marineDao.save(marine);

		return new MarineData(mDb);
	}

	@Transactional(readOnly = false)
	public ShipData saveShip(Long marineId, ShipData marineShip) {
		Marine marine = findMarineById(marineId);
		Long shipId = marineShip.getShipId();
		Ship ship = findOrCreateShip(marineId, shipId);

		copyShipFields(ship, marineShip);
		marine.getShips().add(ship);

		Ship dbShip = shipDao.save(ship);

		return new ShipData(dbShip);
	}

	private Ship findOrCreateShip(Long marineId, Long shipId) {
		if (Objects.isNull(shipId)) {
			return new Ship();
		} else {
			return findShipById(marineId, shipId);
		}
	}

	private void copyShipFields(Ship ship, ShipData shipData) {
		ship.setShipId(shipData.getShipId());
		ship.setName(shipData.getName());

	}

	private void copyMarineFields(Marine marine, MarineData marineData) {
		marine.setMarineId(marineData.getMarineId());
		marine.setMarineName(marineData.getMarineName());
		marine.setWeapon(marineData.getWeapon());

	}

	private void copyChapterFields(Chapter chapter, ChapterData chapterData) {
		chapter.setChapterId(chapterData.getChapterId());
		chapter.setChapterName(chapterData.getChapterName());
		chapter.setTactics(chapterData.getTactics());
		chapter.setAlignment(chapterData.getAlignment());
		chapter.setDescription(chapterData.getDescription());
		chapter.setLeader(chapterData.getLeader());
		chapter.setHomeworld(chapterData.getHomeworld());

	}

	public Chapter findOrCreateChapter(Long chapterId) {
		Chapter chapter;
		if (Objects.isNull(chapterId)) {
			chapter = new Chapter();
		} else {
			chapter = findChapterById(chapterId);
		}

		return chapter;
	}

	private Marine findOrCreateMarine(Long chapterId, Long marineId) {
		if (Objects.isNull(marineId)) {
			return new Marine();
		} else {
			return findMarineInChapterById(chapterId, marineId);
		}
	}

	private Marine findMarineInChapterById(Long chapterId, Long marineId) {
		Marine marine = marineDao.findById(marineId).orElseThrow();

		if (marine.getChapter().getChapterId().equals(chapterId)) {
			return marine;
		} else {
			throw new IllegalArgumentException(chapterId + " does not match the given chapterId");
		}
	}

	private Ship findShipById(Long marineId, Long shipId) {
		Ship ship = shipDao.findById(shipId)
				.orElseThrow(() -> new NoSuchElementException("Ship with ID=" + shipId + " was not found"));
		boolean found = false;
		for (Marine marine : ship.getMarines()) {
			if (marine.getMarineId() == marineId) {
				found = true;
				break;
			}
		}
		if (!found) {
			throw new IllegalArgumentException(
					"The ship with Id=" + shipId + " is not a member of the pet store with ID=" + marineId);
		}
		return ship;
	}

	private Marine findMarineById(Long marineId) {
		Marine marine = marineDao.findById(marineId).orElseThrow();

		if (marine.getMarineId() != null) {
			return marine;
		} else {
			throw new IllegalArgumentException(marineId + " does not match the given marineId");
		}
	}

	private Chapter findChapterById(Long chapterId) {
		return chapterDao.findById(chapterId)
				.orElseThrow(() -> new NoSuchElementException("Chapter with ID=" + chapterId + " was not found."));
	}

	@Transactional(readOnly = true)
	public MarineData retrieveMarineById(Long marineId) {
		if (findMarineById(marineId) == null) {
			throw new NoSuchElementException("No matching marine with id of " + marineId);
		} else {
			MarineData marineData = new MarineData(findMarineById(marineId));
			return marineData;
		}

	}

	@Transactional(readOnly = true)
	public List<MarineData> retrieveAllMarines() {
		List<Marine> marines = marineDao.findAll();
		List<MarineData> response = new LinkedList<>();

		for (Marine marine : marines) {
			response.add(new MarineData(marine));
		}

		return response;
	}

	@Transactional(readOnly = false)
	public void deleteMarineById(Long marineId) {
		Marine marine = findMarineById(marineId);
		marineDao.delete(marine);

	}

	public void deleteChapterById(Long chapterId) {
		Chapter chapter = findChapterById(chapterId);
		chapterDao.delete(chapter);

	}

	@Transactional(readOnly = true)
	public ChapterData retrieveChapterById(Long chapterId) {
		if (findChapterById(chapterId) == null) {
			throw new NoSuchElementException("No matching chaoter with id of " + chapterId);
		} else {
			ChapterData chapterData = new ChapterData(findChapterById(chapterId));
			return chapterData;
		}

	}

	@Transactional(readOnly = true)
	public List<ChapterData> retrieveAllChapters() {
		List<Chapter> chapters = chapterDao.findAll();
		List<ChapterData> response = new LinkedList<>();

		for (Chapter chapter : chapters) {
			response.add(new ChapterData(chapter));
		}

		return response;
	}

}
