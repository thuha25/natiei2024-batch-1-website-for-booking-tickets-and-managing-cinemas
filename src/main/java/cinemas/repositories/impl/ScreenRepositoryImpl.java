package cinemas.repositories.impl;

import cinemas.models.Screen;
import cinemas.repositories.ScreenRepository;
import org.springframework.stereotype.Repository;

@Repository("screenRepository")
public class ScreenRepositoryImpl extends BaseRepositoryImpl<Screen, Integer> implements ScreenRepository {
    public ScreenRepositoryImpl() {
        super(Screen.class);
    }
}
