package ua.estate.rialto.repository.ad;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.estate.rialto.model.Ad;

@Transactional(readOnly = true)
public interface CrudAdRepository<T extends Ad> extends JpaRepository<T, Integer> {

    @Override
    T findOne(Integer id);

    @Override
    @Transactional
    T save(Ad ad);
}
