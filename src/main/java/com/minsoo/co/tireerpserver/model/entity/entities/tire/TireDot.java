package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.dto.stock.ModifyStock;
import com.minsoo.co.tireerpserver.model.dto.stock.ModifyStockRequest;
import com.minsoo.co.tireerpserver.model.dto.tire.dot.TireDotRequest;
import com.minsoo.co.tireerpserver.model.entity.entities.stock.Stock;
import lombok.*;

import javax.persistence.*;

import java.util.*;

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

    public void modifyStocks(List<ModifyStock> modifyStocks) {
        // create map
        Map<Long, ModifyStock> modifyStockMap = new HashMap<>();
        Set<Stock> created = new HashSet<>();
        modifyStocks.forEach(modifyStock -> {
            if (modifyStock.getStockId() == null) {
                // TODO: warehouse와 nickname의 중복을 확인해서 합쳐서 생성해야한다.
                created.add(Stock.of(this, modifyStock.getWarehouse(), modifyStock.getNickname(), modifyStock.getQuantity(), modifyStock.getLock()));
            } else {
                Long key = modifyStock.getStockId();
                if (modifyStockMap.containsKey(key)) {
                    modifyStock.setQuantity(modifyStock.getQuantity() + modifyStockMap.get(key).getQuantity());
                }
                modifyStockMap.put(key, modifyStock);
            }
        });

        // filter removed
        Set<Stock> removed = new HashSet<>();
        this.getStocks()
                .forEach(stock -> {
                    if (modifyStockMap.containsKey(stock.getId())) {
                        ModifyStock modifyStock = modifyStockMap.get(stock.getId());
                        stock.update(this, modifyStock.getWarehouse(), modifyStock.getNickname(), modifyStock.getQuantity(), modifyStock.getLock());
                    } else {
                        removed.add(stock);
                    }
                });

        this.getStocks().addAll(created);
        this.getStocks().removeAll(removed);
    }
}
