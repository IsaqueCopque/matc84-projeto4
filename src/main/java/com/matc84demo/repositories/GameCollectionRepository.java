package com.matc84demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matc84demo.entities.GameCollection;

public interface GameCollectionRepository extends JpaRepository<GameCollection, Long> {

}
