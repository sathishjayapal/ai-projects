package me.sathish.ai_project.ragfiledata.doc_d_b_data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import me.sathish.ai_project.base.file.FileData;
import me.sathish.ai_project.base.user_info.UserInfo;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
@Getter
@Setter
public class DocDBData {

    @Id
    private Long id;

    @Indexed(unique = true)
    @NotNull
    @Size(max = 255)
    private String name;

    @Valid
    private FileData fileName;

    @DocumentReference(lazy = true)
    @NotNull
    private UserInfo username;

    @CreatedDate
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    private OffsetDateTime lastUpdated;

    @Version
    private Integer version;

}
