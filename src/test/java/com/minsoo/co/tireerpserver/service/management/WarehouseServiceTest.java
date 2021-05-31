package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.api.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.entity.management.Warehouse;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.WAREHOUSE;
import static org.assertj.core.api.Assertions.*;

public class WarehouseServiceTest extends ServiceTest {

    /**
     * 창고 이름은 중복일 수 없다.
     */
    @Test
    @DisplayName("창고 생성")
    void create() {
        // GIVEN
        WarehouseRequest warehouseRequest = WAREHOUSE("창고 테스트");
        WarehouseRequest duplicateRequest = WAREHOUSE("창고 테스트");

        // WHEN
        Warehouse warehouse = warehouseService.create(warehouseRequest);

        // THEN
        assertThat(warehouse.getName()).isEqualTo("창고 테스트");
        assertThatThrownBy(() -> warehouseService.create(duplicateRequest))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("창고 수정")
    void update() {
        // GIVEN
        Warehouse warehouse1 = warehouseService.create(WAREHOUSE("창고-1"));
        Warehouse warehouse2 = warehouseService.create(WAREHOUSE("창고-2"));

        // WHEN
        Warehouse updated = warehouseService.update(warehouse1.getId(), WAREHOUSE("창고"));
        clear();

        // THEN
        assertThat(updated.getName()).isEqualTo("창고");
        assertThatThrownBy(() -> warehouseService.update(warehouse2.getId(), WAREHOUSE("창고")))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("창고 삭제")
    void removeById() {
        // GIVEN
        Warehouse warehouse = warehouseService.create(WAREHOUSE("창고-1"));

        // WHEN
        warehouseService.removeById(warehouse.getId());

        // THEN
        assertThatThrownBy(() -> warehouseService.findById(warehouse.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}
