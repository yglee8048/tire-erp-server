package com.minsoo.co.tireerpserver.model.entity.entities.tire;

import com.minsoo.co.tireerpserver.model.code.PatternOption;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "pattern_options")
public class PatternOptions {

    @Id
    @ManyToOne(fetch = LAZY)
    private Pattern pattern;

    @Enumerated(STRING)
    @Column(name = "option")
    private PatternOption option;
}
