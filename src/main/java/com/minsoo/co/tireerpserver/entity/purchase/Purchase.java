package com.minsoo.co.tireerpserver.entity.purchase;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.management.Vendor;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseContentRequest;
import com.minsoo.co.tireerpserver.model.request.purchase.PurchaseRequest;
import com.minsoo.co.tireerpserver.repository.stock.StockRepository;
import com.minsoo.co.tireerpserver.repository.tire.TireDotRepository;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "purchase")
public class Purchase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendor_id", referencedColumnName = "vendor_id")
    private Vendor vendor;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final Set<PurchaseContent> purchaseContents = new HashSet<>();

    public static Purchase of(Vendor vendor, PurchaseRequest purchaseRequest, TireDotRepository tireDotRepository, StockRepository stockRepository) {
        Purchase purchase = new Purchase();
        return purchase.update(vendor, purchaseRequest, tireDotRepository, stockRepository);
    }

    public Purchase update(Vendor vendor, PurchaseRequest purchaseRequest, TireDotRepository tireDotRepository, StockRepository stockRepository) {
        this.vendor = vendor;
        this.transactionDate = purchaseRequest.getTransactionDate();
        this.description = purchaseRequest.getDescription();

        List<Long> removable = this.getPurchaseContents().stream()
                .map(PurchaseContent::getId)
                .collect(Collectors.toList());
        List<Long> stored = new ArrayList<>();
        for (PurchaseContentRequest purchaseContentRequest : purchaseRequest.getContents()) {
//            findByTireDotAntWarehouse(purchaseContentRequest)
//                    .map(purchaseContent -> purchaseContent.update(purchaseContentRequest))
//                    .orElseGet(() -> {
//
//                    });
        }

        return this;
    }

    public Optional<PurchaseContent> findByTireDotAntWarehouse(PurchaseContentRequest purchaseContentRequest) {
        return purchaseContents.stream()
                .filter(purchaseContent -> purchaseContent.match(purchaseContentRequest.getTireId(), purchaseContentRequest.getDot(), purchaseContentRequest.getStockId()))
                .findAny();
    }
}
