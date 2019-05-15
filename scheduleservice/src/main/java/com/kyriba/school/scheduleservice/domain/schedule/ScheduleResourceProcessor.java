package com.kyriba.school.scheduleservice.domain.schedule;

import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClass;
import com.kyriba.school.scheduleservice.domain.schoolclass.SchoolClassProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * @author vitali.zhuleho
 */
@Component
public class ScheduleResourceProcessor implements ResourceProcessor<Resource<ScheduleProjection>> {

    private final RepositoryEntityLinks entityLinks;
    private final String REL = Schedule.class.getSimpleName().toLowerCase();

    @Autowired
    public ScheduleResourceProcessor(RepositoryEntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public Resource<ScheduleProjection> process(Resource<ScheduleProjection> resource) {
        ScheduleProjection schedule = resource.getContent();
        SchoolClassProjection schoolClass = schedule.getSchoolClass();
        Link linkByYearGradeLetter = entityLinks.linkFor(Schedule.class).slash(schedule.getYear()).slash(schoolClass.getGrade())
                .slash(schoolClass.getLetter()).withSelfRel().withTitle("by year grade and letter");
        Link linkById = entityLinks.linkFor(Schedule.class).slash(schedule.getId()).withSelfRel().withTitle("by id");
        resource.add(linkByYearGradeLetter);
        resource.add(linkById);
        return resource;
    }

}
