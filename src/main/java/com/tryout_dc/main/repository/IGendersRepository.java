package com.tryout_dc.main.repository;

import com.tryout_dc.main.models.GendersModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGendersRepository extends JpaRepository<GendersModel, Long> {
}
