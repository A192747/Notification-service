package ru.micro.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import ru.micro.entity.Contacts;

import java.util.List;
import java.util.UUID;

@Repository
public interface ContactsRepository extends CassandraRepository<Contacts, UUID> {
    @Query("SELECT * FROM contacts WHERE user_id =?0 ALLOW FILTERING")
    List<Contacts> findAllByUserId(Integer userId);
}
