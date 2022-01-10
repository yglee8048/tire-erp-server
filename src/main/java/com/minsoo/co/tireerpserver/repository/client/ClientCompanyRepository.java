package com.minsoo.co.tireerpserver.repository.client;

import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClientCompanyRepository extends JpaRepository<ClientCompany, Long> {

    @Query("select com from ClientCompany com join fetch com.rank r")
    List<ClientCompany> findAllFetchRank();

    @EntityGraph(attributePaths = "rank")
    Optional<ClientCompany> findFetchRankById(Long clientCompanyId);
}
