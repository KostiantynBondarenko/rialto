package ua.estate.rialto.repository.flat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ua.estate.rialto.model.Flat;

@Transactional(readOnly = true)
public interface CrudFlatRepository extends JpaRepository<Flat, Integer> {

    @Override
    Flat findOne(Integer id);

    @Override
    @Transactional
    Flat save(Flat agent);
}
