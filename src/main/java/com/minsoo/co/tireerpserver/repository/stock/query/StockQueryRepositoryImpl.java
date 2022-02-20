package com.minsoo.co.tireerpserver.repository.stock.query;

import com.minsoo.co.tireerpserver.model.response.stock.StockGridResponse;
import com.minsoo.co.tireerpserver.repository.JPAQuerySnippet;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.minsoo.co.tireerpserver.entity.management.QWarehouse.warehouse;
import static com.minsoo.co.tireerpserver.entity.stock.QStock.stock;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StockQueryRepositoryImpl implements StockQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<StockGridResponse> findStockGridsByTireDotId(Long tireDotId) {
        return selectStockGrids()
                .where(stock.tireDot.id.eq(tireDotId))
                .fetch();
    }

    @Override
    public List<StockGridResponse> findOpenAndHasQuantityStockGridsByTireDotId(Long tireDotId) {
        return selectStockGrids()
                .where(stock.tireDot.id.eq(tireDotId),
                        stock.lock.eq(false),
                        stock.quantity.gt(0))
                .fetch();
    }

    private JPAQuery<StockGridResponse> selectStockGrids() {
        return queryFactory.select(JPAQuerySnippet.stockGridResponse(stock, warehouse))
                .from(stock)
                .join(warehouse).on(stock.warehouse.eq(warehouse));
    }
}
