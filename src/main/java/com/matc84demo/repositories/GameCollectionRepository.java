package com.matc84demo.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.matc84demo.entities.GameCollection;
import com.matc84demo.entities.User;

public interface GameCollectionRepository extends JpaRepository<GameCollection, Long> {

	@Query("SELECT c FROM User u "
			+ "INNER JOIN GameCollection c ON c.creator = u "
			+ "WHERE c.id = :collecId AND u.id = :userId")
	GameCollection findByIds(Long userId, Long collecId);
	
	List<GameCollection> findByCreator(User creator);
}
