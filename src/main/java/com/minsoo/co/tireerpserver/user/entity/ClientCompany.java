package com.minsoo.co.tireerpserver.user.entity;

import com.minsoo.co.tireerpserver.shared.entity.BusinessInfo;
import com.minsoo.co.tireerpserver.user.model.client.company.ClientCompanyRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "client_company")
public class ClientCompany {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "client_company_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Embedded
    private BusinessInfo businessInfo;

    public ClientCompany(ClientCompanyRequest clientCompanyRequest) {
        this.name = clientCompanyRequest.getName();
        this.description = clientCompanyRequest.getDescription();
        this.businessInfo = BusinessInfo.of(clientCompanyRequest.getBusinessInfo());
    }

    public static ClientCompany of(ClientCompanyRequest clientCompanyRequest) {
        return new ClientCompany(clientCompanyRequest);
    }

    public ClientCompany update(ClientCompanyRequest clientCompanyRequest) {
        this.name = clientCompanyRequest.getName();
        this.description = clientCompanyRequest.getDescription();
        this.businessInfo = BusinessInfo.of(clientCompanyRequest.getBusinessInfo());
        return this;
    }
}
