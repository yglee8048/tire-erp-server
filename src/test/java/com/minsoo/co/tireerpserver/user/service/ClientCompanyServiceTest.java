package com.minsoo.co.tireerpserver.user.service;

import com.minsoo.co.tireerpserver.ServiceTest;
import com.minsoo.co.tireerpserver.shared.error.exceptions.AlreadyExistException;
import com.minsoo.co.tireerpserver.shared.error.exceptions.NotFoundException;
import com.minsoo.co.tireerpserver.user.entity.ClientCompany;
import com.minsoo.co.tireerpserver.user.model.client.company.ClientCompanyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.minsoo.co.tireerpserver.utils.RequestBuilder.CLIENT_COMPANY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ClientCompanyServiceTest extends ServiceTest {

    @Autowired
    private ClientCompanyService clientCompanyService;

    @Test
    @DisplayName("고객사 생성")
    void create() {
        // GIVEN
        ClientCompanyRequest request = CLIENT_COMPANY("고객사");
        ClientCompanyRequest duplicate = CLIENT_COMPANY("고객사");

        // WHEN
        ClientCompany clientCompany = clientCompanyService.create(request);

        // THEN
        assertThat(clientCompany.getName()).isEqualTo("고객사");
        assertThatThrownBy(() -> clientCompanyService.create(duplicate)).isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("고객사 수정")
    void update() {
        // GIVEN
        ClientCompany clientCompany = clientCompanyService.create(CLIENT_COMPANY("고객사"));
        ClientCompany duplicate = clientCompanyService.create(CLIENT_COMPANY("중복"));

        // WHEN
        ClientCompany updated = clientCompanyService.update(clientCompany.getId(), CLIENT_COMPANY("변경"));

        // THEN
        assertThat(updated.getName()).isEqualTo("변경");
        assertThatThrownBy(() -> clientCompanyService.update(updated.getId(), CLIENT_COMPANY("중복")))
                .isInstanceOf(AlreadyExistException.class);
    }

    @Test
    @DisplayName("고객사 삭제")
    void removeById() {
        // GIVEN
        ClientCompany clientCompany = clientCompanyService.create(CLIENT_COMPANY("고객사"));

        // WHEN
        clientCompanyService.removeById(clientCompany.getId());

        // THEN
        assertThatThrownBy(() -> clientCompanyService.findById(clientCompany.getId()))
                .isInstanceOf(NotFoundException.class);
    }
}