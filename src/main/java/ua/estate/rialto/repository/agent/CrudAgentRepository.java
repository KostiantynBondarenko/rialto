package ua.estate.rialto.repository.agent;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ua.estate.rialto.model.Agent;

import java.util.List;

@Transactional(readOnly = true)
public interface CrudAgentRepository extends JpaRepository<Agent, Integer> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Agent a WHERE a.id=:id")
    int delete(@Param("id") int id);

    @Override
    @Transactional
    Agent save(Agent agent);

    @Override
    Agent findOne(Integer id);

    @Override
    List<Agent> findAll(Sort sort);

    List<Agent> findByPhone(String phone);

    List<Agent> findByPhoneOrAddPhone(String phone, String addPhone);
}
