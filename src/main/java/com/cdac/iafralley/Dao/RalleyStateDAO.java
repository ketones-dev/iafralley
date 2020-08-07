package com.cdac.iafralley.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cdac.iafralley.entity.RalleyStates;

@Repository
public interface RalleyStateDAO extends JpaRepository<RalleyStates, Long> {

}
