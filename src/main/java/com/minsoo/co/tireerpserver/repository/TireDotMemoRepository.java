package com.minsoo.co.tireerpserver.repository;

import com.minsoo.co.tireerpserver.model.entity.TireDotMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TireDotMemoRepository extends JpaRepository<TireDotMemo, Long> {

    List<TireDotMemo> findAllByTireDot_Id(Long tireDotId);
}
