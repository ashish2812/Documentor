package org.example.datastoreretrieveservice.repository;

import org.example.datastoreretrieveservice.model.UserDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ashish
 *
 * @date 26-Feb-2024
 *
 */
@Repository
public interface UserDetailsRepository extends MongoRepository<UserDetails, String> {
}
