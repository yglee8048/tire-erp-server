package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.code.TireOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "tire_options")
public class TireOptions implements Serializable {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tire_id", nullable = false)
    private Tire tire;

    @Id
    @Enumerated(STRING)
    @Column(name = "option")
    private TireOption option;

    public TireOptions(Tire tire, TireOption option) {
        this.tire = tire;
        this.option = option;
    }

    public static TireOptions of(Tire tire, TireOption option) {
        return new TireOptions(tire, option);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TireOptions that = (TireOptions) o;
        return tire.getId().equals(that.tire.getId()) && option == that.option;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tire.getId(), option);
    }
}
