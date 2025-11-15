package me.sathish.ai_project.sathishaiquery.lookup_user_questions;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LookupUserQuestionsDTO {

    private Long id;

    @Size(max = 255)
    private String questionAsked;

    private Long lookupusername;

}
