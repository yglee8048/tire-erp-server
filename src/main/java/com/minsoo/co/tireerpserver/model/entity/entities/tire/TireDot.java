package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.dto.stock.ModifyStockRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.stock.Stock;
import lombok.*;

import javax.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.*;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_dot",
        uniqueConstraints = {@UniqueConstraint(name = "tire_dot_unique_dot", columnNames = {"tire_id", "dot"})})
public class TireDot {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "tire_dot_id", length = 20)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Column(name = "dot", nullable = false)
    private String dot;

    @Column(name = "retail_price", nullable = false)
    private Long retailPrice;

    @OneToMany(mappedBy = "tireDot", fetch = LAZY, cascade = ALL, orphanRemoval = true)
    private final Set<Stock> stocks = new HashSet<>();

    //== Business ==//
    public TireDot(Tire tire, TireDotRequest tireDotRequest) {
        this.tire = tire;
        this.dot = tireDotRequest.getDot();
        this.retailPrice = tireDotRequest.getRetailPrice();
    }

    public static TireDot of(Tire tire, TireDotRequest tireDotRequest) {
        return new TireDot(tire, tireDotRequest);
    }

    public TireDot update(Tire tire, TireDotRequest tireDotRequest) {
        this.tire = tire;
        this.dot = tireDotRequest.getDot();
        this.retailPrice = tireDotRequest.getRetailPrice();

        return this;
    }

    public Long getSumOfQuantity() {
        return this.stocks.stream()
                .map(Stock::getQuantity)
                .reduce(0L, Long::sum);
    }

    public boolean validateSumOfQuantity(List<ModifyStockRequest> modifyStockRequests) {
        Long sumOfQuantity = modifyStockRequests.stream()
                .map(ModifyStockRequest::getQuantity)
                .reduce(0L, Long::sum);
        return this.getSumOfQuantity().equals(sumOfQuantity);
    }
}
