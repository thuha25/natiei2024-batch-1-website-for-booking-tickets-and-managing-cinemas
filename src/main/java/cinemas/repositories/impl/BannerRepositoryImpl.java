package cinemas.repositories.impl;

import cinemas.models.Banner;
import cinemas.repositories.BannerRepository;
import org.springframework.stereotype.Repository;

@Repository("bannerRepository")
public class BannerRepositoryImpl extends BaseRepositoryImpl<Banner, Integer> implements BannerRepository {
    public BannerRepositoryImpl() {
        super(Banner.class);
    }
}
