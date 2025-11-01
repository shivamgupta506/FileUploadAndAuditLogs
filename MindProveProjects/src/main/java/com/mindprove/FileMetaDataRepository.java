package com.mindprove;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface FileMetaDataRepository extends JpaRepository<FileMetadata, Integer> {

	@Query("SELECT f FROM FileMetadata f WHERE f.fileName = :fileName")
	Optional<FileMetadata> findByFileNames(String fileName);

}
