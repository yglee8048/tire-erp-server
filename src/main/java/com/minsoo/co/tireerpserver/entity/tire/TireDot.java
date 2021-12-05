package com.minsoo.co.tireerpserver.entity.tire;

import com.minsoo.co.tireerpserver.entity.BaseTimeEntity;
import com.minsoo.co.tireerpserver.entity.stock.Stock;
import com.minsoo.co.tireerpserver.model.stock.request.StockRequest;
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
public class TireDot extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tire_dot_id", length = 20)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Column(name = "dot", nullable = false)
    private String dot;

    @OneToMany(mappedBy = "tireDot", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Stock> stocks = new HashSet<>();

    public static TireDot of(Tire tire, String dot) {
        TireDot tireDot = new TireDot();
        return tireDot.update(tire, dot);
    }

    public TireDot update(Tire tire, String dot) {
        this.tire = tire;
        this.dot = dot;
        return this;
    }

    public long getSumOfStockQuantity() {
        return stocks.stream()
                .mapToLong(Stock::getQuantity)
                .sum();
    }

    public boolean isValidStockRequests(List<StockRequest> stockRequests) {
        long sumOfRequest = stockRequests.stream()
                .mapToLong(StockRequest::getQuantity)
                .sum();
        return getSumOfStockQuantity() == sumOfRequest;
    }

    public long getSumOfOpenedStockQuantity() {
        return stocks.stream()
                .filter(stock -> !stock.getLock())
                .mapToLong(Stock::getQuantity)
                .sum();
    }

    public boolean isActive() {
        return getSumOfStockQuantity() > 0;
    }
}
