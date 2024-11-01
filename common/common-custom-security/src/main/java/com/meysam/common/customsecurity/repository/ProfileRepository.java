package com.meysam.common.customsecurity.repository;

import com.meysam.common.customsecurity.model.entity.Profile;
import com.meysam.common.dao.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfileRepository extends BaseRepository<Profile> {

    List<Profile> findByFirstName(String firstName);

    List<Profile> findByLastName(String lastName);

    Optional<Profile> findByUsernameAndPasswordAndIsActive(String username, String pass, Boolean active);

    Optional<Profile> findByUsername(String username);

    List<Profile> findAllByIdIn(List<Long> ids);

    boolean existsByUsername(String username);
}
