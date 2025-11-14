package me.sathish.sathishaidashboard.web;

import me.sathish.sathishaidashboard.ragfiledata.doc_d_b_data.DocDBDataResource;
import me.sathish.sathishaidashboard.sathishaiquery.lookup_user_questions.LookupUserQuestionsResource;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeResource {

    @GetMapping("/home")
    public RepresentationModel<?> index() {
        return RepresentationModel.of(null)
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(LookupUserQuestionsResource.class).getAllLookupUserQuestionss(null, null)).withRel("lookupUserQuestionses"))
                .add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DocDBDataResource.class).getAllDocDBDatas(null, null)).withRel("docDBDatas"));
    }

}
