package space.marine.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import space.marine.controller.model.ChapterData;
import space.marine.controller.model.ChapterData.MarineData;
import space.marine.controller.model.ChapterData.ShipData;
import space.marine.service.ChapterService;

@RestController
@RequestMapping("/space_marine")
@Slf4j
public class MarineController {

	@Autowired
	private ChapterService chapterService;

	@PostMapping("/chapter")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ChapterData createChapter(@RequestBody ChapterData chapterData) {
		log.info("Creating chapter{}", chapterData);

		return chapterService.saveChapter(chapterData);
	}

	@GetMapping("/chapter/{chapterId}")
	public ChapterData retrieveChapterById(@PathVariable Long chapterId) {
		log.info("Retrieving chapter {}", chapterId);
		return chapterService.retrieveChapterById(chapterId);

	}

	@PutMapping("/chapter/{chapterId}")
	public ChapterData updateChapter(@PathVariable Long chapterId, @RequestBody ChapterData chapterData) {
		chapterData.setChapterId(chapterId);
		log.info("Updating chapter {}", chapterData);
		return chapterService.saveChapter(chapterData);
	}

	@GetMapping("/chapter")
	public List<ChapterData> retrieveAllChapters() {
		log.info("Retrieving all chapters");
		return chapterService.retrieveAllChapters();
	}

	@PostMapping("/chapter/{chapterId}/marine")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MarineData addMarineToChapter(@PathVariable Long chapterId, @RequestBody MarineData marineData) {
		log.info("Adding marine {} to chapter {}", marineData, chapterId);

		return chapterService.saveMarine(chapterId, marineData);

	}

	@GetMapping("/marine")
	public List<MarineData> retrieveAllMarines() {
		log.info("Retrieve all marines called.");
		return chapterService.retrieveAllMarines();

	}

	@GetMapping("/marine/{marineId}")
	public MarineData retrieveMarineById(@PathVariable Long marineId) {
		log.info("Retrieving marine with ID={}", marineId);
		return chapterService.retrieveMarineById(marineId);
	}

	@PostMapping("/marine/{marineId}/ship")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ShipData addShipToMarine(@PathVariable Long marineId, @RequestBody ShipData shipData) {

		log.info("Adding ship {} to marine {}", shipData, marineId);

		return chapterService.saveShip(marineId, shipData);

	}

	@DeleteMapping("/chapter/{chapterId}")
	public Map<String, String> deleteChapterById(@PathVariable Long chapterId) {
		log.info("Attempting to delete pet store with id {}", chapterId);
		chapterService.deleteChapterById(chapterId);
		return Map.of("message", "Deletion of chapter with Id " + chapterId + " is complete.");

	}
}
