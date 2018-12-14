package main.repo;

import main.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByTextIsContainingOrTagIsContaining(String textFilter, String tagFilter, Pageable pageable);

    Page<Message> findAllByOrderByIdDesc(Pageable pageable);

    Page<Message> findAll(org.springframework.data.domain.Pageable pageable);
}
