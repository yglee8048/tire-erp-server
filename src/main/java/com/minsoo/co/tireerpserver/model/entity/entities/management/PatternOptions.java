package com.minsoo.co.tireerpserver.model.entity.entities.management;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
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
@Table(name = "pattern_options")
public class PatternOptions implements Serializable {

    @Id
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pattern_id", nullable = false)
    private Pattern pattern;

    @Id
    @Enumerated(STRING)
    @Column(name = "option", nullable = false)
    private PatternOption option;

    public PatternOptions(Pattern pattern, PatternOption option) {
        this.pattern = pattern;
        this.option = option;
    }

    public static PatternOptions of(Pattern pattern, PatternOption option) {
        return new PatternOptions(pattern, option);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatternOptions that = (PatternOptions) o;
        return pattern.getId().equals(that.pattern.getId()) && option == that.option;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pattern.getId(), option);
    }
}
