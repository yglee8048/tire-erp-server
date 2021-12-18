package com.minsoo.co.tireerpserver.repository.grid;

import com.minsoo.co.tireerpserver.model.TireStandardDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.minsoo.co.tireerpserver.entity.management.QBrand.brand;
import static com.minsoo.co.tireerpserver.entity.management.QPattern.pattern;
import static com.minsoo.co.tireerpserver.entity.tire.QTire.tire;

@Slf4j
@Repository
public class GridRepository {

    private final JPAQueryFactory queryFactory;

    @Autowired
    public GridRepository(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    public List<TireStandardDTO> findAllTireStandardDTOs() {
        return queryFactory
                .select(Projections.fields(TireStandardDTO.class,
                        tire.id,
                        tire.tireCode,
                        brand.id,
                        brand.name,
                        tire.onSale,
                        tire.width,
                        tire.flatnessRatio,
                        tire.inch,
                        tire.loadIndex,
                        tire.speedIndex,
                        pattern.id,
                        pattern.name,
                        tire.runFlat,
                        tire.sponge,
                        tire.sealing,
                        tire.oe,
                        tire.originalVehicle,
                        tire.countryOfManufacture,
                        tire.retailPrice,
                        tire.tireGroup,
                        tire.note,
                        pattern.season,
                        pattern.quietness,
                        pattern.rideQuality,
                        pattern.mileage,
                        pattern.handling,
                        pattern.breakingPower,
                        pattern.sports,
                        tire.pr,
                        tire.lr,
                        tire.tireRoId))
                .from(tire)
                .join(pattern).on(tire.pattern.eq(pattern))
                .join(brand).on(pattern.brand.eq(brand))
                .fetch();
    }
}
