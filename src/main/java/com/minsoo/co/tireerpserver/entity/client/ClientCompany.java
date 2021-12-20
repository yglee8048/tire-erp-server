package com.minsoo.co.tireerpserver.entity.client;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.BusinessInfo;
import com.minsoo.co.tireerpserver.entity.rank.Rank;
import com.minsoo.co.tireerpserver.model.request.client.ClientCompanyRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "client_company")
public class ClientCompany extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_company_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rank_id", referencedColumnName = "rank_id")
    private Rank rank;

    @Column(name = "description")
    private String description;

    @Embedded
    private BusinessInfo businessInfo;

    public static ClientCompany of(ClientCompanyRequest clientCompanyRequest) {
        ClientCompany clientCompany = new ClientCompany();
        return clientCompany.update(clientCompanyRequest);
    }

    public ClientCompany update(ClientCompanyRequest clientCompanyRequest) {
        this.name = clientCompanyRequest.getName();
        this.description = clientCompanyRequest.getDescription();
        this.businessInfo = new BusinessInfo(clientCompanyRequest.getBusinessInfo());
        return this;
    }
}
