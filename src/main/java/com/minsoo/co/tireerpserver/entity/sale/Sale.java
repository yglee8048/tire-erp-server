package com.minsoo.co.tireerpserver.entity.sale;

import com.minsoo.co.tireerpserver.constant.SaleSource;
import com.minsoo.co.tireerpserver.constant.SaleStatus;
import com.minsoo.co.tireerpserver.constant.SystemMessage;
import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.client.ClientCompany;
import com.minsoo.co.tireerpserver.exception.BadRequestException;
import com.minsoo.co.tireerpserver.model.request.sale.DeliveryRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleCreateRequest;
import com.minsoo.co.tireerpserver.model.request.sale.SaleUpdateRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "sale")
public class Sale extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sale_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_company_id", referencedColumnName = "client_company_id")
    private ClientCompany clientCompany;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id", referencedColumnName = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    @Column(name = "source")
    private SaleSource source;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private SaleStatus status;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "desired_delivery_date")
    private LocalDate desiredDeliveryDate;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<SaleContent> saleContents = new HashSet<>();

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<SaleMemo> saleMemos = new HashSet<>();

    private Sale(SaleSource source, DeliveryRequest deliveryRequest) {
        this.source = source;
        this.status = source.equals(SaleSource.ONLINE) ? SaleStatus.REQUESTED : SaleStatus.COMPLETED;
        this.delivery = new Delivery(this).update(deliveryRequest);
    }

    public static Sale of(ClientCompany clientCompany, SaleCreateRequest saleCreateRequest, SaleSource source) {
        Sale sale = new Sale(source, saleCreateRequest.getDelivery());
        return sale.update(clientCompany, saleCreateRequest.toUpdate(), source);
    }

    public Sale update(ClientCompany clientCompany, SaleUpdateRequest saleUpdateRequest, SaleSource source) {
        if (source.equals(SaleSource.ONLINE) && !this.status.equals(SaleStatus.REQUESTED)) {
            throw new BadRequestException(SystemMessage.ALREADY_CONFIRMED);
        }

        this.clientCompany = clientCompany;
        this.transactionDate = saleUpdateRequest.getTransactionDate();
        this.releaseDate = saleUpdateRequest.getReleaseDate();
        this.desiredDeliveryDate = saleUpdateRequest.getDesiredDeliveryDate();
        return this;
    }

    public Sale confirm() {
        this.status = SaleStatus.CONFIRMED;
        return this;
    }

    public Sale completed() {
        this.status = SaleStatus.COMPLETED;
        return this;
    }
}
