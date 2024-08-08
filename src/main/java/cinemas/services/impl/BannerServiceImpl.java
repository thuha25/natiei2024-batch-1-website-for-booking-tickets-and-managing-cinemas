package cinemas.services.impl;

import cinemas.models.Banner;
import cinemas.repositories.BannerRepository;
import cinemas.services.BannerService;

import java.util.List;

public class BannerServiceImpl implements BannerService {
    private BannerRepository bannerRepository;

    public BannerServiceImpl(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAll();
    }
}
