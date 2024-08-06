package cinemas.models;

import cinemas.models.common.CreationUpdationAuditableEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "banners")
public class Banner extends CreationUpdationAuditableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "photo_url")
    private String photoUrl;
    @Column(name = "target_url")
    private String targetUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }
}
