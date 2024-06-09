package ru.micro.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;
import ru.micro.entity.Contacts;

import java.util.UUID;

@Repository
public interface ContactsRepository extends CassandraRepository<Contacts, UUID> {
}
