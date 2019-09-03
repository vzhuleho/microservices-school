/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 27.07.2019         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.configuration;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.coxautodev.graphql.tools.GraphQLResolver;
import com.kyriba.schoolclassservice.service.SchoolClassService;
import com.kyriba.schoolclassservice.service.dto.PupilDto;
import com.kyriba.schoolclassservice.service.dto.SchoolClassDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.List;


/**
 * @author M-VBE
 * @since 19.2
 */
@RequiredArgsConstructor
@Configuration
public class GraphQLConfiguration
{
  final SchoolClassService schoolClassService;


  @Bean
  public GraphQLQueryResolver queryResolver()
  {
    return new GraphQLQueryResolver()
    {
      //here method name should be equal to attribute name in schema.graphql
      public List<SchoolClassDto> schoolClasses(List<Long> ids)
      {
        return CollectionUtils.isEmpty(ids) ? schoolClassService.getAll() : schoolClassService.getByIds(ids);
      }
    };
  }


  @Bean
  public GraphQLResolver<SchoolClassDto> schooolClassGraphQLResolver()
  {
    return new GraphQLResolver<SchoolClassDto>()
    {
      public Collection<PupilDto> pupils(SchoolClassDto schoolClassDto)
      {
        return schoolClassService.getPupilsForClass(schoolClassDto.getId());
      }
    };
  }
}
