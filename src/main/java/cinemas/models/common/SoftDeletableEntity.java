package cinemas.models.common;

import cinemas.converters.ZonedDateTimeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;

import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class SoftDeletableEntity extends CreationUpdationAuditableEntity {

    @Convert(converter = ZonedDateTimeConverter.class)
    @Column(name = "deleted_at")
    private ZonedDateTime deletedAt;

    public ZonedDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(ZonedDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }
}

