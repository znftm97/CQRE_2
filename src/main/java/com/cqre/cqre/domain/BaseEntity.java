package com.cqre.cqre.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    @Convert(converter = DateFormatChange.class)
    private LocalDateTime createDate;

    @LastModifiedDate
    @Convert(converter = DateFormatChange.class)
    private LocalDateTime lastModifiedDate;
}

@Converter
class DateFormatChange implements AttributeConverter<LocalDateTime, String>{
    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        return attribute.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(dbData, formatter);
    }
}
