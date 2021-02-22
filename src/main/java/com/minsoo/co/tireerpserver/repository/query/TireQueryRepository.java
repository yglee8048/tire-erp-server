package com.minsoo.co.tireerpserver.repository.query;

import com.minsoo.co.tireerpserver.model.entity.Tire;

public interface TireQueryRepository {

    /**
     * Tire 하위의 Dot 를 검증 후 삭제한다.
     * - Dot 와 관련된 재고의 수량이 1개 이상이면 삭제할 수 없다.
     * - Dot 가 삭제될 때 관련된 매입 내역이 함께 삭제된다.
     * Tire 하위의 Memo 가 함께 삭제된다.
     */
    void deleteTire(Tire tire);
}
