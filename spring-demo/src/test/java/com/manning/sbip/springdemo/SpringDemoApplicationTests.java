package com.manning.sbip.springdemo;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
@ExtendWith(SpringExtension.class)
class SpringDemoApplicationTests {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void GivenObjectAvailableWhenSavetoCollectionThenExpectValue() {
		DBObject object = BasicDBObjectBuilder.start().add("Manning", "Spring boot na prática").get();

		mongoTemplate.save(object, "colecao");

		assertThat(mongoTemplate.findAll(DBObject.class, "colecao"))
				.extracting("Manning")
				.containsOnly("Spring boot na prática");

	}

}
