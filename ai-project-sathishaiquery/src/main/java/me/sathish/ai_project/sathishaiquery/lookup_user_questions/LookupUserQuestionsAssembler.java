package me.sathish.ai_project.sathishaiquery.lookup_user_questions;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import me.sathish.ai_project.base.model.SimpleValue;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class LookupUserQuestionsAssembler implements SimpleRepresentationModelAssembler<LookupUserQuestionsDTO> {

    @Override
    public void addLinks(final EntityModel<LookupUserQuestionsDTO> entityModel) {
        entityModel.add(linkTo(methodOn(LookupUserQuestionsResource.class).getLookupUserQuestions(entityModel.getContent().getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(LookupUserQuestionsResource.class).getAllLookupUserQuestionss(null, null)).withRel(IanaLinkRelations.COLLECTION));
    }

    @Override
    public void addLinks(
            final CollectionModel<EntityModel<LookupUserQuestionsDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(LookupUserQuestionsResource.class).getAllLookupUserQuestionss(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<Long>> toSimpleModel(final Long id) {
        final EntityModel<SimpleValue<Long>> simpleModel = SimpleValue.entityModelOf(id);
        simpleModel.add(linkTo(methodOn(LookupUserQuestionsResource.class).getLookupUserQuestions(id)).withSelfRel());
        return simpleModel;
    }

}
