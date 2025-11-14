package me.sathish.ai_project.ragfiledata.doc_d_b_data;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import me.sathish.ai_project.base.model.SimpleValue;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;


@Component
public class DocDBDataAssembler implements SimpleRepresentationModelAssembler<DocDBDataDTO> {

    @Override
    public void addLinks(final EntityModel<DocDBDataDTO> entityModel) {
        entityModel.add(linkTo(methodOn(DocDBDataResource.class).getDocDBData(entityModel.getContent().getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(DocDBDataResource.class).getAllDocDBDatas(null, null)).withRel(IanaLinkRelations.COLLECTION));
    }

    @Override
    public void addLinks(final CollectionModel<EntityModel<DocDBDataDTO>> collectionModel) {
        collectionModel.add(linkTo(methodOn(DocDBDataResource.class).getAllDocDBDatas(null, null)).withSelfRel());
    }

    public EntityModel<SimpleValue<Long>> toSimpleModel(final Long id) {
        final EntityModel<SimpleValue<Long>> simpleModel = SimpleValue.entityModelOf(id);
        simpleModel.add(linkTo(methodOn(DocDBDataResource.class).getDocDBData(id)).withSelfRel());
        return simpleModel;
    }

}
