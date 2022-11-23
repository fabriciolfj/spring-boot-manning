package com.manning.sbip.ch01.springbootappdemo;

import com.manning.sbip.ch01.springbootappdemo.entity.Course;
import com.manning.sbip.ch01.springbootappdemo.entity.QCourse;
import com.manning.sbip.ch01.springbootappdemo.repository.AuthorRepository;
import com.manning.sbip.ch01.springbootappdemo.repository.CourseRepository;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import org.assertj.core.api.Condition;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest carrega todo o contexto do spring
@DataJpaTest
class SpringBootAppDemoApplicationTests {

	@Autowired
	private CourseRepository courseRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private EntityManager entityManager;

	@Test
	public void whenCountallCoursesThenExpectFiveCourses() {
		assertThat(authorRepository.getAuthorCourseInfo(2L)).hasSize(3);
	}

	@Test
	public void givenCoursesCreateWhenLoadCoursesWithQueryThenExpectCorrectCourseDetails() {
		courseRepository.saveAll(getCourseList());
		QCourse course = QCourse.course;
		JPAQuery query1 = new JPAQuery(entityManager);
		query1.from(course).where(course.category.eq("Spring"));

		assertThat(query1.fetch().size()).isEqualTo(3);

		JPAQuery query2 = new JPAQuery(entityManager);
		query2.from(course)
				.where(course.category.eq("Spring")
				.and(course.rating.gt(3)));

		assertThat(query2.fetch().size()).isEqualTo(2);

		OrderSpecifier<Integer> descOrderSpecifier = course.rating.desc();
		assertThat(Lists.newArrayList(courseRepository.findAll(descOrderSpecifier)).get(0).getName())
				.isEqualTo("Getting Started with Spring Security DSL");
	}

	@Test
	public void givenCoursesCreatedWhenLoadCourseswithQueryThenExpectCorrectCourseDetails() {
		courseRepository.saveAll(getCourseList());

		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		CriteriaQuery<Course> courseCriteriaQuery = criteriaBuilder.createQuery(Course.class);
		Root<Course> courseRoot = courseCriteriaQuery.from(Course.class);

		Predicate courseCategoryPredicate = criteriaBuilder.equal(courseRoot.get("category"), "Spring");
		courseCriteriaQuery.where(courseCategoryPredicate);

		TypedQuery<Course> query = entityManager.createQuery(courseCriteriaQuery);

		assertThat(query.getResultList().size()).isEqualTo(3);
	}

	@Test
	public void GivenCreateCourseWhenLoadTheCourseThenExpectSameCourse() {
		courseRepository.saveAll(getCourseList());
		assertThat(courseRepository.findAllByCategory("Spring")).hasSize(3);
		assertThat(courseRepository.existsByName("JavaScript for All")).isTrue();
		assertThat(courseRepository.existsByName("Mastering JavaScript")).isFalse();
		assertThat(courseRepository.findByCategoryOrderByNameDesc("Python").stream().count()).isEqualTo(2);
		assertThat(courseRepository.findByNameStartsWith("Getting Started")).hasSize(3);
		//assertThat(Arrays.asList(courseRepository.findAll()).size()).isEqualTo(1);
		//assertThat(courseRepository.findById(result.getId())).get().isEqualTo(result);

		//courseRepository.deleteById(result.getId());
		//assertThat(courseRepository.findAll()).size().isEqualTo(0);
	}

	@Test
	void givenDataAvailableWhenLoadFirstPageThenGetFiveRecords() {
		courseRepository.saveAll(getCourseList());
		Pageable pageable = PageRequest.of(0,5);
		assertThat(courseRepository.findAll(pageable)).hasSize(5);
		assertThat(pageable.getPageNumber()).isEqualTo(0);

		Pageable nextPageable = pageable.next();
		assertThat(courseRepository.findAll(nextPageable)).hasSize(2);
		//assertThat(nextPageable.getPageNumber()).isEqualTo(0);
	}

	@Test
	void givenDataAvailableWhenApplyCustomSortThenGetSortedResult() {
		courseRepository.saveAll(getCourseList());
		Pageable customSortPageable = PageRequest.of(0,7, Sort.by("ration").descending().and(Sort.by("name")));
		Condition<Course> customSortFirstCourseCondition = new Condition<>() {
			@Override
			public boolean matches(Course course) {
				return course.getId() == 4L && course.getName().equals("Getting Started with Python");
			}
		};

		var result =  courseRepository.findAll(customSortPageable).stream().findFirst();
		assertThat(courseRepository.findAll(customSortPageable)).first().has(customSortFirstCourseCondition);
	}

	@Test
	void givenCoursesCreatewhenLoadCoursesBySpringCategoryThenExpectThreeCourses() {
		courseRepository.saveAll(getCourseList());
		assertThat(courseRepository.findAllByCategoryAndRating("Spring", 4)).hasSize(1);
	}

	@Test
	public void givenCoursesCreatedWhenLoadCoursesWithQueryThenExpectCorrectCourseDetails() {
		courseRepository.saveAll(getCourseList());
		assertThat(courseRepository.findAllByCategory("Spring")).hasSize(3);
		assertThat(courseRepository.findAllByRating(3)).hasSize(2);
		assertThat(courseRepository.findAllByCategoryAnRatingGreaterThan("Spring", 3)).hasSize(2);
		courseRepository.updateCourseRatingByName(4, "Getting Started with Spring Cloud Kubernetes");
		assertThat(courseRepository.findAllByCategoryAnRatingGreaterThan("Spring", 3)).hasSize(3);
	}

	private List<Course> getCourseList() {
		Course rapidSpringBootCourse = new Course("Rapid Spring Boot Application Development", "Spring", 4,"Spring Boot gives all the power of the Spring Framework without all of the complexity");
		Course springSecurityDslCourse = new Course("Getting Started with Spring Security DSL", "Spring", 5, "Learn Spring Security DSL in easy steps");
		Course springCloudKubernetesCourse = new Course("Getting Started with Spring Cloud Kubernetes", "Spring", 3, "Master Spring Boot application deployment with Kubernetes");
		Course rapidPythonCourse = new Course("Getting Started with Python", "Python", 5, "Learn Python concepts in easy steps");
		Course gameDevelopmentWithPython = new Course("Game Development with Python", "Python", 3, "Learn Python by developing 10 wonderful games");
		Course javaScriptForAll = new Course("JavaScript for All", "JavaScript", 4, "Learn basic JavaScript syntax that can apply to anywhere");
		Course javaScriptCompleteGuide = new Course("JavaScript Complete Guide", "JavaScript", 5, "Master JavaScript with Core Concepts and Web Development");

		return Arrays.asList(rapidSpringBootCourse, springSecurityDslCourse, springCloudKubernetesCourse, rapidPythonCourse, gameDevelopmentWithPython, javaScriptForAll, javaScriptCompleteGuide);
	}

}
