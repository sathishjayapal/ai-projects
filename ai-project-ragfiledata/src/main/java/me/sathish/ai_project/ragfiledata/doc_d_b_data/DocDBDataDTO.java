package me.sathish.ai_project.ragfiledata.doc_d_b_data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import me.sathish.ai_project.base.file.FileData;


@Getter
@Setter
public class DocDBDataDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @DocDBDataNameUnique
    private String name;

    @Valid
    private FileData fileName;

    @NotNull
    private Long username;

}
