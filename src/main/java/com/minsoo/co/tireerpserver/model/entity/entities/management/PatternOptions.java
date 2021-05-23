package com.minsoo.co.tireerpserver.model.entity.entities.management;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "pattern_options",
        uniqueConstraints = {@UniqueConstraint(name = "pattern_options_unique", columnNames = {"pattern_id", "option"})})
public class PatternOptions {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "pattern_options_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "pattern_id", nullable = false)
    private Pattern pattern;

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
}
