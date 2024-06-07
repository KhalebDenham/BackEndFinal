package space.marine.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import space.marine.entity.Chapter;

public interface ChapterDao extends JpaRepository<Chapter, Long> {

}
