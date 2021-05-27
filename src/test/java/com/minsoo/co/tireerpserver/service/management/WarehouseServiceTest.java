package com.minsoo.co.tireerpserver.service.management;

import com.minsoo.co.tireerpserver.api.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.model.dto.management.warehouse.WarehouseRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.management.Warehouse;
import com.minsoo.co.tireerpserver.service.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.WAREHOUSE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class WarehouseServiceTest extends ServiceTest {

    @Autowired
    WarehouseService warehouseService;

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
}
