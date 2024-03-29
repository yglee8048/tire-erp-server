package com.minsoo.co.tireerpserver.entity.tire;

import com.minsoo.co.tireerpserver.entity.BaseEntity;
import com.minsoo.co.tireerpserver.entity.purchase.PurchaseContent;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.model.request.stock.StockMoveRequest;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tire_dot")
public class TireDot extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tire_dot_id", length = 20)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tire_id")
    private Tire tire;

    @Column(name = "dot")
    private String dot;

    @OneToMany(mappedBy = "tireDot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Stock> stocks = new HashSet<>();

    @OneToMany(mappedBy = "tireDot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<PurchaseContent> purchaseContents = new HashSet<>();

    public static TireDot of(Tire tire, String dot) {
        TireDot tireDot = new TireDot();
        return tireDot.update(tire, dot);
    }

    public TireDot update(Tire tire, String dot) {
        this.tire = tire;
        this.dot = dot;
        return this;
    }

    public int getSumOfStockQuantity() {
        return stocks.stream()
                .mapToInt(Stock::getQuantity)
                .sum();
    }

    public boolean isValidStockRequests(List<StockMoveRequest> stockMoveRequests) {
        int sumOfRequest = stockMoveRequests.stream()
                .mapToInt(StockMoveRequest::getQuantity)
                .sum();
        return getSumOfStockQuantity() == sumOfRequest;
    }
}
