package com.manning.sbip.ch01.springbootappdemo;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import com.manning.sbip.ch01.springbootappdemo.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest carrega todo o contexto do spring
@DataJpaTest
class SpringBootAppDemoApplicationTests {

	@Autowired
	private CourseRepository courseRepository;

	@Test
	public void GivenCreateCourseWhenLoadTheCourseThenExpectSameCourse() {
		Course course = new Course("Rapid Spring Boot Application Development", "Spring", 4, "'Spring Boot gives all the power of the Spring Framework without all of the complexities");
		var result = courseRepository.save(course);
		assertThat(Arrays.asList(courseRepository.findAll()).size()).isEqualTo(1);
		//assertThat(courseRepository.findById(result.getId())).get().isEqualTo(result);

		//courseRepository.deleteById(result.getId());
		//assertThat(courseRepository.findAll()).size().isEqualTo(0);
	}

}
