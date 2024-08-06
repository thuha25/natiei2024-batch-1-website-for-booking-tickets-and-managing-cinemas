package cinemas.models.common;

import cinemas.converters.ZonedDateTimeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class CreationUpdationAuditableEntity {

    @CreationTimestamp
    @Convert(converter = ZonedDateTimeConverter.class)
    @Column(name = "created_at", updatable = false)
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    @Convert(converter = ZonedDateTimeConverter.class)
    @Column(name = "updated_at")
    private ZonedDateTime updatedAt;

    // Getters and Setters
    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

