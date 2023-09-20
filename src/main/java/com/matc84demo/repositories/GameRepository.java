package com.matc84demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matc84demo.entities.Game;

public interface GameRepository extends JpaRepository<Game,Long> {

}
