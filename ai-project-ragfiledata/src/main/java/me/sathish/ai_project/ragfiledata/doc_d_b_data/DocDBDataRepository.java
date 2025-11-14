package me.sathish.ai_project.ragfiledata.doc_d_b_data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DocDBDataRepository extends MongoRepository<DocDBData, Long> {

    Page<DocDBData> findAllById(Long id, Pageable pageable);

    boolean existsByNameIgnoreCase(String name);

}
