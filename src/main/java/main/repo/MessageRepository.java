package main.repo;

import main.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByTextIsContainingOrTagIsContaining(String textFilter, String tagFilter);

    List<Message> findAllByOrderByIdDesc();
}
