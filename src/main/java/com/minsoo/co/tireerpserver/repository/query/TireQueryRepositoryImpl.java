package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.api.error.errors.CanNotDeleteException;
import com.minsoo.co.tireerpserver.model.entity.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.minsoo.co.tireerpserver.model.entity.QPurchase.*;
import static com.minsoo.co.tireerpserver.model.entity.QStock.*;
import static com.minsoo.co.tireerpserver.model.entity.QTire.*;
import static com.minsoo.co.tireerpserver.model.entity.QTireDot.*;
import static com.minsoo.co.tireerpserver.model.entity.QTireMemo.*;

@Repository
public class TireQueryRepositoryImpl implements TireQueryRepository {

    private final JPAQueryFactory queryFactory;

    public TireQueryRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * Tire 하위의 Dot 를 검증 후 삭제한다.
     * - Dot 와 관련된 재고의 수량이 1개 이상이면 삭제할 수 없다.
     * - Dot 가 삭제될 때 관련된 매입 내역이 함께 삭제된다.
     * - Dot 가 삭제될 때 관련된 매출 내역이 함께 삭제된다. //TODO
     * Tire 하위의 Memo 가 함께 삭제된다.
     */
    @Override
    public void deleteTire(Tire target) {
        // dot 삭제
        if (!CollectionUtils.isEmpty(target.getTireDots())) {
            deleteTireDots(new ArrayList<>(target.getTireDots()));
        }
        // memo 삭제
        if (!CollectionUtils.isEmpty(target.getTireMemos())) {
            deleteTireMemos(new ArrayList<>(target.getTireMemos()));
        }
        // tire 삭제
        queryFactory
                .delete(tire)
                .where(tire.eq(target))
                .execute();
    }

    private void deleteTireDots(List<TireDot> tireDots) {
        // 재고 검증 및 삭제
        deleteStockByTireDots(tireDots);
        // 매입 내역 삭제
        deletePurchases(tireDots);
        // dot 삭제
        queryFactory
                .delete(tireDot)
                .where(tireDot.in(tireDots))
                .execute();
    }

    private void deleteTireMemos(List<TireMemo> tireMemos) {
        queryFactory
                .delete(tireMemo)
                .where(tireMemo.in(tireMemos))
                .execute();
    }

    private void deleteStockByTireDots(List<TireDot> tireDots) {
        // 재고가 남아있으면 삭제할 수 없다.
        Optional<Long> sum = Optional.ofNullable(
                queryFactory
                        .select(stock.quantity.sum())
                        .from(stock)
                        .where(stock.tireDot.in(tireDots))
                        .fetchOne());

        if (sum.isPresent()) {
            if (sum.get() > 0) {
                throw new CanNotDeleteException("해당 타이어에 대한 재고가 남아있어 삭제할 수 없습니다.");
            } else {
                // 재고가 0개인 경우 해당 재고를 삭제한다.
                queryFactory
                        .delete(stock)
                        .where(stock.tireDot.in(tireDots))
                        .execute();
            }
        }
    }

    private void deletePurchases(List<TireDot> tireDots) {
        queryFactory
                .delete(purchase)
                .where(purchase.tireDot.in(tireDots))
                .execute();
    }
}
