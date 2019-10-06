/* ******************************************************************************
 * Copyright 2000 - 2019 Kyriba Corp. All Rights Reserved.                      *
 * The content of this file is copyrighted by Kyriba Corporation and can not be *
 * reproduced, distributed, altered or used in any form, in whole or in part.   *
 *   Date            Author        Changes                                      *
 * 27.07.2019         M-VBE         Created                                      *
 ********************************************************************************/
package com.kyriba.schoolclassservice.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.database.rider.core.DBUnitRule;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.io.IOException;

import static org.junit.Assert.assertTrue;


/**
 * @author M-VBE
 * @since 19.2
 */
@ActiveProfiles("testcontainer")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
@AutoConfigureRestDocs
@DataSet(value = "datasets/classes-and-pupils.yml", cleanAfter = true)
public class SchoolClassGraphQLApiTest
{
  @LocalServerPort
  private int port;

  @Value("${api.version.path}")
  private String apiPrefix;

  @Autowired
  private GraphQLTestTemplate graphQLTestTemplate;


  @Autowired
  private WebApplicationContext context;

  //DbRider's cache connection works very strange because it caches connection between tests in static var so 
  //different tests may reuse same connection even if new database was specified in context
  //So it's important to specify different executors
  @Rule
  public DBUnitRule dbUnitRule = DBUnitRule.instance("graphql",
      () -> context.getBean(DataSource.class).getConnection());


  @Test
  public void testSimpleQuery() throws IOException, JSONException
  {
    //given
    ObjectNode objectNode = new ObjectMapper().createObjectNode();
    objectNode.putArray("ids");

    //when
    GraphQLResponse result = graphQLTestTemplate.perform("graphql/query-classes.graphql", objectNode);

    //then
    assertTrue(result.isOk());
    JsonNode parsedResponse = result.readTree().get("data").get("schoolClasses");
    Assertions.assertThat(parsedResponse.size()).isEqualTo(2);
    JSONAssert.assertEquals("{\n" +
            "  \"id\": \"1\",\n" +
            "  \"grade\": 1,\n" +
            "  \"letter\": \"A\",\n" +
            "  \"headTeacher\": null,\n" +
            "  \"pupils\": [\n" +
            "    {\n" +
            "      \"id\": \"1\",\n" +
            "      \"name\": \"Иванов\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"2\",\n" +
            "      \"name\": \"Петров\"\n" +
            "    }\n" +
            "  ]\n" +
            "}", parsedResponse.get(0).toString(), JSONCompareMode.STRICT );
    JSONAssert.assertEquals("{\n" +
            "  \"id\": \"2\",\n" +
            "  \"grade\": 2,\n" +
            "  \"letter\": \"Б\",\n" +
            "  \"headTeacher\": null,\n" +
            "  \"pupils\": []\n" +
            "}", parsedResponse.get(1).toString(), JSONCompareMode.STRICT);
  }


  @Test
  public void testParameterizedQuery() throws IOException, JSONException
  {
    //given
    ObjectNode objectNode = new ObjectMapper().createObjectNode();
    objectNode.putArray("ids").add(2);

    //when
    GraphQLResponse result = graphQLTestTemplate.perform("graphql/query-classes.graphql", objectNode);

    //then
    assertTrue(result.isOk());
    JsonNode parsedResponse = result.readTree().get("data").get("schoolClasses");
    Assertions.assertThat(parsedResponse.size()).isEqualTo(1);
    JSONAssert.assertEquals("{\n" +
        "  \"id\": \"2\",\n" +
        "  \"grade\": 2,\n" +
        "  \"letter\": \"Б\",\n" +
        "  \"headTeacher\": null,\n" +
        "  \"pupils\": []\n" +
        "}", parsedResponse.get(0).toString(), JSONCompareMode.STRICT);

  }
}
